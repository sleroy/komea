
package org.komea.product.service.esper;



import java.util.List;

import org.komea.product.database.alert.IAlert;



public interface IEsperAlertSnapshotService
{
    
    
    /**
     * Return an instant view from an EPL Statement
     * 
     * @return an instant view.
     * @since 28 juin 2013
     */
    List<IAlert> getDefaultView();
    
    
    /**
     * Return an instant view from an EPL Statement
     * 
     * @return an instant view.
     * @since 28 juin 2013
     */
    List<IAlert> getInstantView(String _EplStatement);
    
}
