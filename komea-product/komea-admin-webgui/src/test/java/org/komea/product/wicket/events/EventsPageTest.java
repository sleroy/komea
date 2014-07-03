/**
 * 
 */

package org.komea.product.wicket.events;



import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class EventsPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        final IEventViewerService mock = Mockito.mock(IEventViewerService.class);
        final List<IEvent> values = new ArrayList<IEvent>();
        final EventType eventType = new EventType();
        eventType.setSeverity(Severity.INFO);
        final Event event = new Event();
        event.setDate(new Date());
        event.setEventType(eventType);
        event.setMessage("Message");
        event.setPerson(new Person());
        event.setPersonGroup(new PersonGroup());
        event.setProject(new Project());
        event.setProvider(new Provider());
        event.setUrl("http://");
        event.setValue(12.0d);
        values.add(event);
        when(mock.getGlobalActivity()).thenReturn(values);
        wicketRule.getApplicationContextMock().putBean(mock);
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.events.EventsPage#EventsPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test 
    public final void testEventsPage() throws Exception {
    
    
        wicketRule.testStart(EventsPage.class);
    }
    
}
