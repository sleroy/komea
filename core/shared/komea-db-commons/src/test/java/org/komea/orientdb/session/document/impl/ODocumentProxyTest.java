package org.komea.orientdb.session.document.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.komea.orientdb.session.document.impl.ODocumentProxy;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ODocumentProxyTest {

	public static class PojoInject {
		private String	fieldA;
		private Integer	fieldB;

		public String getFieldA() {
			return this.fieldA;
		}

		public Integer getFieldB() {
			return this.fieldB;
		}

		public void setFieldA(final String _fieldA) {
			this.fieldA = _fieldA;
		}

		public void setFieldB(final Integer _fieldB) {
			this.fieldB = _fieldB;
		}
	}

	private static final String	 FIELD_B	     = "fieldB";

	private static final String	 FIELD_A	     = "fieldA";
	private static final Integer	CONSTANT_INT	= 10;

	private static final String	 CONSTANT_STRING	= "valueA";

	@Test
	public void testContainsField() throws Exception {
		final ODocument odoc = mock(ODocument.class);
		final ODocumentProxy oDocumentProxy = new ODocumentProxy(odoc);
		final String fieldName = "champ";
		oDocumentProxy.containsField(fieldName);
		verify(odoc, times(1)).containsField(fieldName);
	}

	/**
	 * Test prettyprint of a odocument
	 *
	 * @throws Exception
	 */
	@Test
	public void testDump() throws Exception {
		final ODocument odoc = mock(ODocument.class);
		final ODocumentProxy oDocumentProxy = new ODocumentProxy(odoc);
		when(odoc.fieldNames()).thenReturn(new String[] { FIELD_A, FIELD_B });
		when(odoc.field(FIELD_A)).thenReturn(CONSTANT_STRING);
		when(odoc.field(FIELD_B)).thenReturn(CONSTANT_INT);

		oDocumentProxy.dump();
	}

	@Test
	public void testFieldString() throws Exception {
		final ODocument odoc = mock(ODocument.class);
		final ODocumentProxy oDocumentProxy = new ODocumentProxy(odoc);
		final String fieldName = "champ";
		oDocumentProxy.field(fieldName);
		verify(odoc, times(1)).field(fieldName);
	}

	@Test
	public void testFieldStringSerializable() throws Exception {
		final ODocument odoc = mock(ODocument.class);
		final ODocumentProxy oDocumentProxy = new ODocumentProxy(odoc);
		final String serializableValue = "serializableValue";
		final String fieldName = "champ";
		oDocumentProxy.field(fieldName, serializableValue);
		verify(odoc, times(1)).field(fieldName, serializableValue);
	}

	@Test()
	public void testFieldStringSerializableException() throws Exception {
		final ODocument odoc = mock(ODocument.class);

		final ODocumentProxy oDocumentProxy = new ODocumentProxy(odoc);
		final String serializableValue = "serializableValue";
		final String fieldName = "champ";
		when(odoc.field(fieldName, serializableValue)).thenThrow(IllegalArgumentException.class);
		oDocumentProxy.field(fieldName, serializableValue);
		verify(odoc, times(1)).field(fieldName, serializableValue);
	}

	@Test
	public void testSave() throws Exception {
		final ODocument odoc = mock(ODocument.class);
		final ODocumentProxy oDocumentProxy = new ODocumentProxy(odoc);
		oDocumentProxy.save();
		verify(odoc, times(1)).save();
	}

	/**
	 * Test conversion of a ODocument into a pojo.
	 *
	 * @throws Exception
	 */
	@Test
	public void testToPojo() throws Exception {
		final ODocument odoc = mock(ODocument.class);
		final ODocumentProxy oDocumentProxy = new ODocumentProxy(odoc);
		when(odoc.fieldNames()).thenReturn(new String[] { FIELD_A, FIELD_B });
		when(odoc.field(FIELD_A)).thenReturn(CONSTANT_STRING);
		when(odoc.field(FIELD_B)).thenReturn(CONSTANT_INT);

		final PojoInject pojo = new PojoInject();
		oDocumentProxy.toPojo(pojo);
		assertEquals(CONSTANT_STRING, pojo.getFieldA());
		assertEquals(CONSTANT_INT, pojo.getFieldB());

		final PojoInject pojo2 = oDocumentProxy.toPojo(PojoInject.class);
		assertEquals(CONSTANT_STRING, pojo2.getFieldA());
		assertEquals(CONSTANT_INT, pojo2.getFieldB());

	}

	@Test
	public void testToString() throws Exception {
		final ODocument odoc = mock(ODocument.class);
		final ODocumentProxy oDocumentProxy = new ODocumentProxy(odoc);
		assertNotNull(oDocumentProxy.toString());
		assertFalse(oDocumentProxy.toString().isEmpty());
	}

}
