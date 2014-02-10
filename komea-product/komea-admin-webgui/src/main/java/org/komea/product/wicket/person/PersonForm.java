
package org.komea.product.wicket.person;



import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;



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
        add(new TextField<String>("login", new PropertyModel<String>(personDto, "login")));
        add(new TextField<String>("firstname", new PropertyModel<String>(personDto, "firstName")));
        add(new TextField<String>("lastname", new PropertyModel<String>(personDto, "lastName")));
        final TextField<String> textField =
                new TextField<String>("email", new PropertyModel<String>(personDto, "email"));
        textField.add(EmailAddressValidator.getInstance());
        add(textField);
        // Creation the drop down.
        final List<PersonRole> selectPersonRoles =
                personRoleDAO.selectByCriteria(new PersonRoleCriteria());
        final PropertyModel<PersonRole> selectionRoleModel =
                new PropertyModel<PersonRole>(this, "selectedRole");
        
        final DropDownChoice<PersonRole> dropDownChoice =
                new DropDownChoice<PersonRole>("role", selectionRoleModel, selectPersonRoles,
                        new ChoiceRenderer<PersonRole>("name"));
        add(dropDownChoice);
        
        
    }
    
    
    /**
     * Validation the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {
    
    
        debug("Submitting person...");
        
        final Person person = new Person();
        person.setId(personDto.getId());
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
