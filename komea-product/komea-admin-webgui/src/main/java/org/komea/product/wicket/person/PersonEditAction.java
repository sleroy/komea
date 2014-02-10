
package org.komea.product.wicket.person;



import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonRole;
import org.komea.product.wicket.widget.api.IEditAction;



public class PersonEditAction implements IEditAction<Person>
{
    
    
    private final PersonRoleDao personRoleDAO;
    private final PersonPage    editedPersonPage;
    
    
    
    public PersonEditAction(final PersonPage _editedPersonPage, final PersonRoleDao _personRoleDAO) {
    
    
        editedPersonPage = _editedPersonPage;
        personRoleDAO = _personRoleDAO;
        
    }
    
    
    @Override
    public void selected(final Person _object) {
    
    
        final PersonDto editedPerson = new PersonDto();
        editedPerson.setLogin(_object.getLogin());
        editedPerson.setFirstName(_object.getFirstName());
        editedPerson.setLastName(_object.getLastName());
        editedPerson.setEmail(_object.getEmail());
        final PersonRole personRole = personRoleDAO.selectByPrimaryKey(_object.getIdPersonRole());
        if (personRole != null) {
            editedPerson.setRole(personRole.getName());
        } else {
            editedPerson.setRole("");
        }
        editedPerson.setId(_object.getId());
        editedPersonPage.setResponsePage(new PersonPage(editedPersonPage.getPageParameters(),
                editedPerson));
        
        //
        
    }
    
}
