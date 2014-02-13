
package org.komea.product.wicket.person;



import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
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
    
    private final Component     feedBack;
    
    
    
    public PersonForm(
            final PersonRoleDao _personRoleDAO,
            final PersonDao _personDAO,
            final String _id,
            final Component _feedBack,
            final IModel<PersonDto> _dto) {
    
    
        super(_id, _dto);
        personRoleDAO = _personRoleDAO;
        personDAO = _personDAO;
        feedBack = _feedBack;
        personDto = _dto.getObject();
        key = personDto.getId();
        
        add(TextFieldBuilder.<String> createRequired("login", personDto, "login")
                .simpleValidator(3, 255).withTooltip("User requires a login.").highlightOnErrors()
                .build());
        add(TextFieldBuilder.<String> createRequired("firstname", personDto, "firstName")
                .simpleValidator(2, 255).withTooltip("User requires a first name.")
                .highlightOnErrors().simpleValidator(2, 255).build());
        add(TextFieldBuilder.<String> createRequired("lastname", personDto, "lastName")
                .simpleValidator(2, 255).highlightOnErrors()
                .withTooltip("User requires a last name.").build());
        add(TextFieldBuilder.<String> createRequired("email", personDto, "email")
                .withTooltip("User requires a valid email.").highlightOnErrors().build());
        // add(new FormComponentFeedbackIndicator(id));
        // Creation the drop down.
        final List<PersonRole> selectPersonRoles =
                personRoleDAO.selectByCriteria(new PersonRoleCriteria());
        final PropertyModel<PersonRole> selectionRoleModel =
                new PropertyModel<PersonRole>(this, "selectedRole");
        
        final DropDownChoice<PersonRole> dropDownChoice =
                new DropDownChoice<PersonRole>("role", selectionRoleModel, selectPersonRoles,
                        new ChoiceRenderer<PersonRole>("name"));
        add(dropDownChoice);
        
        AjaxFormValidatingBehavior.addToAllFormComponents(this, "onkeyup", Duration.ONE_SECOND);
        
        // add a button that can be used to submit the form via ajax
        add(new AjaxButton("submit", this)
        {
            
            
            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {
            
            
                error("error found");
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }
            
            
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
            
            
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                setResponsePage(new PersonPage(new PageParameters()));
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
