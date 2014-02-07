
package org.komea.product.backend.service;


import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.KpiKey;

public interface IEntityService
{
    
    /**
     * Loads an entity from the database.
     * 
     * @param _entityType
     *            the entity type
     * @param _key
     *            the key
     * @return the entity or null if the entity does not exist
     */
    <TEntity extends IEntity> TEntity getEntity(EntityType _entityType, int _key);
    
    
    IEntity getEntityAssociatedToKpi(KpiKey _kpiKey);
    
    
    /**
     * Returns the entity or fail
     * 
     * @param _entityType
     *            the entity type
     * @param _entityID
     *            the entity ID
     * @return the entity.
     */
    IEntity getEntityOrFail(EntityType _entityType, int _entityID);
    
    /**
     * Loads a list of entities from the database.
     * 
     * @param _entityType
     *            the entity type
     * @param _keys
     *            the keys
     * @return the entity
     */
    <TEntity extends IEntity> List<TEntity> loadEntities(EntityType _entityType, List<Integer> _keys);
}
