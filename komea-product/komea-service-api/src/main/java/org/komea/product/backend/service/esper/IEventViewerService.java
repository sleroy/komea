
package org.komea.product.backend.service.esper;


import java.util.List;

import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.EntityType;

/**
 */
public interface IEventViewerService {
    
    /**
     * Returns the global activity.
     */
    List<IEvent> getGlobalActivity();
    
    /**
     * Return an instant view from an EPL Statement
     *
     * @param _eplStatement
     *            the esper statement
     * @since 28 juin 2013
     * @return an instant view.
     */
    List<IEvent> getInstantView(String _eplStatement);
    
    String getEntityKey(EntityType entityType, IEvent event);
}
