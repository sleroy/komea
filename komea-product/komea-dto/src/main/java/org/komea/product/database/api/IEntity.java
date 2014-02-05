
package org.komea.product.database.api;



import org.komea.product.database.enums.EntityType;



/**
 * THis interfae defines a facade that provides basic informations on a entity. Informations are the id, the underlying object and list of
 * kpis.
 * 
 * @author sleroy
 * @param <T>
 */
public interface IEntity
{
    
    
    Integer getId();
    
    
    /**
     * Returns the enitty type.
     * 
     * @return the entity type.
     */
    EntityType getType();
    
    
}
