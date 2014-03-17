/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.komea.product.cep.formula.NoCEPFormula;
import org.komea.product.cep.query.CEPQueryImplementation;
import org.komea.product.cep.query.FilterDefinition;
import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;



/**
 * This class defines the cep query to store events on a given time.
 * 
 * @author sleroy
 */
public class RetentionQuery extends CEPQueryImplementation
{
    
    
    private final RetentionPeriod retentionTime;
    private final Severity        severity;
    
    
    
    /**
     * @param _severity
     * @param _retentionTime
     */
    public RetentionQuery(final Severity _severity, final RetentionPeriod _retentionTime) {
    
    
        super();
        severity = _severity;
        retentionTime = _retentionTime;
        setParameters(new HashMap<String, Object>());
        setFormula(new NoCEPFormula());
        addFilterDefinition(FilterDefinition.create()
                .setCacheConfiguration(buildCacheRetention(retentionTime))
                .setFilter(new EventSeverityFilter(severity)));
        
    }
    
    
    /**
     * Builds cache retention policy
     * 
     * @param _retentionTime
     *            the retention time
     * @return the cache configuration
     */
    private ICacheConfiguration buildCacheRetention(final RetentionPeriod _retentionTime) {
    
    
        switch (_retentionTime) {
            case NEVER:
                return CacheConfigurationBuilder.noConfiguration();
            case ONE_HOUR:
                return CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.HOURS);
            case ONE_MONTH:
                return CacheConfigurationBuilder.expirationTimeCache(30, TimeUnit.DAYS);
            case ONE_WEEK:
                return CacheConfigurationBuilder.expirationTimeCache(1, TimeUnit.HOURS);
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
}
