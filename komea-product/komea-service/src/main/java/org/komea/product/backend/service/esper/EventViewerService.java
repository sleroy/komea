package org.komea.product.backend.service.esper;

import com.espertech.esper.client.EPStatement;
import java.util.List;
import javax.annotation.PostConstruct;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.database.alert.IEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventViewerService implements IEventViewerService {

    private static final String DAY_EVENTS = "DAY_EVENTS";
    private static final String HOUR_EVENTS = "HOUR_EVENTS";
    private static final String LAST100_EVENTS = "LAST100_EVENTS";
    @Autowired
    private IEsperEngine esperService;

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

    @Override
    public List<IEvent> getLastEvents() {

        return getInstantView(LAST100_EVENTS);
    }

    @PostConstruct
    public void initialize() {

        esperService.createEPL(new QueryDefinition(
                "SELECT * FROM  Event.win:time(1 day) ORDER BY date DESC", DAY_EVENTS));
        esperService.createEPL(new QueryDefinition(
                "SELECT * FROM  Event.win:time(1 hour) ORDER BY date DESC", HOUR_EVENTS));
        esperService.createEPL(new QueryDefinition(
                "SELECT * FROM  Event.win:time(1 hour) ORDER BY date DESC LIMIT 100",
                LAST100_EVENTS));
    }

    public void setEsperService(final IEsperEngine _esperService) {

        esperService = _esperService;
    }

}
