
package org.komea.product.backend.service.esper;



import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.IEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espertech.esper.client.EPStatement;



@Service
public class EventViewerService implements IEventViewerService
{
    
    
    private static final String DAY_EVENTS  = "DAY_EVENTS";
    private static final String HOUR_EVENTS = "HOUR_EVENTS";
    @Autowired
    private IEsperEngine        esperService;
    
    
    
    public EventViewerService() {
    
    
        super();
    }
    
    
    @Override
    public List<IEvent> getDayEvents() {
    
    
        return getInstantView(DAY_EVENTS);
    }
    
    
    public IEsperEngine getEsperService() {
    
    
        return esperService;
    }
    
    
    @Override
    public List<IEvent> getHourEvents() {
    
    
        return getInstantView(HOUR_EVENTS);
    }
    
    
    @Override
    public List<IEvent> getInstantView(final String _EplStatement) {
    
    
        final EPStatement requiredEplStatement = esperService.getStatementOrFail(_EplStatement);
        return EPStatementResult.build(requiredEplStatement).listUnderlyingObjects();
    }
    
    
    @PostConstruct
    public void initialize() {
    
    
        esperService.createEPL(new QueryDefinition("SELECT * FROM  Event.win:time(1 day)",
                DAY_EVENTS));
        esperService.createEPL(new QueryDefinition("SELECT * FROM  Event.win:time(1 hour)",
                HOUR_EVENTS));
    }
    
    
    public void setEsperService(final IEsperEngine _esperService) {
    
    
        esperService = _esperService;
    }
    
}
