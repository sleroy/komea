
package org.komea.product.backend.service;



import org.komea.product.backend.kpi.EntityWithKPI;
import org.komea.product.backend.service.business.IEntityWithKPIFacade;
import org.komea.product.backend.service.kpi.IEntityWithKPIAdapter;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.api.IEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class EntityWithKPIAdapter implements IEntityWithKPIAdapter
{
    
    
    @Autowired
    private IKPIService         kpiService;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityWithKPIAdapter.class);
    
    
    
    public EntityWithKPIAdapter() {
    
    
        super();
    }
    
    
    @Override
    public <TEntity extends IEntity> IEntityWithKPIFacade<TEntity> adapt(final TEntity _entity) {
    
    
        LOGGER.info("Build adapter for {} ", _entity);
        return new EntityWithKPI(_entity, kpiService.getListOfKpisForEntity(_entity));
    }


    public IKPIService getKpiService() {
    
    
        return kpiService;
    }


    public void setKpiService(IKPIService _kpiService) {
    
    
        kpiService = _kpiService;
    }
    
    
}
