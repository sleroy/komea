/**
 * 
 */

package org.komea.product.cep.cache;



import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheIndexer;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.cache.CacheConfigurationBuilder.CacheConfiguration;
import org.komea.product.cep.formula.ElFormula;



/**
 * @author sleroy
 */
public class CacheConfigurationParser
{
    
    
    /**
     * 
     */
    public static final String DEFAULT = "default";
    
    
    
    /**
     * Days
     * 
     * @param _unit
     *            the unit
     * @param _timeUnit
     *            the time unit.
     * @return the cache configuration.
     */
    public static ICacheConfiguration max(final int _unit) {
    
    
        return CacheConfigurationBuilder.create().maximumSize(_unit).build();
    }
    
    
    /**
     * Days
     * 
     * @param _unit
     *            the unit
     * @return the cache configuration.
     */
    public ICacheConfiguration days(final int _unit) {
    
    
        return CacheConfigurationBuilder.expirationTimeCache(_unit, TimeUnit.DAYS);
    }
    
    
    public ICacheConfiguration hours(final int _unit) {
    
    
        return CacheConfigurationBuilder.expirationTimeCache(_unit, TimeUnit.HOURS);
    }
    
    
    public ICacheConfiguration minutes(final int _unit) {
    
    
        return CacheConfigurationBuilder.expirationTimeCache(_unit, TimeUnit.MINUTES);
    }
    
    
    /**
     * Parse the configuration
     * 
     * @param _formula
     *            the formula
     * @return the cache configuration;
     */
    public ICacheConfiguration parse(final String _formula) {
    
    
        final HashMap hashMap = new HashMap();
        hashMap.put(DEFAULT, CacheConfigurationBuilder.noConfiguration());
        final ElFormula<ICacheConfiguration> elFormula =
                new ElFormula(_formula, ICacheConfiguration.class);
        elFormula.registerMethod("max", getMethod("max", int.class));
        
        elFormula.registerMethod("days", getMethod("days", int.class));
        elFormula.registerMethod("hours", getMethod("hours", int.class));
        elFormula.registerMethod("minutes", getMethod("minutes", int.class));
        return elFormula.getValue(this, hashMap);
        
    }
    
    
    /**
     * Builds a cache configuration
     * 
     * @param _cacheConfiguration
     *            the cache configuration
     * @param _elCacheIndexer
     *            the cache indexer
     * @return the cache configuration.
     */
    public ICacheConfiguration parseWithIndexer(
            final String _cacheConfiguration,
            final ICacheIndexer<?, ?> _elCacheIndexer) {
    
    
        final ICacheConfiguration parse = parse(_cacheConfiguration);
        // FIXME cache configuration.
        ((CacheConfiguration) parse).setCustomIndexer(_elCacheIndexer);
        return parse;
    }
    
    
    private Method getMethod(final String _name, final Class<?>... parameters) {
    
    
        try {
            return CacheConfigurationParser.class.getMethod(_name, parameters);
        } catch (final NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
