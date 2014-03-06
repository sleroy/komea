package org.komea.product.database.api;

import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;

/**
 * THis interfae defines a facade that provides basic informations on a entity.
 * Informations are the id, the underlying object and list of kpis.
 *
 * @author sleroy
 * @param <T>
 */
public interface IEntity extends IHasKey {

    /**
     * Returns the entitty type.
     *
     * @return the entity type.
     */
    EntityType entityType();

    /**
     * Returns the entity key.
     *
     * @return the entity key.
     */
    EntityKey getEntityKey();

    String getKey();

    String getDisplayName();

}
