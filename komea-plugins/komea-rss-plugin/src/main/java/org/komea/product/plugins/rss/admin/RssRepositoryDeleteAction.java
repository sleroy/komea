
package org.komea.product.plugins.rss.admin;



import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.api.IDeleteAction;



public class RssRepositoryDeleteAction implements IDeleteAction<Person>
{
    
    
    private final IPersonService personDAO;
    
    
    
    public RssRepositoryDeleteAction(final IPersonService _personDAO) {
    
    
        personDAO = _personDAO;
        
        
    }
    
    
    @Override
    public void delete(final Person _object) {
    
    
        personDAO.deleteByPrimaryKey(_object.getId());
        
    }
    
}
