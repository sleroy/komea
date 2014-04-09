
package org.komea.product.backend.exceptions;



import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;



/**
 */
public class EntityNotFoundException extends RuntimeException
{
    
    
    /**
     * @param _entityKey
     */
    public EntityNotFoundException(final EntityKey _entityKey) {
    
    
        this(_entityKey.getId(), _entityKey.getEntityType());
    }
    
    
    /**
     * Constructor for EntityNotFoundException.
     * 
     * @param _entityID
     *            int
     * @param _entityType
     *            EntityType
     */
    public EntityNotFoundException(final Integer _entityID, final EntityType _entityType) {
    
    
        super("Entity not found " + _entityID + " type " + _entityType);
    }
}
