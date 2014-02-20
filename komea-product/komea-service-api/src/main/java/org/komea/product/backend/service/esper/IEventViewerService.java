
package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.database.alert.IEvent;



/**
 */
public interface IEventViewerService
{
    
    
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
}
