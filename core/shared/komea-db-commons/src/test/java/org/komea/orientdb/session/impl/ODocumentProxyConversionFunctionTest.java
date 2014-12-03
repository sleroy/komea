package org.komea.orientdb.session.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.komea.orientdb.session.document.IODocument;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ODocumentProxyConversionFunctionTest {

	@Test()
	public final void testApply() throws Exception {
		final ODocument oDocument = mock(ODocument.class);
		final IODocument ioDocument = new ODocumentProxyConversionFunction().apply(oDocument);
		assertNotNull("Document should be converted", ioDocument);
		assertEquals("Raw node should be the same", ioDocument.getOrientDBDocument(), oDocument);
		// Check wrapping
		ioDocument.containsField("gni");
		verify(oDocument, times(1)).containsField("gni");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testApplyAgainstNull() throws Exception {
		new ODocumentProxyConversionFunction().apply(null);
	}

}
