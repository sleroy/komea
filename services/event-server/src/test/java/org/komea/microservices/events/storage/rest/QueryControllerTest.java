/**
 *
 */
package org.komea.microservices.events.storage.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.impl.EmptyResultIterator;
import org.komea.microservices.events.query.rest.QueryController;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class QueryControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private final QueryController controller = new QueryController();


	@Mock
	private IEventStorage eventStorage;

	@Mock
	private IMessageSender messageSender;


	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
	}





	@Test
	public void testCountAllEvents() throws Exception {
		this.getQueryController().countAllEvents();
		Mockito.verify(this.eventStorage, Mockito.times(1)).countAllEvents();
	}

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.QueryController#countEvent(java.lang.String)}
	 * .
	 */
	@Test
	public void testCountEvent() throws Exception {
		final String eventType = "e1";
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/query/{eventType}/count", eventType); //$NON-NLS-1$
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk());

		Mockito.when(this.eventStorage.existStorage(eventType))
		.thenReturn(true);
		Mockito.when(this.eventStorage.getEventDB(eventType))
		.thenReturn(mock(IEventDB.class));

		this.mockMvc.perform(requestBuilder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("0"));
	}



	@Test
	public void testExecuteQuery() throws Exception {
		final EventsQuery query = new EventsQuery();
		this.getQueryController().executeQuery(query);
		Mockito.verify(this.eventStorage, Mockito.times(1)).executeQuery(query);
	}

	@Test
	public void testGetAllEventsOnPeriod() throws Exception {
		final int limit = 1;
		final DateInterval interval = new DateInterval();
		this.getQueryController().getAllEventsOnPeriod(limit, interval);
		Mockito.verify(this.eventStorage, Mockito.times(1)).getAllEventsOnPeriod(interval, limit);
	}

	@Test
	public void testGetEventsByFilter() throws Exception {
		final EventsFilter filter = new EventsFilter();
		this.getQueryController().getEventsByFilter(filter);
		Mockito.verify(this.eventStorage, Mockito.times(1)).getEventsByFilter(filter);
	}

	@Test
	public void testGetEventsOfType() throws Exception {
		final String eventType = "builds";
		this.getQueryController().getEventsOfType(eventType);
		Mockito.verify(this.eventStorage, Mockito.times(1)).loadEventsOfType(eventType);
	}

	@Test
	public void testGetEventTypeOnPeriod() throws Exception {
		final String eventType = "builds";
		final int limit = 1;
		final DateInterval interval = new DateInterval();
		this.getQueryController().getEventsOfTypeOnPeriod(eventType, limit, interval);
		Mockito.verify(this.eventStorage, Mockito.times(1)).loadEventsOfTypeOnPeriod(eventType, interval, limit);
	}

	@Test
	public void testGetEventTypes() throws Exception {
		this.getQueryController().getEventTypes();
		Mockito.verify(this.eventStorage, Mockito.times(1)).getEventTypes();
	}

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.QueryController#getLastEvent(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetLastEvent() throws Exception {
		final String eventType = "e1";
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/query/{eventType}/last", eventType); //$NON-NLS-1$
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk());

		when(this.eventStorage.existStorage(eventType)).thenReturn(true);
		final IEventDB eventDB = mock(IEventDB.class);
		when(this.eventStorage.getEventDB(eventType)).thenReturn(eventDB);
		when(eventDB.getLastEvent()).thenReturn(new DateTime());

		this.mockMvc.perform(requestBuilder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

	}

	private QueryController getQueryController() {

		Mockito.when(this.eventStorage.loadEventsOfType(Matchers.anyString())).thenReturn(new EmptyResultIterator());
		Mockito.when(this.eventStorage.loadEventsOfTypeOnPeriod(Matchers.anyString(),
		                                                        Matchers.any(DateInterval.class), Matchers.anyInt())).thenReturn(new EmptyResultIterator());
		return this.controller;
	}

}
