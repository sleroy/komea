package org.komea.product.wicket.person;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.forms.PersonFormData;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.utils.SelectMultipleDialog;
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
    private PersonGroup personGroup;
    private final IPersonService personService;
    private PersonRole selectedRole;
    private final NameGeneric groupName;
    private final TextField groupField;
    private final IModel<String> errorModel;
    private final UserBdd savUserBdd;
    private List<Project> currentEntityList;
    private List<Project> selectedEntity;


//    private final TeamSelectorDialog teamDialog;
    public PersonForm(
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
        this.prService = _prService;
        projectService=_projectService;
        personService = _personService;
        person = _compoundPropertyModel.getObject();
        selectedEntity = new ArrayList<Project>();
        currentEntityList = new ArrayList<Project>();
        feedBack = new FeedbackPanel("feedback");
        add(feedBack);
        feedBack.setOutputMarkupId(true);
        feedBack.setVisible(false);
        this.groupName = new NameGeneric("");

        final WebMarkupContainer classLogin = new WebMarkupContainer("class_login");
        add(classLogin);
        errorModel = new Model<String>("");
//        errorModel.setObject("has-error");
//        final Model<String> successModel = new Model<String>("has-success");
        classLogin.add(AttributeModifier.append("class", errorModel));
        classLogin.setOutputMarkupId(true);
        classLogin.add(TextFieldBuilder.<String>createRequired("login", person, "login")
                .simpleValidator(3, 255).withTooltip("User requires a login.").highlightOnErrors()
                .build());

        PersonGroup selectByPrimaryKey = this.prService.selectByPrimaryKey(this.person.getIdPersonGroup());
        if (selectByPrimaryKey != null) {
            this.groupName.setName(selectByPrimaryKey.getName());
        }
        groupField = TextFieldBuilder.<String>create("group", this.groupName, "name").withTooltip("Use can be put in group").build();
        add(groupField);

        if (person.getUserBdd() == null) {
            person.setUserBdd(UserBdd.KOMEA);
        }
        savUserBdd = person.getUserBdd();
        add(SelectBoxBuilder.<UserBdd>createWithEnum("userBdd", person,
                UserBdd.class).build());

        if (this.person.getId() != null) {

            currentEntityList = projectService.getProjectsOfPerson(this.person.getId());
        }
        initProjectManagement();
        initClassicField();
        initSubmitbutton();
        initSimpleButton();

    }

    public void initClassicField() {
        add(TextFieldBuilder.<String>createRequired("firstname", person, "firstName")
                .simpleValidator(2, 255).withTooltip("User requires a first name.")
                .highlightOnErrors().simpleValidator(2, 255).build());
        add(TextFieldBuilder.<String>createRequired("lastname", person, "lastName")
                .simpleValidator(2, 255).highlightOnErrors()
                .withTooltip("User requires a last name.").build());
        add(TextFieldBuilder.<String>createRequired("email", person, "email")
                .withTooltip("User requires a valid email.").highlightOnErrors().build());
    }

    public void initSubmitbutton() {

        add(new AjaxButton("submit", errorModel) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {
//                errorModel.setObject("has-error");

            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                Person nPerson = new Person();
                nPerson.setEmail(person.getEmail());
                nPerson.setFirstName(person.getFirstName());
                nPerson.setIdPersonGroup(person.getIdPersonGroup());
                nPerson.setLastName(person.getLastName());
                nPerson.setLogin(person.getLogin());
                nPerson.setUserBdd(savUserBdd);
                nPerson.setPassword(person.getPassword());
                nPerson.setIdPersonRole(person.getIdPersonRole());
                nPerson.setId(person.getId());
                
                personService.saveOrUpdatePerson(person, currentEntityList);
                
                if (nPerson.getId() == null) {
                    personService.insert(nPerson);
                } else {
                    personService.updateByPrimaryKey(nPerson);
                }
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

    public void initProjectManagement() {
        IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();

        final ListMultipleChoice<IEntity> listEntite = new ListMultipleChoice<IEntity>("table", new PropertyModel<List<IEntity>>(this, "selectedEntity"), currentEntityList);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        add(listEntite);

        List<IEntity> allTeamsPG = (List<IEntity>) (List<?>) this.projectService.selectAll();
        final SelectMultipleDialog<IEntity> dialogProject = new SelectMultipleDialog<IEntity>("dialogAddPerson", "Choose project", allTeamsPG) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                List<IEntity> selected = getSelected();
                for (IEntity iEntity : selected) {
                    if (iEntity != null) {
                        Project selectByPrimaryKey1 = projectService.selectByPrimaryKey(iEntity.getId());
                        if (!currentEntityList.contains(selectByPrimaryKey1)) {
                            currentEntityList.add(selectByPrimaryKey1);
                        }
                        target.add(listEntite);
                    }
                }

            }

        };
        add(dialogProject);
        dialogProject.setFilter((List<IEntity>) (List<?>) currentEntityList);
        add(new AjaxLinkLayout<Object>("btnAddPerson", null) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogProject.open(art);
            }
        });

        add(new AjaxButton("btnDelPerson") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                currentEntityList.removeAll(selectedEntity);


                target.add(listEntite);
            }
        });
    }

    public List<Project> getCurrentEntityList() {
        return currentEntityList;
    }

    public void setCurrentEntityList(List<Project> currentEntityList) {
        this.currentEntityList = currentEntityList;
    }

    public List<Project> getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(List<Project> selectedEntity) {
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

}
