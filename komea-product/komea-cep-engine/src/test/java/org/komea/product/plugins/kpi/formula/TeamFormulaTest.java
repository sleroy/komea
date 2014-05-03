/**
 * 
 */

package org.komea.product.plugins.kpi.formula;



import java.util.Collections;

import org.junit.Test;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.product.cep.formula.EventCountFormula;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.service.dto.EntityKey;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class TeamFormulaTest
{
    
    
    public static Event fakeEvent() {
    
    
        final Event event = new Event();
        final PersonGroup personGroup = new PersonGroup();
        personGroup.setId(1);
        personGroup.setType(PersonGroupType.TEAM);
        event.setPersonGroup(personGroup);
        return event;
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.kpi.formula.TeamFormula#compute(org.komea.eventory.api.engine.ICEPStatement, java.util.Map)}.
     */
    @Test
    public final void testCompute() throws Exception {
    
    
        final TeamFormula departmentFormula = new TeamFormula(new EventCountFormula());
        final ICEPStatement<IEvent> cepStatement = mock(ICEPStatement.class);
        final Event event = fakeEvent();
        when(cepStatement.getAggregateView()).thenReturn(Lists.<IEvent> newArrayList(event));
        
        final KpiResult compute =
                departmentFormula.compute(cepStatement, Collections.<String, Object> emptyMap());
        assertEquals(1, compute.size());
        assertNotNull(compute.getValue(EntityKey.of(EntityType.TEAM, 1)));
        
    }
    
}
