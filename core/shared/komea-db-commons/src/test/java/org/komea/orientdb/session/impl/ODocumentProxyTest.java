package org.komea.orientdb.session.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ODocumentProxyTest {

	public static class PojoInject {
		private String fieldA;
		private Integer fieldB;

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

	private static final String FIELD_B = "fieldB";

	private static final String FIELD_A = "fieldA";
	private static final Integer CONSTANT_INT = 10;

	private static final String CONSTANT_STRING = "valueA";

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

	}

}
