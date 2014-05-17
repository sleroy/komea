package org.komea.eventory.api.cache;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BackupDelayTest {

	@Test
	public void testIsAppliedTo() throws Exception {

		assertTrue(BackupDelay.DAY.isAppliedTo("DAY"));
		assertFalse(BackupDelay.HOUR.isAppliedTo("DAY"));
	}

	@Test
	public void testWrongExpressionIsAppliedTo() throws Exception {

		assertTrue(BackupDelay.DAY.isAppliedTo("HULK"));
		assertFalse(BackupDelay.HOUR.isAppliedTo("HULK"));
	}

}
