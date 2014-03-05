/**
 * 
 */

package org.komea.product.wicket.providers;



import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.database.model.Provider;
import org.komea.product.wicket.widget.api.IEditAction;



/**
 * This class defines an edit action.
 * 
 * @author sleroy
 */
public class ProviderEditAction implements IEditAction<Provider>
{
    
    
    private final ProviderPage     providerPage;
    private final IProviderService providerService;
    
    
    
    /**
     * @param _providerPage
     * @param _providerService
     */
    public ProviderEditAction(
            final ProviderPage _providerPage,
            final IProviderService _providerService) {
    
    
        super();
        providerPage = _providerPage;
        providerService = _providerService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.wicket.widget.api.IEditAction#selected(java.lang.Object)
     */
    @Override
    public void selected(final Provider _object) {
    
    
        // TODO Auto-generated method stub
        
    }
    
}
