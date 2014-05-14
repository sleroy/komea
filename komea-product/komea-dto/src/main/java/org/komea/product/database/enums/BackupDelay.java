package org.komea.product.database.enums;

import org.komea.product.database.model.Kpi;

public enum BackupDelay {
	HOUR, DAY, WEEK, MONTH;

	/**
	 * Tests if the given delay is applied for the kpi passed in parameter.
	 * 
	 * @param _kpi
	 *            the kpi.
	 * 
	 */
	public boolean isAppliedTo(final Kpi _kpi) {
		final String cronExpression = _kpi.getCronExpression();
		try {
			final BackupDelay valueOf = BackupDelay.valueOf(cronExpression);
			return equals(valueOf);
		} catch (final IllegalArgumentException e) {
			return false;
		}

	}
}
