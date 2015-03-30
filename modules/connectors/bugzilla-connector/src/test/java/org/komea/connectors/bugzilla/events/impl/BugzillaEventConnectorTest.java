package org.komea.connectors.bugzilla.events.impl;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;

import org.joda.time.DateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.connectors.bugzilla.BugzillaEventConnector;
import org.komea.connectors.bugzilla.proxy.IBugzillaAPI;
import org.komea.connectors.bugzilla.proxy.impl.BugzillaServerConfiguration;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventStorage;

import static org.mockito.Matchers.any;

import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BugzillaEventConnectorTest {

    private final BugzillaServerConfiguration configuration = new BugzillaServerConfiguration();

    @Mock
    private IEventStorage eventStorage;

    @Mock
    private IBugzillaAPI bugzillaAPI;

    @Test
    public void testIsRecentlyCreated_moreRecentDate() throws Exception {
        final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(this.bugzillaAPI,
                this.eventStorage, this.configuration);
        // NOW TIME
        this.configuration.setSince(new DateTime());
        final Bug bug = mock(Bug.class);
        // MORE RECENT DATE
        when(this.bugzillaAPI.getCreationTime(bug)).thenReturn(new DateTime().plusYears(1));
        assertTrue(bugzillaDataConnector.isRecentlyCreated(bug, this.bugzillaAPI));

    }

    @Test
    public void testIsRecentlyCreated_noDate() throws Exception {
        final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(this.bugzillaAPI,
                this.eventStorage, this.configuration);
        // NO DATE
        this.configuration.setSince(null);
        final Bug bug = mock(Bug.class);
        when(this.bugzillaAPI.getCreationTime(bug)).thenReturn(new DateTime().minusYears(1));
        assertTrue(bugzillaDataConnector.isRecentlyCreated(bug, this.bugzillaAPI));

    }

    @Test
    public void testIsRecentlyCreated_olderDate() throws Exception {
        final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(this.bugzillaAPI,
                this.eventStorage, this.configuration);
        // NOW TIME
        this.configuration.setSince(new DateTime());
        final Bug bug = mock(Bug.class);
        // OLDER DATE
        when(this.bugzillaAPI.getCreationTime(bug)).thenReturn(new DateTime().minusYears(1));
        assertFalse(bugzillaDataConnector.isRecentlyCreated(bug, this.bugzillaAPI));

    }

    @Test
    public void testIsRecentlyUpdated_moreRecentDate() throws Exception {
        final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(this.bugzillaAPI,
                this.eventStorage, this.configuration);
        // NOW TIME
        this.configuration.setSince(new DateTime());
        final Bug bug = mock(Bug.class);
        // MORE RECENT DATE
        when(this.bugzillaAPI.getUpdatedTime(bug)).thenReturn(new DateTime().plusYears(1));
        assertTrue(bugzillaDataConnector.isRecentlyUpdated(bug, this.bugzillaAPI));

    }

    @Test
    public void testIsRecentlyUpdated_noDate() throws Exception {
        final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(this.bugzillaAPI,
                this.eventStorage, this.configuration);
        // NO DATE
        this.configuration.setSince(null);
        final Bug bug = mock(Bug.class);
        when(this.bugzillaAPI.getUpdatedTime(bug)).thenReturn(new DateTime().minusYears(1));
        assertTrue(bugzillaDataConnector.isRecentlyUpdated(bug, this.bugzillaAPI));

    }

    @Test
    public void testIsRecentlyUpdated_olderDate() throws Exception {
        final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(this.bugzillaAPI,
                this.eventStorage, this.configuration);
        // NOW TIME
        this.configuration.setSince(new DateTime());
        final Bug bug = mock(Bug.class);
        // OLDER DATE
        when(this.bugzillaAPI.getUpdatedTime(bug)).thenReturn(new DateTime().minusYears(1));
        assertFalse(bugzillaDataConnector.isRecentlyUpdated(bug, this.bugzillaAPI));

    }

    @Test
    public void testLaunchWithOneProject() throws Exception {

        final Bug bug = mock(Bug.class);
        final Bug bug2 = mock(Bug.class);
        when(this.bugzillaAPI.obtainProductList()).thenReturn(Lists.newArrayList("DEMO_PROJECT"));
        when(this.bugzillaAPI.getBugList("DEMO_PROJECT")).thenReturn(Lists.newArrayList(bug, bug2));

        final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(this.bugzillaAPI,
                this.eventStorage, this.configuration);
        bugzillaDataConnector.launch();
        verify(this.eventStorage, times(4)).storeEvent(any(KomeaEvent.class));

    }

    @Test
    public void testLaunchWithoutConfiguration() throws Exception {
        final BugzillaEventConnector bugzillaDataConnector = new BugzillaEventConnector(this.bugzillaAPI,
                this.eventStorage, this.configuration);
        bugzillaDataConnector.launch();

    }

}
