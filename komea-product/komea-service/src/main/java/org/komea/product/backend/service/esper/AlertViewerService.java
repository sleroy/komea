
package org.komea.product.backend.service.esper;


import java.util.Date;
import java.util.List;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;
import com.google.common.collect.Lists;

@Service
public class AlertViewerService implements IEventViewerService {
    
    @Autowired
    private IEsperEngine esperService;
    
    public AlertViewerService() {
    
        super();
    }
    
    @Override
    public List<IEvent> findEvents(final SearchEventDto _searchEvent) {
    
        // TODO STUB
        
        final List<IEvent> events = Lists.newArrayList();
        final IEvent event = new Event();
        event.setDate(new Date());
        
        final EventType eventType = new EventType();
        eventType.setCategory("large category");
        eventType.setDescription("a large event");
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setEventKey("dtc");
        eventType.setId(1);
        eventType.setIdProvider(1);
        eventType.setName("dtc");
        eventType.setSeverity(Severity.MINOR);
        event.setEventType(eventType);
        
        event.setMessage("a massage");
        
        final Provider provider = new Provider();
        provider.setIcon("/incon.png");
        provider.setId(1);
        provider.setProviderType(ProviderType.JENKINS);
        event.setProvider(provider);
        
        events.add(event);
        return events;
    }
    
    public IEsperEngine getEsperService() {
    
        return esperService;
    }
    
    @Override
    public List<IEvent> getEvents(final String _severityMin, final int _number) {
    
        // TODO STUB
        
        final List<IEvent> events = Lists.newArrayList();
        final IEvent event = new Event();
        event.setDate(new Date());
        
        final EventType eventType = new EventType();
        eventType.setCategory("large category");
        eventType.setDescription("a large event");
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setEventKey("dtc");
        eventType.setId(1);
        eventType.setIdProvider(1);
        eventType.setName("dtc");
        eventType.setSeverity(Severity.MINOR);
        eventType.setEnabled(true);
        event.setEventType(eventType);
        
        event.setMessage("a massage");
        
        final Provider provider = new Provider();
        provider.setIcon("/incon.png");
        provider.setId(1);
        provider.setProviderType(ProviderType.JENKINS);
        provider.setName("jenkins provider");
        provider.setUrl("http://provider/jenkins");
        event.setProvider(provider);
        
        events.add(event);
        return events;
    }
    
    @Override
    public List<IEvent> getInstantView(final String _EplStatement) {
    
        final EPStatement statementOrFail = esperService.getStatementOrFail(_EplStatement);
        final SafeIterator<EventBean> safeIterator = null;
        return EPStatementResult.build(statementOrFail).listUnderlyingObjects();
    }
    
    public void setEsperService(final IEsperEngine _esperService) {
    
        esperService = _esperService;
    }
    
}
