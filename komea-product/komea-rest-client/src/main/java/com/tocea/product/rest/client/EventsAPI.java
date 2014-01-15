
package com.tocea.product.rest.client;


import java.net.ConnectException;
import java.util.List;

import org.komea.product.database.dto.EventDto;
import org.komea.product.database.dto.SearchEventsDto;

import com.tocea.product.rest.client.api.IEventsAPI;

class EventsAPI extends AbstractRestCientAPI implements IEventsAPI
{
    
    @Override
    public void pushEvent(final EventDto _event) throws ConnectException {
    
        // TODO Auto-generated pushEvent
        
    }
    
    @Override
    public List<EventDto> findEvents(final SearchEventsDto _searchEvent) throws ConnectException {
    
        // TODO Auto-generated findEvents
        return null;
    }
    
    @Override
    public List<EventDto> getEvents(final String _severityMin, final int _number) throws ConnectException {
    
        // TODO Auto-generated getEvents
        return null;
    }
    //
}
