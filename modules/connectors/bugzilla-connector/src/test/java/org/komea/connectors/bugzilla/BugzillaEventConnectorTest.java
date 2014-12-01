package org.komea.connectors.bugzilla;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.model.api.IComplexEvent;
import org.komea.event.storage.api.IEventStorage;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;

@RunWith(MockitoJUnitRunner.class)
public class BugzillaEventConnectorTest {
	private final BugzillaServerConfiguration configuration = new BugzillaServerConfiguration();

	@Mock
	private IEventStorage eventStorage;

	@Mock
	private IBugzillaAPI bugzillaAPI;

	@Test
	public void testIsRecentlyCreated_moreRecentDate() throws Exception {
		final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(
				this.bugzillaAPI, this.eventStorage, this.configuration);
		// NOW TIME
		this.configuration.setSince(new DateTime());
		final Bug bug = mock(Bug.class);
		// MORE RECENT DATE
		when(this.bugzillaAPI.getCreationTime(bug)).thenReturn(
				new DateTime().plusYears(1));
		assertTrue(bugzillaDataConnector.isRecentlyCreated(bug,
				this.bugzillaAPI));

	}

	@Test
	public void testIsRecentlyCreated_noDate() throws Exception {
		final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(
				this.bugzillaAPI, this.eventStorage, this.configuration);
		// NO DATE
		this.configuration.setSince(null);
		final Bug bug = mock(Bug.class);
		when(this.bugzillaAPI.getCreationTime(bug)).thenReturn(
				new DateTime().minusYears(1));
		assertTrue(bugzillaDataConnector.isRecentlyCreated(bug,
				this.bugzillaAPI));

	}

	@Test
	public void testIsRecentlyCreated_olderDate() throws Exception {
		final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(
				this.bugzillaAPI, this.eventStorage, this.configuration);
		// NOW TIME
		this.configuration.setSince(new DateTime());
		final Bug bug = mock(Bug.class);
		// OLDER DATE
		when(this.bugzillaAPI.getCreationTime(bug)).thenReturn(
				new DateTime().minusYears(1));
		assertFalse(bugzillaDataConnector.isRecentlyCreated(bug,
				this.bugzillaAPI));

	}

	@Test
	public void testIsRecentlyUpdated_moreRecentDate() throws Exception {
		final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(
				this.bugzillaAPI, this.eventStorage, this.configuration);
		// NOW TIME
		this.configuration.setSince(new DateTime());
		final Bug bug = mock(Bug.class);
		// MORE RECENT DATE
		when(this.bugzillaAPI.getUpdatedTime(bug)).thenReturn(
				new DateTime().plusYears(1));
		assertTrue(bugzillaDataConnector.isRecentlyUpdated(bug,
				this.bugzillaAPI));

	}

	@Test
	public void testIsRecentlyUpdated_noDate() throws Exception {
		final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(
				this.bugzillaAPI, this.eventStorage, this.configuration);
		// NO DATE
		this.configuration.setSince(null);
		final Bug bug = mock(Bug.class);
		when(this.bugzillaAPI.getUpdatedTime(bug)).thenReturn(
				new DateTime().minusYears(1));
		assertTrue(bugzillaDataConnector.isRecentlyUpdated(bug,
				this.bugzillaAPI));

	}

	@Test
	public void testIsRecentlyUpdated_olderDate() throws Exception {
		final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(
				this.bugzillaAPI, this.eventStorage, this.configuration);
		// NOW TIME
		this.configuration.setSince(new DateTime());
		final Bug bug = mock(Bug.class);
		// OLDER DATE
		when(this.bugzillaAPI.getUpdatedTime(bug)).thenReturn(
				new DateTime().minusYears(1));
		assertFalse(bugzillaDataConnector.isRecentlyUpdated(bug,
				this.bugzillaAPI));

	}

	@Test
	public void testLaunchWithOneProject() throws Exception {

		final Bug bug = mock(Bug.class);
		final Bug bug2 = mock(Bug.class);
		when(this.bugzillaAPI.obtainProductList()).thenReturn(
				Lists.newArrayList("DEMO_PROJECT"));
		when(this.bugzillaAPI.getBugList("DEMO_PROJECT")).thenReturn(
				Lists.newArrayList(bug, bug2));

		final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(
				this.bugzillaAPI, this.eventStorage, this.configuration);
		bugzillaDataConnector.launch();
		verify(this.eventStorage, times(4)).storeComplexEvent(
				any(IComplexEvent.class));

	}

	@Test
	public void testLaunchWithoutConfiguration() throws Exception {
		final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(
				this.bugzillaAPI, this.eventStorage, this.configuration);
		bugzillaDataConnector.launch();

	}

}
