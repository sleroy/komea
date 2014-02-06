
package org.komea.product.backend.exceptions;



import org.komea.product.database.enums.EntityType;



public class EntityNotFoundException extends RuntimeException
{
    
    
    public EntityNotFoundException(final int _entityID, final EntityType _entityType) {
    
    
        super("Entity not found " + _entityID + " type " + _entityType);
    }
    
}
