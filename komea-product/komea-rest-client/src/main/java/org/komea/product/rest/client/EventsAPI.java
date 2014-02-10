
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.core.GenericType;

import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.dto.EventDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.service.dto.errors.InternalServerException;

class EventsAPI extends AbstractRestCientAPI implements IEventsAPI {
    
    private static final String EVENTS_PATH = "events";
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.IEventsAPI#findEvents(org.komea.product.database.dto.SearchEventDto)
     */
    @Override
    public List<EventDto> findEvents(final SearchEventDto _searchEvent) throws ConnectException, InternalServerException {
    
        String url = EVENTS_PATH + "/find";
        return post(url, _searchEvent, new GenericType<List<EventDto>>() {
        });
    }
    
    @Override
    public List<EventDto> getEvents(final Criticity _severityMin, final int _number) throws ConnectException, InternalServerException {
    
        String url = EVENTS_PATH + "/get/" + _severityMin.name() + "/" + _number;
        return get(url, new GenericType<List<EventDto>>() {
        });
    }
    //
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.IEventsAPI#pushEvent(org.komea.product.database.dto.EventDto)
     */
    @Override
    public void pushEvent(final EventDto _event) throws ConnectException, InternalServerException {
    
        String url = EVENTS_PATH + "/push";
        post(url, _event);
        
    }
}
