
package org.komea.product.backend.service;



import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;



public interface IEntityService
{
    
    
    /**
     * Loads an entity from the database.
     * 
     * @param _entityType
     *            the entity type
     * @param _key
     *            the key
     * @return the entity
     */
    <TEntity extends IEntity> TEntity getEntity(EntityType _entityType, int _key);
    
    
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
