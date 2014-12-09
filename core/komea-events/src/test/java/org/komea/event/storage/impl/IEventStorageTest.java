package org.komea.event.storage.impl;

import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.event.model.IBasicEventInformations;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.impl.EventStorage;
import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.document.IODocumentToolbox;
import org.mockito.Mockito;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;

public class IEventStorageTest {

	private static final String	FIELD_EXTRA	      = "extra";

	private static final String	FIELD_EXTRA_VALUE	= "field1";

	private static final String	BUGZILLA	      = "bugzilla";

	private static final String	NEW_BUG	          = "new_bug";

	private IODocumentToolbox	documentToolBox	  = null;

	private IEventStorage	    eventStorage;

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
		this.eventStorage.close();
	}

	@Before
	public void setup() {
		this.documentToolBox = Mockito.mock(IODocumentToolbox.class);
		this.eventStorage = new EventStorage(mock(IOrientSessionFactory.class), this.documentToolBox);
	}

	/**
	 * Test the storage of event.
	 */
	@Test
	public void storeComplexEvent() {

		// WHEN
		final IODocument odocument = Mockito.mock(IODocument.class);
		Mockito.when(this.documentToolBox.newDocument(NEW_BUG)).thenReturn(odocument);

		final Date newDate = new Date();
		final ComplexEvent complexEvent = new ComplexEvent(BUGZILLA, NEW_BUG, newDate);
		complexEvent.addField(FIELD_EXTRA, FIELD_EXTRA_VALUE);
		this.configureMock(odocument, newDate);

		// WHEN
		this.eventStorage.storeComplexEvent(complexEvent);

		// THEN
		Mockito.verify(this.documentToolBox, Mockito.times(1)).newDocument(NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_PROVIDER, BUGZILLA);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_DATE, newDate);
		Mockito.verify(odocument, Mockito.times(1)).field(FIELD_EXTRA, FIELD_EXTRA_VALUE);
		Mockito.verify(odocument, Mockito.times(1)).save();
	}

	/**
	 * Test the storage of event.
	 */
	@Test
	public void storeEvent() {

		// WHEN
		final IODocument odocument = Mockito.mock(IODocument.class);
		Mockito.when(this.documentToolBox.newDocument(NEW_BUG)).thenReturn(odocument);
		final Date newDate = new Date();
		final BasicEvent be1 = new BasicEvent(BUGZILLA, NEW_BUG, newDate);
		this.configureMock(odocument, newDate);

		this.eventStorage.storeBasicEvent(be1);
		// THEN
		Mockito.verify(this.documentToolBox, Mockito.times(1)).newDocument(NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_PROVIDER, BUGZILLA);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_DATE, newDate);
		Mockito.verify(odocument, Mockito.times(1)).save();
	}

	/**
	 * Test the storage of a flat event.
	 */
	@Test
	public void storeFlatEvent() {

		// WHEN
		final IODocument odocument = Mockito.mock(IODocument.class);
		Mockito.when(this.documentToolBox.newDocument(NEW_BUG)).thenReturn(odocument);
		final Date newDate = new Date();
		final FlatEvent flatEvent = new FlatEvent();

		flatEvent.addField(IBasicEventInformations.FIELD_DATE, newDate);
		flatEvent.addField(IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		flatEvent.addField(IBasicEventInformations.FIELD_PROVIDER, BUGZILLA);
		// ODOCUMENT Mocking
		this.configureMock(odocument, newDate);
		this.eventStorage.storeFlatEvent(flatEvent);
		// THEN
		Mockito.verify(this.documentToolBox, Mockito.times(1)).newDocument(NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_PROVIDER, BUGZILLA);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		Mockito.verify(odocument, Mockito.times(1)).field(IBasicEventInformations.FIELD_DATE, newDate);
		Mockito.verify(odocument, Mockito.times(1)).save();
	}

	private void configureMock(final IODocument odocument, final Date newDate) {
		Mockito.when(odocument.containsField(IBasicEventInformations.FIELD_DATE)).thenReturn(true);
		Mockito.when(odocument.containsField(IBasicEventInformations.FIELD_EVENT_TYPE)).thenReturn(true);
		Mockito.when(odocument.containsField(IBasicEventInformations.FIELD_PROVIDER)).thenReturn(true);
		Mockito.when(odocument.field(IBasicEventInformations.FIELD_DATE)).thenReturn(newDate);
		Mockito.when(odocument.field(IBasicEventInformations.FIELD_EVENT_TYPE)).thenReturn(NEW_BUG);
		Mockito.when(odocument.field(IBasicEventInformations.FIELD_PROVIDER)).thenReturn(BUGZILLA);
	}
}
