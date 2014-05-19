package org.komea.product.plugins.kpi.standard.bugzilla;

import java.util.concurrent.TimeUnit;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.cache.CacheConfigurationBuilder;

public abstract class AbstractCEPQueryImplementation implements ICEPQueryImplementation {

	protected final int	      time;
	protected final TimeUnit	timeUnit;
	private final BackupDelay	delay;

	public AbstractCEPQueryImplementation(final BackupDelay _delay) {
		super();
		delay = _delay;
		switch (_delay) {
			case DAY:
				time = 1;
				timeUnit = TimeUnit.DAYS;
				break;
			case HOUR:
				time = 1;
				timeUnit = TimeUnit.HOURS;
				break;
			case MONTH:
				time = 30;
				timeUnit = TimeUnit.DAYS;
				break;
			case WEEK:
				time = 7;
				timeUnit = TimeUnit.DAYS;
				break;
			default:
				throw new UnsupportedOperationException();
		}
	}

	@Override
	public BackupDelay getBackupDelay() {

		return delay;
	}

	protected ICacheConfiguration buildExpirationCache() {

		return prepareCacheConfiguration().build();
	}

	protected CacheConfigurationBuilder prepareCacheConfiguration() {
		return CacheConfigurationBuilder.create().expirationTime(time, timeUnit);
	}
}