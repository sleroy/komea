
package org.komea.product.rest.client.api;



import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.service.dto.errors.InternalServerException;



/**
 * Komea rest api client to manage events
 * the interface call the komea server via the rest api
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 15 janv. 2014
 */
public interface IEventsAPI extends IRestClientAPI
{
    
    
    /**
     * This method find events which have been stored into komea
     * 
     * @param _searchEvent
     *            looking for event of some entities and of dome type
     * @return an event list
     * @throws ConnectException
     *             launch if it can't connect to the server
     * @throws InternalServerException
     *             launch if exception happened in server side
     */
    List<Event> findEvents(final SearchEventDto _searchEvent)
            throws ConnectException, InternalServerException;
    
    
    /**
     * @return
     * @throws InternalServerException
     * @throws ConnectException
     */
    List<Event> getEvents() throws ConnectException, InternalServerException;
    
    
    /**
     * This method find events
     * 
     * @param _severityMin
     *            the severity min of the events to return
     * @param _number
     *            the number of event to return
     * @return an event list
     * @throws ConnectException
     *             launch if it can't connect to the server
     * @throws InternalServerException
     *             launch if exception happened in server side
     */
    List<Event> getEvents(final Criticity _severityMin, final int _number)
            throws ConnectException, InternalServerException;
    
    
    /**
     * This method push a new event into komea
     * 
     * @param _event
     *            the event to push
     * @throws ConnectException
     *             launch if it can't connect to the server
     * @throws InternalServerException
     *             launch if exception happened in server side
     */
    void pushEvent(final EventSimpleDto _event) throws ConnectException, InternalServerException;
}
