
package org.komea.product.wicket.person;



import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
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
    private final PersonDto     personDto;
    private PersonRole          personRole;
    private final PersonRoleDao personRoleDAO;
    
    
    
    public PersonForm(
            final PersonRoleDao _personRoleDAO,
            final PersonDao _personDAO,
            final String _id,
            final IModel<PersonDto> _dto) {
    
    
        super(_id, _dto);
        personRoleDAO = _personRoleDAO;
        personDAO = _personDAO;
        personDto = _dto.getObject();
        add(new HiddenField<Integer>("key", Model.<Integer> of(personDto.getId())));
        add(new TextField<String>("login", Model.<String> of(personDto.getLogin())));
        add(new TextField<String>("firstname", Model.<String> of(personDto.getFirstName())));
        add(new TextField<String>("lastname", Model.<String> of(personDto.getLastName())));
        add(new TextField<String>("email", Model.<String> of(personDto.getLogin())));
        final Select<PersonRole> select =
                new Select<PersonRole>("role", new PropertyModel<PersonRole>(this, "personRole"));
        add(select);
        for (final PersonRole personRoleItem : personRoleDAO
                .selectByCriteria(new PersonRoleCriteria())) {
            select.add(new SelectOption<PersonRole>(personRoleItem.getName(), Model
                    .of(personRoleItem)));
        }
        
        
    }
    
    
    /**
     * Validation of the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {
    
    
        final Person person = new Person();
        person.setId(personDto.getId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setLastName(personDto.getLastName());
        person.setLogin(personDto.getLogin());
        if (personRole != null) {
            person.setIdPersonRole(personRole.getId());
        }
        if (person.getId() != null) {
            personDAO.updateByPrimaryKey(person);
        } else {
            personDAO.insert(person);
        }
    }
}
