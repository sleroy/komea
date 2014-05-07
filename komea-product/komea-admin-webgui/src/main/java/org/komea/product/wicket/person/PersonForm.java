package org.komea.product.wicket.person;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.forms.PersonFormData;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.CustomUpdater;
import org.komea.product.wicket.utils.DataListSelectDialogBuilder;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.SelectBoxBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 * Formular to edit properties in the settings page.
 *
 * @author sleroy
 */
public final class PersonForm extends Form<Person> {

    private List<IHasKey> currentEntityList;
    private final Component feedBack;
    private final TextField groupField;
    private final NameGeneric groupName;
    private Boolean isAdmin;
    private final boolean isNew;
    private final LayoutPage page;
    private final IPasswordEncoder passEncoder;
    private PasswordTextField password;
    private final Person person;
    private final IPersonRoleService personRole;
    private final IPersonService personService;
    private final IProjectService projectService;
    /**
     * @author sleroy
     */
    private final IPersonGroupService prService;
    private String savPassword;
    private final UserBdd savUserBdd;
    private List<IHasKey> selectedEntity;
    private final List<IHasKey> projectMemberList;
    private final List<IHasKey> directProjectList;

    // private final TeamSelectorDialog teamDialog;
    public PersonForm(
            final boolean _isNew,
            final IPersonRoleService _personRole,
            final IPasswordEncoder _passEncoder,
            final IPersonService _personService,
            final IProjectService _projectService,
            final PersonFormData _personFormData,
            final String _id,
            final CompoundPropertyModel<Person> _compoundPropertyModel,
            final LayoutPage _page,
            final IPersonGroupService _prService) {

        super(_id, _compoundPropertyModel);
        page = _page;
        isNew = _isNew;
        passEncoder = _passEncoder;
        prService = _prService;
        personRole = _personRole;
        projectService = _projectService;
        personService = _personService;
        person = _compoundPropertyModel.getObject();

        selectedEntity = new ArrayList<IHasKey>();
        currentEntityList = new ArrayList<IHasKey>();
        projectMemberList = new ArrayList<IHasKey>();
        directProjectList = new ArrayList<IHasKey>();

        feedBack = new FeedbackPanel("feedback");
        feedBack.setOutputMarkupId(true);
        feedBack.setOutputMarkupPlaceholderTag(true);
        feedBack.setVisible(false);
        add(feedBack);
        groupName = new NameGeneric("");
        final TextFieldBuilder<String> keyField
                = TextFieldBuilder.<String>createRequired("login", person, "login")
                .simpleValidator(3, 255)
                .withTooltip(getString("global.save.form.field.tooltip.login"))
                .highlightOnErrors();

        if (isNew) {
            keyField.UniqueStringValidator(getString("global.save.form.field.label.login"),
                    personService);
        } else {
            keyField.buildTextField().add(new AttributeModifier("readonly", "readonly"));
        }

        add(keyField.build());
        final PersonGroup selectByPrimaryKey
                = prService.selectByPrimaryKey(person.getIdPersonGroup());
        if (selectByPrimaryKey != null) {
            groupName.setName(selectByPrimaryKey.getName());
        }
        groupField
                = TextFieldBuilder.<String>create("group", groupName, "name")
                .withTooltip(getString("memberpage.save.form.field.tooltip.memberof"))
                .buildTextField();
        add(groupField);

        if (person.getUserBdd() == null) {
            person.setUserBdd(UserBdd.KOMEA);
        }
        savUserBdd = person.getUserBdd();
        add(SelectBoxBuilder.<UserBdd>createWithEnumRequire("userBdd", person, UserBdd.class)
                .withTooltip(getString("memberpage.save.form.field.tooltip.userorigin")).build());

        if (person.getId() != null) {

            directProjectList.addAll((List) projectService.getProjectsOfAMember(person.getId()));
            Integer idPersonGroup = person.getIdPersonGroup();
            if (idPersonGroup != null) {
                projectMemberList.addAll(projectService.getProjectsOfPersonGroupRecursively(idPersonGroup));
                projectMemberList.removeAll(directProjectList);
            }
            this.currentEntityList.addAll(directProjectList);
            this.currentEntityList.addAll(projectMemberList);
        }

        ListMultipleChoice<IHasKey> listUser = new ListMultipleChoice<IHasKey>("table", new PropertyModel<List<IHasKey>>(this, "selectedEntity"), currentEntityList) {

            @Override
            protected boolean isDisabled(IHasKey object, int index, String selected) {
                if (projectMemberList.contains(object)) {
                    return true;
                }
                return super.isDisabled(object, index, selected);
            }

        };
        listUser.setChoiceRenderer(DialogFactory.getChoiceRendenerEntity());
        listUser.setMaxRows(8);
        listUser.setOutputMarkupId(true);

        final CustomUpdater cupdater = new CustomUpdater(listUser) {

            @Override
            public void update() {
                currentEntityList.removeAll(projectMemberList);
                directProjectList.clear();
                directProjectList.addAll(currentEntityList);
                currentEntityList.clear();
                projectMemberList.clear();
                if (person.getIdPersonGroup() != null) {
                    List<Project> projectsOfPersonGroupRecursively = projectService.getProjectsOfPersonGroupRecursively(person.getIdPersonGroup());
                    projectMemberList.addAll(projectsOfPersonGroupRecursively);
                }
                currentEntityList.addAll(projectMemberList);
                currentEntityList.addAll(directProjectList);
            }
        };

        DataListSelectDialogBuilder dataProject = new DataListSelectDialogBuilder();
        dataProject.setPage(this);
        dataProject.setIdDialog("dialogAddPerson");
        dataProject.setIdBtnAdd("btnAddPerson");
        dataProject.setIdBtnDel("btnDelPerson");
        dataProject.setListEntite(listUser);
        dataProject.setDisplayDialogMessage(getString("memberpage.save.form.field.tooltip.projects"));
        dataProject.setCurrentEntityList(currentEntityList);
        dataProject.setChoiceEntityList(selectedEntity);
        dataProject.setSelectDialogList((List) projectService.selectAll());
        dataProject.setService(projectService);
//        dataProject.addFilter(DialogFactory.getPersonWithoutPersonGroupFilter(personGroup.getId()));
        dataProject.setTooltips(getString("memberpage.form.field.multiple.member"));
        DialogFactory.addMultipleListDialog(dataProject);

        final SelectDialog dialogPersonGroup = new SelectDialog("dialogGroup", getString("memberpage.save.form.field.tooltip.memberof"), (List<IHasKey>) (List<?>) prService.selectAll(), person.getIdPersonGroup()) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {

                IHasKey selectedPersonGroup = getSelected();
                if (selectedPersonGroup != null) {
                    person.setIdPersonGroup(selectedPersonGroup.getId());
                    groupName.setName(selectedPersonGroup.getDisplayName());
                } else {
                    person.setIdPersonGroup(null);
                    groupName.setName("");
                }
                groupField.clearInput();
                target.add(groupField);
                cupdater.update();
                target.add(cupdater.getComposant());

            }

        };
        page.add(dialogPersonGroup);
        add(new AjaxLinkLayout<LayoutPage>("btnGroup", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogPersonGroup.open(art);

            }
        });

        initClassicField();
        add(new AjaxButtonSubmit("submit"));
        add(new AjaxLinkCancelButton("cancel", page));

    }

    public TextField getGroupField() {

        return groupField;
    }

    public NameGeneric getGroupName() {

        return groupName;
    }

    public Person getPerson() {

        return person;
    }

    public List<IHasKey> getSelectedEntity() {

        return selectedEntity;
    }

    public void initClassicField() {

        add(TextFieldBuilder.<String>createRequired("firstname", person, "firstName")
                .simpleValidator(2, 255)
                .withTooltip(getString("memberpage.save.form.field.tooltip.firstn"))
                .highlightOnErrors().build());
        add(TextFieldBuilder.<String>createRequired("lastname", person, "lastName")
                .simpleValidator(2, 255).highlightOnErrors()
                .withTooltip(getString("memberpage.save.form.field.tooltip.lastn")).build());

        isAdmin = Boolean.FALSE;
        final Integer idPersonRole = person.getIdPersonRole();
        if (idPersonRole != null) {
            final PersonRole adminRole = personRole.getAdminRole();
            if (idPersonRole.equals(adminRole.getId())) {
                isAdmin = Boolean.TRUE;
            }

        }
        if (person.getPassword() == null) {
            savPassword = "";
        } else {
            savPassword = person.getPassword();
        }
        person.setPassword("00000");
        password
                = (PasswordTextField) TextFieldBuilder.createPassword("password", person, "password")
                .simpleValidator(5, 255)
                .withTooltip(getString("global.save.form.field.tooltip.password"))
                .highlightOnErrors().build();
        password.setConvertEmptyInputStringToNull(false);
        password.setResetPassword(false);
        password.setOutputMarkupId(true);
        password.setOutputMarkupPlaceholderTag(true);
        if (isAdmin) {
            password.setVisible(true);
        } else {
            password.setVisible(false);
        }
        add(password);

        final PropertyModel<Boolean> modelchcekBox = new PropertyModel<Boolean>(this, "isAdmin");
        final AjaxCheckBox checkBox = new AjaxCheckBox("isAdmin", modelchcekBox) {

            @Override
            protected void onUpdate(final AjaxRequestTarget target) {

                if (isAdmin) {
                    password.setVisible(true);
                } else {
                    password.setVisible(false);
                    person.setPassword("00000");
                }
                target.add(password);
            }
        };

        add(checkBox);
        final TextField<String> emailTextField
                = TextFieldBuilder.<String>createRequired("email", person, "email")
                .withTooltip(getString("memberpage.save.form.field.tooltip.email"))
                .highlightOnErrors().buildTextField();
        emailTextField.add(EmailAddressValidator.getInstance());
        add(emailTextField);
    }

    public Boolean isIsAdmin() {

        return isAdmin;
    }

    public void setIsAdmin(final Boolean isAdmin) {

        this.isAdmin = isAdmin;
    }

    public void setSelectedEntity(final List<IHasKey> selectedEntity) {

        this.selectedEntity = selectedEntity;
    }

    private class AjaxLinkCancelButton extends AjaxLinkLayout<LayoutPage> {

        public AjaxLinkCancelButton(String string, LayoutPage page) {
            super(string, page);
        }

        @Override
        public void onClick(final AjaxRequestTarget art) {

            final LayoutPage page = getCustom();
            page.setResponsePage(new PersonPage(page.getPageParameters()));
        }
    }

    private class AjaxButtonSubmit extends AjaxButton {

        public AjaxButtonSubmit(String id) {
            super(id);
        }

        @Override
        protected void onError(final AjaxRequestTarget target, final Form<?> form) {

            feedBack.setVisible(true);
            target.add(feedBack);

        }

        @Override
        protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

            feedBack.setVisible(false);
            // repaint the feedback panel so that it is hidden
            target.add(feedBack);
            person.setUserBdd(savUserBdd);
            if ("00000".equals(person.getPassword())) {
                person.setPassword(savPassword);
            } else {
                person.setPassword(passEncoder.encodePassword(person.getPassword()));
            }
            if (isAdmin) {
                person.setIdPersonRole(personRole.getAdminRole().getId());
            } else {
                person.setIdPersonRole(null);
            }
            currentEntityList.removeAll(projectMemberList);
            personService.saveOrUpdatePersonAndItsProjects(person,
                    (List<Project>) (List<?>) currentEntityList);
            page.setResponsePage(new PersonPage(page.getPageParameters()));

        }
    }

}
