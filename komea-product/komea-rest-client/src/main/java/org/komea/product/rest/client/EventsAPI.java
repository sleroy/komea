package org.komea.product.rest.client;

import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.alert.Event;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.service.dto.errors.InternalServerException;

public class EventsAPI extends AbstractRestCientAPI implements IEventsAPI {

    /**
     * @author sleroy
     */
    private static final class ListEventType extends GenericType<List<Event>> {
        //
    }

    private static final String EVENTS_PATH = "events";

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.rest.client.api.IEventsAPI#findEvents(org.komea.product.database.dto.MeasureDTODto)
     */
    @Override
    public List<Event> findEvents(final SearchEventDto _searchEvent)
            throws ConnectException, InternalServerException {

        final String url = EVENTS_PATH + "/find";
        return post(url, _searchEvent, new ListEventType());
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.rest.client.api.IEventsAPI#getEvents()
     */
    @Override
    public List<Event> getEvents() throws ConnectException, InternalServerException {

        final String url = EVENTS_PATH + "/get";
        return get(url, new ListEventType());
    }

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

        final String url = EVENTS_PATH + "/push";
        post(url, _event);

    }
}
