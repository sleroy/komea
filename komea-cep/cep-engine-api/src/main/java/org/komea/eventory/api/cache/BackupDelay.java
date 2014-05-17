package org.komea.eventory.api.cache;

public enum BackupDelay {
	HOUR, DAY, WEEK, MONTH;

	/**
	 * Tests if the given delay is applied for the kpi passed in parameter.
	 * Always true for DAY enum if the given kpi does not provide any value.
	 * 
	 * @param _cronExpression
	 *            the cron expression.
	 * 
	 */
	public boolean isAppliedTo(final String _cronExpression) {
		try {
			final BackupDelay valueOf = BackupDelay.valueOf(_cronExpression);
			return equals(valueOf);
		} catch (final IllegalArgumentException e) {
			return BackupDelay.DAY.equals(this); // Provided default value DAY
		}

	}
}
