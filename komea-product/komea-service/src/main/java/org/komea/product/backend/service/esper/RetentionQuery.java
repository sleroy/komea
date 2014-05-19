/**
 * 
 */

package org.komea.product.backend.service.esper;

import java.util.concurrent.TimeUnit;

import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.query.CEPQueryImplementation;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.cep.filter.EventSeverityFilter;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;

/**
 * This class defines the cep query to store events on a given time.
 * 
 * @author sleroy
 */
public class RetentionQuery extends CEPQueryImplementation {

	/**
	 * Builds cache retention policy
	 * 
	 * @param _retentionTime
	 *            the retention time
	 * @return the cache configuration
	 */
	public static ICacheConfiguration buildCacheRetention(final RetentionPeriod _retentionTime) {

		switch (_retentionTime) {
			case NEVER:
				return CacheConfigurationBuilder.noConfiguration();
			case ONE_HOUR:
				return CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.HOURS);
			case ONE_MONTH:
				return CacheConfigurationBuilder.expirationTimeCache(30, TimeUnit.DAYS);
			case ONE_WEEK:
				return CacheConfigurationBuilder.expirationTimeCache(7, TimeUnit.DAYS);
			case ONE_YEAR:
				return CacheConfigurationBuilder.expirationTimeCache(365, TimeUnit.DAYS);
			case ONE_DAY:
				return CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.DAYS);
			case SIX_HOURS:
				return CacheConfigurationBuilder.expirationTimeCache(6, TimeUnit.HOURS);
			case THREE_DAYS:
				return CacheConfigurationBuilder.expirationTimeCache(3, TimeUnit.DAYS);
			case THREE_MONTHS:
				return CacheConfigurationBuilder.expirationTimeCache(90, TimeUnit.DAYS);
			case TWO_WEEKS:
				return CacheConfigurationBuilder.expirationTimeCache(15, TimeUnit.DAYS);
			default:
				return CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.DAYS);
		}

	}

	private final RetentionPeriod	retentionTime;

	private final Severity	      severity;

	/**
	 * @param _severity
	 * @param _retentionTime
	 */
	public RetentionQuery(final Severity _severity, final RetentionPeriod _retentionTime) {

		super();
		severity = _severity;
		retentionTime = _retentionTime;
		setFormula(new NoKpiResultFormula());

		addFilterDefinition(FilterDefinition
		        .create()
		        .setCacheConfiguration(buildCacheRetention(retentionTime))
		        .setFilter(
		                EventFilterBuilder.create().chain(new OnlyEventFilter())
		                        .chain(new EventSeverityFilter(_severity)).build()));

	}
}
