/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.komea.product.service.dto.EntityKey;



/**
 * @author sleroy
 */
public interface IEntityKpiFormula
{
    
    
    /**
     * Evaluate the entity and provides a value
     * 
     * @param _entityKey
     *            the entity key
     * @return the value.
     */
    Number evaluate(EntityKey _entityKey);
}
