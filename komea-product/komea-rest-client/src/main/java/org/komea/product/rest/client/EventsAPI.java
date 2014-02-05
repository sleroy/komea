
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.dto.EventDto;
import org.komea.product.database.dto.SearchEventsDto;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.rest.util.GenericListType;

class EventsAPI extends AbstractRestCientAPI implements IEventsAPI
{
    
    private static final String EVENTS_PATH = "events";
    
    @Override
    public void pushEvent(final EventDto _event) throws ConnectException {
    
        String url = EVENTS_PATH + "/push";
        post(url, _event);
        
    }
    
    @Override
    public List<EventDto> findEvents(final SearchEventsDto _searchEvent) throws ConnectException {
    
        String url = EVENTS_PATH + "/find";
        return post(url, _searchEvent, new GenericListType<EventDto>());
    }
    @Override
    public List<EventDto> getEvents(final String _severityMin, final int _number) throws ConnectException {
    
        String url = EVENTS_PATH + "/find";
        return get(url, new GenericListType<EventDto>(), _severityMin, String.valueOf(_number));
    }
    //
}
