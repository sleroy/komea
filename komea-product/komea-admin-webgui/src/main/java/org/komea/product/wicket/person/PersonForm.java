
package org.komea.product.wicket.person;



import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
import org.komea.product.wicket.widget.EmptyStringValidator;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;



/**
 * Formular to edit properties in the settings page.
 * 
 * @author sleroy
 */
public final class PersonForm extends Form<PersonDto>
{
    
    
    private final PersonDao     personDAO;
    
    private final PersonRoleDao personRoleDAO;
    private final PersonDto     personDto;
    private PersonRole          selectedRole;
    
    private final Integer       key;
    
    
    
    public PersonForm(
            final PersonRoleDao _personRoleDAO,
            final PersonDao _personDAO,
            final String _id,
            final IModel<PersonDto> _dto) {
    
    
        super(_id, _dto);
        personRoleDAO = _personRoleDAO;
        personDAO = _personDAO;
        personDto = _dto.getObject();
        key = personDto.getId();
        
        add(TextFieldBuilder.<String> create("login", personDto, "login").simpleValidator(1, 255)
                .withTooltip("User requires a login.").highlightOnErrors().build());
        add(TextFieldBuilder.<String> create("firstname", personDto, "firstName")
                .withTooltip("User requires a first name.")
                .withValidation(new EmptyStringValidator()).highlightOnErrors().build());
        add(TextFieldBuilder.<String> create("lastname", personDto, "lastName")
                .withValidation(new EmptyStringValidator()).highlightOnErrors()
                .withTooltip("User requires a last name.").build());
        add(TextFieldBuilder.<String> create("email", personDto, "email")
                .withValidation(new EmptyStringValidator())
                .withTooltip("User requires a valid email.").highlightOnErrors().build());
        
        // Creation the drop down.
        final List<PersonRole> selectPersonRoles =
                personRoleDAO.selectByCriteria(new PersonRoleCriteria());
        final PropertyModel<PersonRole> selectionRoleModel =
                new PropertyModel<PersonRole>(this, "selectedRole");
        
        final DropDownChoice<PersonRole> dropDownChoice =
                new DropDownChoice<PersonRole>("role", selectionRoleModel, selectPersonRoles,
                        new ChoiceRenderer<PersonRole>("name"));
        add(dropDownChoice);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        
        AjaxFormValidatingBehavior.addToAllFormComponents(this, "onkeyup", Duration.ONE_SECOND);
        
        // add a button that can be used to submit the form via ajax
        add(new AjaxButton("submit", this)
        {
            
            
            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {
            
            
                error("error found");
                // repaint the feedback panel so errors are shown
                target.add(feedbackPanel);
            }
            
            
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
            
            
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedbackPanel);
            }
        });
        
        
    }
    
    
    /**
     * Validation the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {
    
    
        final Person person = new Person();
        person.setId(key);
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setLastName(personDto.getLastName());
        person.setLogin(personDto.getLogin());
        if (selectedRole != null) {
            person.setIdPersonRole(selectedRole.getId());
        }
        if (person.getId() != null) {
            personDAO.updateByPrimaryKey(person);
        } else {
            personDAO.insert(person);
        }
    }
    
    
}
