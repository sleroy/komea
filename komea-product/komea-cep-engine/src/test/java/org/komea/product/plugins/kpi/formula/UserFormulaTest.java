/**
 * 
 */

package org.komea.product.plugins.kpi.formula;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.product.cep.formula.EventCountFormula;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Person;
import org.komea.product.service.dto.EntityKey;

import com.google.common.collect.Lists;

/**
 * @author sleroy
 */
public class UserFormulaTest {

	public static Event fakeEvent() {

		final Event event = new Event();
		final Person person = new Person();
		person.setId(1);
		event.setPerson(person);
		return event;
	}

	/**
	 * Test method for
	 * {@link org.komea.product.plugins.kpi.formula.UserFormula#compute(org.komea.eventory.api.engine.ICEPStatement, java.util.Map)}
	 * .
	 */
	@Test
	public final void testCompute() throws Exception {

		final UserFormula userFormula = new UserFormula(new EventCountFormula());
		final ICEPStatement<IEvent> cepStatement = mock(ICEPStatement.class);
		final Event event = fakeEvent();
		when(cepStatement.getAggregateView()).thenReturn(Lists.<IEvent> newArrayList(event));

		final KpiResult compute = userFormula.compute(cepStatement);
		assertEquals(1, compute.size());
		assertNotNull(compute.getValue(EntityKey.of(EntityType.PERSON, 1)));

	}

}
