/**
 * 
 */

package org.komea.product.backend.groovy;



import java.util.List;

import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.plugin.api.RequiresSpring;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IEntityKpiFormula;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
@RequiresSpring
public abstract class AbstractGroovyQuery implements IQuery<KpiResult>
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGroovyQuery.class);
    private final EntityType    entityType;
    
    @Autowired
    protected IEntityService    entityService;
    @Autowired
    protected IKPIService       ikpiService;
    
    
    @Autowired
    protected ISpringService    springService;
    @Autowired
    protected IStatisticsAPI    statisticsAPI;
    
    
    
    /**
     * Builds a groovy query
     * 
     * @param _entityType
     */
    public AbstractGroovyQuery(final EntityType _entityType) {
    
    
        super();
        entityType = _entityType;
        
    }
    
    
    /**
     * Process for each entity.
     * 
     * @param _formula
     *            the formula
     * @return the kpi result.
     */
    public KpiResult forEachEntity(final IEntityKpiFormula _formula) {
    
    
        final List<IEntity> entitiesByEntityType =
                entityService.getEntitiesByEntityType(entityType);
        final KpiResult kpiResult = new KpiResult();
        for (final IEntity entity : entitiesByEntityType) {
            final EntityKey key = entity.getEntityKey();
            try {
                kpiResult.put(key, _formula.evaluate(key));
            } catch (final Exception e) {
                LOGGER.error("Could not compute value for the entity : {} for the reason {}", key,
                        e.getMessage(), e);
            }
        }
        return kpiResult;
        
    }
    
    
    /**
     * Returns a spring service implementing the given class.
     */
    public <T> T getService(final Class<T> _class) {
    
    
        return springService.getBean(_class);
    }
    
    
    public KpiValueProxy kpi(final String _kpiName) {
    
    
        return new KpiValueProxy(ikpiService.findKPIOrFail(_kpiName), statisticsAPI);
    }
    
    
}
