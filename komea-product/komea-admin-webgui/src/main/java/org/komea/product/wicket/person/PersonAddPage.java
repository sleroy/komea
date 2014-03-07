
package org.komea.product.wicket.person;



import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.api.IFormularService;
import org.komea.product.backend.forms.PersonFormData;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.KomeaEntry;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.dialogs.TeamSelectDialog;
import org.komea.product.wicket.widget.AjaxDialogButton;
import org.komea.product.wicket.widget.builders.DropDownBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;
import org.komea.product.wicket.widget.gravatar.GravatarDefaultImage;
import org.komea.product.wicket.widget.gravatar.GravatarImage;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class PersonAddPage extends LayoutPage
{
    
    
    /**
     * Defines the formular to edit a person.
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
                // repaint the feedBack panel so that it is hidden
                target.add(feedBack);
                setResponsePage(new PersonPage(new PageParameters()));
            }
        }
        
        
        
        private final Person person;
        
        
        private PersonGroup  personGroup;
        
        private Project      selectedProject;
        
        private PersonRole   selectedRole;
        
        
        
        public PersonForm(
                final PersonFormData _personFormData,
                final String _id,
                final CompoundPropertyModel<Person> _compoundPropertyModel) {
        
        
            super(_id, _compoundPropertyModel);
            person = _compoundPropertyModel.getObject();
            feedBack.setVisible(false);
            add(new GravatarImage("avatar", _compoundPropertyModel.getObject().getEmail(),
                    GravatarDefaultImage.MONSTERID, 150));
            add(TextFieldBuilder.<String> createRequired("login", person, "login")
                    .simpleValidator(3, 255).withTooltip("User requires a login.")
                    .highlightOnErrors().build());
            add(TextFieldBuilder.<String> createRequired("firstname", person, "firstName")
                    .simpleValidator(2, 255).withTooltip("User requires a first name.")
                    .highlightOnErrors().simpleValidator(2, 255).build());
            add(TextFieldBuilder.<String> createRequired("lastname", person, "lastName")
                    .simpleValidator(2, 255).highlightOnErrors()
                    .withTooltip("User requires a last name.").build());
            add(TextFieldBuilder.<String> createRequired("email", person, "email")
                    .withTooltip("User requires a valid email.").highlightOnErrors().build());
            
            // Creation the drop down.
            add(DropDownBuilder.buildDropdown("selectedRole", this, "role", "name",
                    _personFormData.getPersonRoles()));
            add(DropDownBuilder.buildDropdown("selectedProject", this, "project", "name",
                    _personFormData.getProjects()));
            add(new AjaxDialogButton("teamlink", "Pick a team", teamDialog));
            
            // AjaxFormValidatingBehavior.addToAllFormComponents(this, "onkeyup", Duration.ONE_SECOND);
            
            // add a button that can be used to submit the form via ajax
            add(new SubmitButton("submit", this));
            
        }
        
        
        /**
         * Validation the formular : settings are updated from the DTO
         */
        @Override
        protected void onSubmit() {
        
        
            personService.saveOrUpdate(person, selectedProject, selectedRole, personGroup);
        }
        
    }
    
    
    
    private final FeedbackPanel    feedBack;
    
    
    @SpringBean
    private IFormularService       formularService;
    
    
    private Person                 person;
    
    @SpringBean
    private IPersonRoleService     personRoleDAO;
    
    
    @SpringBean
    private IPersonService         personService;
    
    @SpringBean
    private IProjectService        projectDAO;
    
    private Project                selectedProject;
    
    
    private PersonRole             selectedRole;
    
    
    private final TeamSelectDialog teamDialog;
    
    
    private PersonGroup            teamGroup;
    
    
    
    public PersonAddPage(final PageParameters _parameters) {
    
    
        this(_parameters, new Person());
        
    }
    
    
    public PersonAddPage(final PageParameters _parameters, final Person _person) {
    
    
        super(_parameters);
        
        
        feedBack = new FeedbackPanel("feedBack");
        feedBack.setOutputMarkupId(true);
        add(feedBack);
        final PersonFormData newPersonForm = formularService.newPersonForm();
        
        teamDialog =
                new TeamSelectDialog("teamDialog", "Simple teamDialog box",
                        newPersonForm.getTeams())
                {
                    
                    
                    @Override
                    public void onClose(final AjaxRequestTarget target, final DialogButton button) {
                    
                    
                        if (button.equals(btnSure)) {
                            teamGroup = getSelectedTeam();
                        }
                    }
                };
        
        this.add(teamDialog); // TODO: open it, using teamDialog.open(target);
        
        final PersonForm personForm =
                new PersonForm(newPersonForm, "form", new CompoundPropertyModel<Person>(_person));
        add(personForm);
        
    }
    
    
    /**
     * @return the formularService
     */
    public IFormularService getFormularService() {
    
    
        return formularService;
    }
    
    
    @Override
    public List<? extends Entry<String, Class>> getMiddleLevelPages() {
    
    
        return Collections.singletonList(new KomeaEntry<String, Class>(
                getString("PersonPage.title"), PersonPage.class));
        
    }
    
    
    /**
     * @return the personService
     */
    public IPersonService getPersonDAO() {
    
    
        return personService;
    }
    
    
    /**
     * @return the personRoleDAO
     */
    public IPersonRoleService getPersonRoleDAO() {
    
    
        return personRoleDAO;
    }
    
    
    /**
     * @return the projectDAO
     */
    public IProjectService getProjectDAO() {
    
    
        return projectDAO;
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("PersonAddPage.title");
    }
    
    
    /**
     * @param _formularService
     *            the formularService to set
     */
    public void setFormularService(final IFormularService _formularService) {
    
    
        formularService = _formularService;
    }
    
    
    /**
     * @param _personDAO
     *            the personService to set
     */
    public void setPersonDAO(final IPersonService _personDAO) {
    
    
        personService = _personDAO;
    }
    
    
    /**
     * @param _personRoleDAO
     *            the personRoleDAO to set
     */
    public void setPersonRoleDAO(final IPersonRoleService _personRoleDAO) {
    
    
        personRoleDAO = _personRoleDAO;
    }
    
    
    /**
     * @param _projectDAO
     *            the projectDAO to set
     */
    public void setProjectDAO(final IProjectService _projectDAO) {
    
    
        projectDAO = _projectDAO;
    }
    
    
}
