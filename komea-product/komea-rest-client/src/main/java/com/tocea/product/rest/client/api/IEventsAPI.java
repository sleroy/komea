
package com.tocea.product.rest.client.api;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.dto.EventDto;
import org.komea.product.database.dto.SearchEventsDto;

/**
 * Komea interface to manage events
 * the interface call the komea server via the rest api
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 15 janv. 2014
 */
public interface IEventsAPI extends IRestClientAPI
{
    
    /**
     * This method push a new event into komea
     * 
     * @param _event
     *            the event to push
     * @throws ConnectException
     *             launch if it can't connect to the server
     */
    void pushEvent(final EventDto _event) throws ConnectException;
    
    /**
     * This method find events which have been stored into komea
     * 
     * @param _searchEvent
     *            looking for event of some entities and of dome type
     * @return an event list
     * @throws ConnectException
     *             launch if it can't connect to the server
     */
    List<EventDto> findEvents(final SearchEventsDto _searchEvent) throws ConnectException;
    
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
     */
    List<EventDto> getEvents(final String _severityMin, final int _number) throws ConnectException;
}
