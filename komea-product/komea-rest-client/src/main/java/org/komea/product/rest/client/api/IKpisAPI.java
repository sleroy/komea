
package org.komea.product.rest.client.api;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.errors.InternalServerException;

/**
 * Komea rest api client to manage events
 * the interface call the komea server via the rest api
 * 
 * @author $Author: jguidoux $
 * @since 11 févr. 2014
 */
public interface IKpisAPI {
    
    /**
     * This method return the kpi list
     * 
     * @return the kpi list
     * @throws ConnectException
     *             launch if it can't connect to the server
     * @throws InternalServerException
     *             launch if exception happened in server side
     */
    public List<Kpi> allKpis() throws ConnectException, InternalServerException;
}
