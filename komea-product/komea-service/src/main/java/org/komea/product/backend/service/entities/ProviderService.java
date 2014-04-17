/**
 *
 */

package org.komea.product.backend.service.entities;



import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.EventTypeCriteria;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This class defines a provider service.
 * 
 * @author sleroy
 */
@Service
public class ProviderService extends AbstractService<Provider, Integer, ProviderCriteria> implements
        IProviderService
{
    
    
    @Autowired
    private IEventTypeService eventTypeService;
    @Autowired
    private ProviderDao       requiredDao;
    
    
    
    public ProviderDao getRequiredDao() {
    
    
        return requiredDao;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public ProviderDao getRequiredDAO() {
    
    
        return requiredDao;
    }
    
    
    @Override
    public void removeProvider(final Provider _provider) {
    
    
        final ProviderType providerType = _provider.getProviderType();
        
        final ProviderCriteria criteria = new ProviderCriteria();
        criteria.createCriteria().andUrlEqualTo(_provider.getUrl());
        deleteByCriteria(criteria);
        
        criteria.clear();
        criteria.createCriteria().andProviderTypeEqualTo(providerType);
        if (countByCriteria(criteria) == 0) {
            final EventTypeCriteria eventTypeCriteria = new EventTypeCriteria();
            eventTypeCriteria.createCriteria().andProviderTypeEqualTo(providerType);
            eventTypeService.deleteByCriteria(eventTypeCriteria);
        }
    }
    
    
    public void setRequiredDao(final ProviderDao _requiredDao) {
    
    
        requiredDao = _requiredDao;
    }
    
    
    @Override
    protected ProviderCriteria createKeyCriteria(final String key) {
    
    
        final ProviderCriteria criteria = new ProviderCriteria();
        criteria.createCriteria().andUrlEqualTo(key);
        return criteria;
    }
    
}
