package org.komea.product.wicket.person;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.form.Form;
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
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.SelectBoxBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 * Formular to edit properties in the settings page.
 *
 * @author sleroy
 */
public final class PersonForm extends Form<Person> {

    /**
     * @author sleroy
     */
    private final IPersonGroupService prService;
    private final Component feedBack;
    private final Person person;
    private final LayoutPage page;
    private final IProjectService projectService;
    private final IPersonService personService;
    private final NameGeneric groupName;
    private final TextField groupField;
    private final UserBdd savUserBdd;
    private List<IHasKey> currentEntityList;
    private List<IHasKey> selectedEntity;
    private String savPassword;
    private final IPasswordEncoder passEncoder;
    private PasswordTextField password;
    private Boolean isAdmin;
    private IPersonRoleService personRole;
    private final boolean isNew;

//    private final TeamSelectorDialog teamDialog;
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
            final IPersonGroupService _prService
    ) {

        super(_id, _compoundPropertyModel);
        this.page = _page;
        this.isNew = _isNew;
        this.passEncoder = _passEncoder;
        this.prService = _prService;
        this.personRole = _personRole;
        projectService = _projectService;
        personService = _personService;
        person = _compoundPropertyModel.getObject();
        selectedEntity = new ArrayList<IHasKey>();
        currentEntityList = new ArrayList<IHasKey>();
        feedBack = new FeedbackPanel("feedback");
        feedBack.setOutputMarkupId(true);
        feedBack.setOutputMarkupPlaceholderTag(true);
        feedBack.setVisible(false);
        add(feedBack);
        this.groupName = new NameGeneric("");
        TextFieldBuilder<String> keyField = TextFieldBuilder.<String>createRequired("login", person, "login")
                .simpleValidator(3, 255).withTooltip("User requires a login.").highlightOnErrors();

        if (isNew) {
            keyField.UniqueStringValidator("login", personService);
        } else {
            keyField.buildTextField().setEnabled(false);
        }

        add(keyField.build());
        PersonGroup selectByPrimaryKey = this.prService.selectByPrimaryKey(this.person.getIdPersonGroup());
        if (selectByPrimaryKey != null) {
            this.groupName.setName(selectByPrimaryKey.getName());
        }
        groupField = TextFieldBuilder.<String>create("group", this.groupName, "name").withTooltip("Use can be put in group").buildTextField();
        add(groupField);

        if (person.getUserBdd() == null) {
            person.setUserBdd(UserBdd.KOMEA);
        }
        savUserBdd = person.getUserBdd();
        add(SelectBoxBuilder.<UserBdd>createWithEnumRequire("userBdd", person,
                UserBdd.class).build());

        if (this.person.getId() != null) {

            currentEntityList = (List<IHasKey>) (List<?>) projectService.getProjectsOfPerson(this.person.getId());
        }

        DialogFactory.addListWithSelectDialog(this,
                "table",
                "dialogAddPerson",
                "btnAddPerson",
                "btnDelPerson",
                "selectedEntity",
                "Choose person",
                currentEntityList,
                selectedEntity,
                (List<IHasKey>) (List<?>) this.projectService.selectAll(),
                projectService);

        initClassicField();
        initSubmitbutton();
        initSimpleButton();

    }

    public void initClassicField() {
        add(TextFieldBuilder.<String>createRequired("firstname", person, "firstName")
                .simpleValidator(2, 255).withTooltip("User requires a first name.")
                .highlightOnErrors().build());
        add(TextFieldBuilder.<String>createRequired("lastname", person, "lastName")
                .simpleValidator(2, 255).highlightOnErrors()
                .withTooltip("User requires a last name.").build());

        isAdmin = Boolean.FALSE;
        Integer idPersonRole = person.getIdPersonRole();
        if (idPersonRole != null) {
            PersonRole adminRole = this.personRole.getAdminRole();
            if (idPersonRole.equals(adminRole.getId())) {
                isAdmin = Boolean.TRUE;
            }

        }

        savPassword = person.getPassword();
        person.setPassword("00000");
        password = (PasswordTextField) TextFieldBuilder.createPassword("password", person, "password")
                .simpleValidator(5, 255).withTooltip("User requires a password").highlightOnErrors()
                .build();
        password.setResetPassword(false);
        password.setOutputMarkupId(true);
        password.setOutputMarkupPlaceholderTag(true);
        if (isAdmin) {
            password.setVisible(true);
        } else {
            password.setVisible(false);
        }
        add(password);

        PropertyModel<Boolean> modelchcekBox = new PropertyModel<Boolean>(this, "isAdmin");
        AjaxCheckBox checkBox = new AjaxCheckBox("isAdmin", modelchcekBox) {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
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
        TextField<String> emailTextField = TextFieldBuilder.<String>createRequired("email", person, "email")
                .withTooltip("User requires a valid email.").highlightOnErrors().buildTextField();
        emailTextField.add(EmailAddressValidator.getInstance());
        add(emailTextField);
    }

    public void initSubmitbutton() {

        add(new AjaxButton("submit") {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {
//                errorModel.setObject("has-error");
                feedBack.setVisible(true);
                target.add(feedBack);

            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                info("Submitted information");
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

                personService.saveOrUpdatePerson(person, (List<Project>) (List<?>) currentEntityList);
                page.setResponsePage(new PersonPage(page.getPageParameters()));

            }

        });

    }

    public void initSimpleButton() {
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new PersonPage(page.getPageParameters()));
            }
        });
    }

    public List<IHasKey> getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(List<IHasKey> selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public Person getPerson() {
        return person;
    }

    public NameGeneric getGroupName() {
        return groupName;
    }

    public TextField getGroupField() {
        return groupField;
    }

    public Boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
