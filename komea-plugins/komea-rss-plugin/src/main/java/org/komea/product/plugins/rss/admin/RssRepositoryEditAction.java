
package org.komea.product.plugins.rss.admin;



import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.api.IEditAction;



public class RssRepositoryEditAction implements IEditAction<Person>
{
    
    
    private final RssRepositoryPage         editedPersonPage;
    private final IPersonRoleService personRoleDAO;
    
    
    
    public RssRepositoryEditAction(
            final RssRepositoryPage _editedPersonPage,
            final IPersonRoleService _personRoleDAO) {
    
    
        editedPersonPage = _editedPersonPage;
        personRoleDAO = _personRoleDAO;
        
    }
    
    
    @Override
    public void selected(final Person _object) {
    
    
        editedPersonPage.setResponsePage(new RssRepositoryAddPage(editedPersonPage.getPageParameters(),
                _object));
        
        //
        
    }
    
}
