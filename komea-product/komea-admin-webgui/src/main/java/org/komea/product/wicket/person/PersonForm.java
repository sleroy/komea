package org.komea.product.wicket.person;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.backend.forms.PersonFormData;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.persongroup.team.TeamPage;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.widget.TeamSelectorDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.DropDownBuilder;
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
    private final class SubmitButton extends AjaxButton {

        /**
         * @param _id
         * @param _form
         */
        private SubmitButton(final String _id, final Form<?> _form) {

            super(_id, _form);
        }

        @Override
        protected void onError(final AjaxRequestTarget target, final Form<?> form) {

            feedBack.setVisible(true);
            error("Error found in the formular");
            target.add(feedBack);
        }

        @Override
        protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

            feedBack.setVisible(false);
            info("Submitted information");
            // repaint the feedback panel so that it is hidden
            target.add(feedBack);
            
        }
    }

    /**
     * @author sleroy
     */
    private final IPersonGroupService prService;
    private final Component feedBack;
    private final Person person;
    private final LayoutPage page;
    private PersonGroup personGroup;
    private final IPersonService personService;
    private Project selectedProject;
    private PersonRole selectedRole;
    private final NameGeneric groupName;
    private final TextField groupField;

//    private final TeamSelectorDialog teamDialog;
    public PersonForm(
            final IPersonService _personService,
            final PersonFormData _personFormData,
            final String _id,
            final Component _feedBack,
            final CompoundPropertyModel<Person> _compoundPropertyModel,
            final LayoutPage _page,
            final IPersonGroupService _prService
    ) {

        super(_id, _compoundPropertyModel);
        this.page = _page;
        this.prService = _prService;
        personService = _personService;
        feedBack = _feedBack;
        person = _compoundPropertyModel.getObject();
        feedBack.setVisible(false);
        this.groupName = new NameGeneric("");

        add(TextFieldBuilder.<String>createRequired("login", person, "login")
                .simpleValidator(3, 255).withTooltip("LdapUser requires a login.").highlightOnErrors()
                .build());
        add(TextFieldBuilder.<String>createRequired("firstname", person, "firstName")
                .simpleValidator(2, 255).withTooltip("LdapUser requires a first name.")
                .highlightOnErrors().simpleValidator(2, 255).build());
        add(TextFieldBuilder.<String>createRequired("lastname", person, "lastName")
                .simpleValidator(2, 255).highlightOnErrors()
                .withTooltip("LdapUser requires a last name.").build());
        add(TextFieldBuilder.<String>createRequired("email", person, "email")
                .withTooltip("LdapUser requires a valid email.").highlightOnErrors().build());
        // add(new FormComponentFeedbackIndicator(id));
        // Creation the drop down.
        PersonGroup selectByPrimaryKey = this.prService.selectByPrimaryKey(this.person.getIdPersonGroup());
        if (selectByPrimaryKey != null) {
            this.groupName.setName(selectByPrimaryKey.getName());
        }
        groupField = TextFieldBuilder.<String>create("group", this.groupName, "name").withTooltip("Use can be put in group").build();
        add(groupField);

//        add(DropDownBuilder.buildDropdown("selectedRole", this, "role", "name",
//                _personFormData.getPersonRoles()));
        add(DropDownBuilder.buildDropdown("selectedProject", this, "project", "name",
                _personFormData.getProjects()));

        add(new SubmitButton("submit", this));

        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new PersonPage(page.getPageParameters()));
            }
        });

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

    /**
     * Validation the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {
        personService.saveOrUpdate(person); // full save comme les autres
        setResponsePage(new PersonPage(new PageParameters()));
    }

}
