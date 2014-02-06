
package org.komea.product.backend.service.kpi;



import org.komea.product.backend.service.business.IEntityWithKPIFacade;
import org.komea.product.database.api.IEntity;



/**
 * This interface defines an adapter to
 * 
 * @author sleroy
 */
public interface IEntityWithKPIAdapter
{
    
    
    /**
     * Adapt a person into a entity with KPI.
     * 
     * @param _person
     *            the person
     * @return the person.
     */
    <TEntity extends IEntity> IEntityWithKPIFacade<TEntity> adapt(TEntity _person);
}
