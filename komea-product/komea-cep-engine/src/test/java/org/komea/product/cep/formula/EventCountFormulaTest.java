
package org.komea.product.cep.formula;



import org.junit.Test;
import org.komea.product.cep.api.formula.IEventTable;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.kpi.tuplecreator.UserTupleCreator;
import org.komea.product.service.dto.EntityKey;

import static org.junit.Assert.assertEquals;



public class EventCountFormulaTest
{
    
    
    @Test
    public void testProcessMapWithoutParameters() throws Exception {
    
    
        final IEventTable eventTable = new EventTable<EntityKey, IEvent>(new UserTupleCreator());
        final Event event = buildFakeEvent();
        
        eventTable.groupEvent(event);
        assertEquals(1, eventTable.getMap().size());
        assertEquals(event, eventTable.get(EntityKey.of(EntityType.PERSON, 1)).getFirstEvent());
        
    }
    
    
    private Event buildFakeEvent() {
    
    
        final Event event = new Event();
        final Person person = new Person();
        person.setId(1);
        event.setPerson(person);
        return event;
    }
}
