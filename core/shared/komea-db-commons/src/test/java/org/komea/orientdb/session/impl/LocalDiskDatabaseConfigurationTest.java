package org.komea.orientdb.session.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class LocalDiskDatabaseConfigurationTest {

	@Rule
	public final TemporaryFolder	temporaryFolder	= new TemporaryFolder();

	@Test
	public final void testLocalDiskDatabaseConfiguration() throws Exception {

		final String tempPath = this.temporaryFolder.getRoot().getAbsolutePath();
		assertTrue(this.temporaryFolder.getRoot().exists());
		System.err.println(tempPath);

		final OrientDocumentDatabaseFactory documentDatabaseFactory = new OrientDocumentDatabaseFactory(
				new LocalDiskDatabaseConfiguration(tempPath, "test"));

		documentDatabaseFactory.close();
	}

}
