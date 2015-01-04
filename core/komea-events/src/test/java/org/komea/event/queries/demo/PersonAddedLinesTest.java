/**
 *
 */
package org.komea.event.queries.demo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.komea.event.generator.IEventDefinition;
import org.komea.event.generator.KpiRange;
import org.komea.event.generator.impl.EventGenerator;
import org.komea.event.model.IBasicEventInformations;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.impl.EventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 */
@RunWith(Parameterized.class)
public class PersonAddedLinesTest {

	@Parameters(name = "events={0},threads={1},fetch_all={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
		                                     {
		                                    	 new Interval(new DateTime().minusYears(1),
		                                    			 new DateTime()),
		                                    			 KpiRange.DAY, 10, 10
		                                     }
		});
	}

	@Parameter(value = 1)
	public KpiRange	            range;
	@Parameter(value = 0)
	public Interval	            intervalDate;
	@Parameter(value = 2)
	public int	                numberDevelopers;
	@Parameter(value = 3)
	public int	                commitPerDay;
	public EventStorage	        eventStorage;
	public EventGenerator	    eventGenerator;
	public int	                generatedEvents;

	private static final Logger	LOGGER	= LoggerFactory
			.getLogger(PersonAddedLinesTest.class);

	@After
	public void after() throws IOException {
		eventStorage.close();
	}

	@Before
	public void before() throws Exception {
		eventStorage = new EventStorageFactory().build(Impl.H2_MEM);

		eventGenerator = new EventGenerator(eventStorage,
				new IEventDefinition() {

			/**
			 *
			 */
			private final Random	RANDOM	= new Random();

			@Override
			public void decorateEvent(final DateTime _date,
					final int _nthEventOfDay, final FlatEvent _flatEvent) {
				_flatEvent.put(
						IBasicEventInformations.FIELD_EVENT_TYPE,
						"new_commit");
				_flatEvent.put(IBasicEventInformations.FIELD_PROVIDER,
						"perforce");
				_flatEvent.put("project", "KOMEA");
				_flatEvent.put("numberOfChangedLines", 100);
				_flatEvent.put("numberOfDeletedLines", 200);
				_flatEvent.put("numberOfModifiedLines", 100);
				_flatEvent.put("numberOfAddedLines", 100);
				_flatEvent.put("commitMessage", "New commit happened");
				_flatEvent.put("id", RANDOM.nextLong());
				_flatEvent.put("author",
						"dev" + _nthEventOfDay
						% numberDevelopers);

			}
		});
		generatedEvents = eventGenerator.generate(commitPerDay
				* numberDevelopers, range, intervalDate);

		LOGGER.info("Has generated {} events", generatedEvents);
	}

	@Test
	public void test() {

	}
}
