package org.komea.product.rest.client;

<<<<<<< HEAD


=======
>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.alert.Event;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.service.dto.errors.InternalServerException;

<<<<<<< HEAD


public class EventsAPI extends AbstractRestCientAPI implements IEventsAPI
{
    
    
    /**
     * @author sleroy
     */
    private static final class ListEventType extends GenericType<List<Event>>
    {
        //
    }
    
    
    
    private static final String EVENTS_PATH = "events";
    
    
    
=======
public class EventsAPI extends AbstractRestCientAPI implements IEventsAPI {

    /**
     * @author sleroy
     */
    private static final class ListEventType extends GenericType<List<Event>> {
        //
    }

    private static final String EVENTS_PATH = "events";

>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.rest.client.api.IEventsAPI#findEvents(org.komea.product.database.dto.MeasureDTODto)
     */
    @Override
    public List<Event> findEvents(final SearchEventDto _searchEvent)
            throws ConnectException, InternalServerException {
<<<<<<< HEAD
    
    
        final String url = EVENTS_PATH + "/find";
        return post(url, _searchEvent, new ListEventType());
    }
    
    
=======

        final String url = EVENTS_PATH + "/find";
        return post(url, _searchEvent, new ListEventType());
    }

>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
    /*
     * (non-Javadoc)
     * @see org.komea.product.rest.client.api.IEventsAPI#getEvents()
     */
<<<<<<< HEAD
    @Override
    public List<Event> getEvents() throws ConnectException, InternalServerException {
    
    
        final String url = EVENTS_PATH + "/get";
        return get(url, new ListEventType());
    }
    
    
    @Override
    public List<Event> getEvents(final Criticity _severityMin, final int _number)
            throws ConnectException, InternalServerException {
    
    
        final String url = EVENTS_PATH + "/get/" + _severityMin.name() + "/" + _number;
        return get(url, new ListEventType());
    }
    
    
=======
    @Override
    public List<Event> getEvents() throws ConnectException, InternalServerException {

        final String url = EVENTS_PATH + "/get";
        return get(url, new ListEventType());
    }

>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
    //
    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.rest.client.api.IEventsAPI#pushEvent(org.komea.product.database.dto.IEvent)
     */
    @Override
    public void pushEvent(final EventSimpleDto _event)
            throws ConnectException, InternalServerException {
<<<<<<< HEAD
    
    
=======

>>>>>>> ae58d31ddbbf4053410d9e031d5cc440abebdc56
        final String url = EVENTS_PATH + "/push";
        post(url, _event);

    }
}
