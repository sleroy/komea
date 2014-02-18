
package org.komea.product.wicket.person;



import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.backend.forms.PersonFormData;
import org.komea.product.backend.service.entities.PersonService;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.widget.TeamSelectorDialog;
import org.komea.product.wicket.widget.builders.DropDownBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;



/**
 * Formular to edit properties in the settings page.
 * 
 * @author sleroy
 */
public final class PersonForm extends Form<Person>
{
    
    
    /**
     * @author sleroy
     */
    private final class SubmitButton extends AjaxButton
    {
        
        
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
            setResponsePage(new PersonPage(new PageParameters()));
        }
    }
    
    
    
    /**
     * @author sleroy
     */
    private final class TeamButton extends AjaxButton
    {
        
        
        /**
         * @param _id
         * @param _form
         */
        private TeamButton(final String _id) {
        
        
            super(_id);
        }
        
        
        @Override
        protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
        
        
            teamDialog.open(target);
        }
    }
    
    
    
    private final Component          feedBack;
    
    private final Person             person;
    
    
    private PersonGroup              personGroup;
    private final PersonService      personService;
    
    private Project                  selectedProject;
    
    private PersonRole               selectedRole;
    
    private final TeamSelectorDialog teamDialog;
    
    
    
    public PersonForm(
            final PersonService _personService,
            final PersonFormData _personFormData,
            final String _id,
            final Component _feedBack,
            final CompoundPropertyModel<Person> _compoundPropertyModel) {
    
    
        super(_id, _compoundPropertyModel);
        personService = _personService;
        feedBack = _feedBack;
        person = _compoundPropertyModel.getObject();
        feedBack.setVisible(false);
        add(TextFieldBuilder.<String> createRequired("login", person, "login")
                .simpleValidator(3, 255).withTooltip("User requires a login.").highlightOnErrors()
                .build());
        add(TextFieldBuilder.<String> createRequired("firstname", person, "firstName")
                .simpleValidator(2, 255).withTooltip("User requires a first name.")
                .highlightOnErrors().simpleValidator(2, 255).build());
        add(TextFieldBuilder.<String> createRequired("lastname", person, "lastName")
                .simpleValidator(2, 255).highlightOnErrors()
                .withTooltip("User requires a last name.").build());
        add(TextFieldBuilder.<String> createRequired("email", person, "email")
                .withTooltip("User requires a valid email.").highlightOnErrors().build());
        // add(new FormComponentFeedbackIndicator(id));
        // Creation the drop down.
        add(DropDownBuilder.buildDropdown("selectedRole", this, "role", "name",
                _personFormData.getPersonRoles()));
        add(DropDownBuilder.buildDropdown("selectedProject", this, "project", "name",
                _personFormData.getProjects()));
        
        // AjaxFormValidatingBehavior.addToAllFormComponents(this, "onkeyup", Duration.ONE_SECOND);
        
        // add a button that can be used to submit the form via ajax
        add(new SubmitButton("submit", this));
        teamDialog = new TeamSelectorDialog(_personFormData.getTeams(), "teamDialog");
        this.add(teamDialog);
        
    }
    
    
    /**
     * Validation the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {
    
    
        personService.saveOrUpdate(person, selectedProject, selectedRole, personGroup);
    }
    
    
}
