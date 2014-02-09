
package org.komea.product.wicket.person;



import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.api.IDeleteAction;



public class PersonDeleteAction implements IDeleteAction<Person>
{
    
    
    private final PersonDao personDAO;
    
    
    
    public PersonDeleteAction(final PersonDao _personDAO) {
    
    
        personDAO = _personDAO;
        
        
    }
    
    
    @Override
    public void delete(final Person _object) {
    
    
        personDAO.deleteByPrimaryKey(_object.getId());
        
    }
    
}
