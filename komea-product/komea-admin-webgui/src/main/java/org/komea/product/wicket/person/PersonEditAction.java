
package org.komea.product.wicket.person;



import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.api.IEditAction;



public class PersonEditAction implements IEditAction<Person>
{
    
    
    private final PersonPage         editedPersonPage;
    private final IPersonRoleService personRoleDAO;
    
    
    
    public PersonEditAction(
            final PersonPage _editedPersonPage,
            final IPersonRoleService _personRoleDAO) {
    
    
        editedPersonPage = _editedPersonPage;
        personRoleDAO = _personRoleDAO;
        
    }
    
    
    @Override
    public void selected(final Person _object) {
    
    
        editedPersonPage.setResponsePage(new PersonAddPage(editedPersonPage.getPageParameters(),
                _object));
        
        //
        
    }
    
}
