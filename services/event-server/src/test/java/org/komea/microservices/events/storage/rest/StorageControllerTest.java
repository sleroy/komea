/**
 *
 */
package org.komea.microservices.events.storage.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class StorageControllerTest {

	private MockMvc					mockMvc;

	@InjectMocks
	private final StorageController	controller	= new StorageController();

	@Mock
	private IEventStorage			eventStorage;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
	}

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.StorageController#clearEvent(java.lang.String)}
	 * .
	 */
	@Test
	public void testClearEvent() throws Exception {

		final String eventType = "e1";
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(	"/storage/{eventType}/clear", eventType); //$NON-NLS-1$
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk());

		Mockito.when(this.eventStorage.existStorage(eventType))
		.thenReturn(true);

		this.mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.StorageController#countEvent(java.lang.String)}
	 * .
	 */
	@Test
	public void testCountEvent() throws Exception {
		final String eventType = "e1";
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/storage/{eventType}/count", eventType); //$NON-NLS-1$
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

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.StorageController#declareEvent(java.lang.String)}
	 * .
	 */
	@Test
	public void testDeclareEvent() throws Exception {
		final String eventType = "e1";
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/storage/{eventType}/declare", eventType); //$NON-NLS-1$

		this.mockMvc.perform(requestBuilder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());
		verify(this.eventStorage, times(1)).declareEventType(eventType);
	}

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.StorageController#getLastEvent(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetLastEvent() throws Exception {
		final String eventType = "e1";
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/storage/{eventType}/last", eventType); //$NON-NLS-1$
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk());

		when(this.eventStorage.existStorage(eventType)).thenReturn(true);
		final IEventDB eventDB = mock(IEventDB.class);
		when(this.eventStorage.getEventDB(eventType)).thenReturn(eventDB);
		when(eventDB.getLastEvent()).thenReturn(new DateTime());

		this.mockMvc.perform(requestBuilder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());

	}

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.StorageController#pushGetEvent(java.lang.String, java.lang.String, java.lang.Double)}
	 * .
	 */
	@Test
	public void testPushGetEvent() throws Exception {
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/storage/push/redmine/new_bug/0"); //$NON-NLS-1$
		this.mockMvc.perform(requestBuilder)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());
	}

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.StorageController#pushGetEvent(java.lang.String, java.lang.String, java.lang.Double)}
	 * .
	 */
	@Test
	public void testPushGetRawEvent() throws Exception {
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/storage/push/post").flashAttr(	"gni", "salut").flashAttr(	"date", new DateTime()).flashAttr(	"provider", "redmine"); //$NON-NLS-1$
		this.mockMvc.perform(requestBuilder)
					.andDo(MockMvcResultHandlers.print())
					.andExpect(status().isOk());
	}

	/**
	 * Test method for
	 * {@link org.komea.microservices.events.storage.rest.StorageController#pushPostEvent(org.komea.event.model.impl.KomeaEvent)}
	 * .
	 */
	@Test
	public void testPushPostEvent() throws Exception {
		final String eventContent = new ObjectMapper().writeValueAsString(new KomeaEvent());
		System.out.println(eventContent);
		final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/storage/push").content(eventContent).contentType(MediaType.APPLICATION_JSON); //$NON-NLS-1$
		this.mockMvc.perform(requestBuilder)
					.andDo(MockMvcResultHandlers.print())
					.andExpect(status().isOk());

	}
}
