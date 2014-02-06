
package org.komea.product.backend.service.esper;



import java.util.List;

import org.komea.product.database.alert.IAlert;



public interface IAlertViewerService
{
    
    
    /**
     * Return an instant view from an EPL Statement
     * 
     * @param _eplStatement
     *            the esper statement
     * @return an instant view.
     * @since 28 juin 2013
     */
    List<IAlert> getInstantView(String _eplStatement);
    
}
