package org.komea.event.storage.service;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.event.model.api.IBasicEventInformations;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.api.IEventStorage;
import org.komea.orientdb.session.IDocumentSessionFactory;
import org.komea.orientdb.session.document.IODocument;
import org.mockito.Mockito;

public class IEventStorageSpecificationTest {

	private static final String FIELD_EXTRA = "extra";

	private static final String FIELD_EXTRA_VALUE = "field1";

	private static final String BUGZILLA = "bugzilla";

	private static final String NEW_BUG = "new_bug";

	private IDocumentSessionFactory dbc = null;

	private IEventStorage es;

	/**
	 * After each feature...
	 * <ul>
	 * <li>Clean the connections</li>
	 * </ul>
	 *
	 * @throws Exception
	 */
	@After
	public void cleanup() throws Exception {
		this.es.close();
	}

	@Before
	public void setup() {
		this.dbc = Mockito.mock(IDocumentSessionFactory.class);
		this.es = new EventStorage(this.dbc);
	}

	/**
	 * Test the storage of event.
	 */
	@Test
	public void storeComplexEvent() {

		// WHEN
		final IODocument odocument = Mockito.mock(IODocument.class);
		Mockito.when(this.dbc.newDocument(NEW_BUG)).thenReturn(odocument);
		final Date newDate = new Date();
		final ComplexEvent complexEvent = new ComplexEvent(BUGZILLA, NEW_BUG,
				newDate);
		complexEvent.addField(FIELD_EXTRA, FIELD_EXTRA_VALUE);
		this.es.storeComplexEvent(complexEvent);
		// THEN
		Mockito.verify(this.dbc, Mockito.times(1)).newDocument(NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_PROVIDER, BUGZILLA);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_DATE, newDate);
		Mockito.verify(odocument, Mockito.times(1)).field(FIELD_EXTRA,
				FIELD_EXTRA_VALUE);
	}

	/**
	 * Test the storage of event.
	 */
	@Test
	public void storeEvent() {

		// WHEN
		final IODocument odocument = Mockito.mock(IODocument.class);
		Mockito.when(this.dbc.newDocument(NEW_BUG)).thenReturn(odocument);
		final Date newDate = new Date();
		final BasicEvent be1 = new BasicEvent(BUGZILLA, NEW_BUG, newDate);
		this.es.storeEvent(be1);
		// THEN
		Mockito.verify(this.dbc, Mockito.times(1)).newDocument(NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_PROVIDER, BUGZILLA);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_DATE, newDate);
	}

	/**
	 * Test the storage of a flat event.
	 */
	@Test
	public void storeFlatEvent() {

		// WHEN
		final IODocument odocument = Mockito.mock(IODocument.class);
		Mockito.when(this.dbc.newDocument(NEW_BUG)).thenReturn(odocument);
		final Date newDate = new Date();
		final FlatEvent flatEvent = new FlatEvent();

		flatEvent.addField(IBasicEventInformations.FIELD_DATE, newDate);
		flatEvent.addField(IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		flatEvent.addField(IBasicEventInformations.FIELD_PROVIDER, BUGZILLA);

		this.es.storeFlatEvent(flatEvent);
		// THEN
		Mockito.verify(this.dbc, Mockito.times(1)).newDocument(NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_PROVIDER, BUGZILLA);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(
				IBasicEventInformations.FIELD_DATE, newDate);
	}
}
