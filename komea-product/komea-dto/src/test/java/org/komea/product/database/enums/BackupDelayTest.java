package org.komea.product.database.enums;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.komea.product.database.model.Kpi;

public class BackupDelayTest {

	@Test
	public void testIsAppliedTo() throws Exception {
		final Kpi kpi = new Kpi();

		kpi.setCronExpression("DAY");
		assertTrue(BackupDelay.DAY.isAppliedTo(kpi));
		assertFalse(BackupDelay.HOUR.isAppliedTo(kpi));
	}

	@Test
	public void testWrongExpressionIsAppliedTo() throws Exception {
		final Kpi kpi = new Kpi();

		kpi.setCronExpression("HULK");
		assertTrue(BackupDelay.DAY.isAppliedTo(kpi));
		assertFalse(BackupDelay.HOUR.isAppliedTo(kpi));
	}

}
