package org.komea.product.rest.client.api;

<<<<<<< HEAD


=======
>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.alert.Event;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.service.dto.errors.InternalServerException;



/**
 * Komea rest api client to manage events the interface call the komea server
 * via the rest api
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 15 janv. 2014
 */
<<<<<<< HEAD
public interface IEventsAPI extends IRestClientAPI
{
    
    
=======
public interface IEventsAPI extends IRestClientAPI {

>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
    /**
     * This method find events which have been stored into komea
     *
     * @param _searchEvent looking for event of some entities and of dome type
     * @return an event list
     * @throws ConnectException launch if it can't connect to the server
     * @throws InternalServerException launch if exception happened in server
     * side
     */
    List<Event> findEvents(final SearchEventDto _searchEvent)
            throws ConnectException, InternalServerException;
<<<<<<< HEAD
    
    
    /**
     * @return
     * @throws InternalServerException
     * @throws ConnectException
     */
    List<Event> getEvents() throws ConnectException, InternalServerException;
    
    
=======

>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
    /**
     * @return @throws InternalServerException
     * @throws ConnectException
     */
<<<<<<< HEAD
    List<Event> getEvents(final Criticity _severityMin, final int _number)
            throws ConnectException, InternalServerException;
    
    
=======
    List<Event> getEvents() throws ConnectException, InternalServerException;

>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
    /**
     * This method push a new event into komea
     *
     * @param _event the event to push
     * @throws ConnectException launch if it can't connect to the server
     * @throws InternalServerException launch if exception happened in server
     * side
     */
    void pushEvent(final EventSimpleDto _event) throws ConnectException, InternalServerException;
}
