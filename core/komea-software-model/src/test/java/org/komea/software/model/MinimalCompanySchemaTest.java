package org.komea.software.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.komea.software.model.impl.MinimalCompanySchema;

public class MinimalCompanySchemaTest {

	@Test
	public void testBuild() throws Exception {
		final ICompanySchema build = new MinimalCompanySchema();
		assertNotNull(build.getHumanType());
		assertEquals(1, build.getSchema().getTypes().size());
		assertTrue(build.getSchema().getTypes().contains(build.getHumanType()));

	}

}
