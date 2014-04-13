/**
 * 
 */

package org.komea.eventory.cache;



import java.util.concurrent.TimeUnit;

import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.api.cache.ICacheIndexer;



/**
 * This class is a builder for cache configurations.
 * 
 * @author sleroy
 */
public class CacheConfigurationBuilder
{
    
    
    public static class CacheConfiguration implements ICacheConfiguration
    {
        
        
        /**
         * 
         */
        private static final long serialVersionUID = 4722910638471754852L;
        
        
        @SuppressWarnings("rawtypes")
        private ICacheIndexer     customIndexer    = null;
        
        
        private boolean           enableStats      = false;
        
        
        private Integer           maximumSize;
        
        
        private Integer           time;
        
        
        private TimeUnit          timeUnit;
        
        
        
        public CacheConfiguration() {
        
        
            super();
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.cache.ICacheConfiguration#getCustomIndexer()
         */
        @Override
        public ICacheIndexer getCustomIndexer() {
        
        
            return customIndexer;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.cache.ICacheConfiguration#getMaximumSize()
         */
        @Override
        public Integer getMaximumSize() {
        
        
            return maximumSize;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.cache.ICacheConfiguration#getTime()
         */
        @Override
        public Integer getTime() {
        
        
            return time;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.cache.ICacheConfiguration#getTimeUnit()
         */
        @Override
        public TimeUnit getTimeUnit() {
        
        
            return timeUnit;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.cache.ICacheConfiguration#hasCustomIndexer()
         */
        @Override
        public boolean hasCustomIndexer() {
        
        
            return customIndexer != null;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.cache.ICacheConfiguration#hasMaximumSizePolicy()
         */
        @Override
        public boolean hasMaximumSizePolicy() {
        
        
            return maximumSize != null;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.cache.ICacheConfiguration#isNoPolicy()
         */
        @Override
        public boolean hasNoPolicy() {
        
        
            return maximumSize == null && time == null && timeUnit == null;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.cache.ICacheConfiguration#hasTimePolicy()
         */
        @Override
        public boolean hasTimePolicy() {
        
        
            return time != null && timeUnit != null;
        }
        
        
        @Override
        public boolean isEnableStats() {
        
        
            return enableStats;
        }
        
        
        public void setCustomIndexer(final ICacheIndexer _customIndexer) {
        
        
            customIndexer = _customIndexer;
        }
        
        
        public void setEnableStats(final boolean _enableStats) {
        
        
            enableStats = _enableStats;
        }
        
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
        
        
            return "CacheConfiguration [maximumSize="
                    + maximumSize + ", time=" + time + ", timeUnit=" + timeUnit + "]";
        }
        
        
    }
    
    
    
    /**
     * Returns a cache configuration with expiration time on elements.
     * 
     * @return the cache configuration.
     */
    public static CacheConfigurationBuilder create() {
    
    
        return new CacheConfigurationBuilder();
    }
    
    
    /**
     * Builds an expiration time cache.
     * 
     * @param _i
     * @param _timeUnit
     * @return
     */
    public static ICacheConfiguration expirationTimeCache(final int _i, final TimeUnit _timeUnit) {
    
    
        return CacheConfigurationBuilder.create().expirationTime(_i, _timeUnit).build();
        
    }
    
    
    /**
     * Returns a cache configuration where no limit are provided. THe elments will be NEVER evicted.
     * 
     * @return the cache configuration.
     */
    public static ICacheConfiguration noConfiguration() {
    
    
        return new CacheConfigurationBuilder().cacheConfiguration;
    }
    
    
    
    private final CacheConfiguration cacheConfiguration = new CacheConfiguration();
    
    
    
    /**
     * Cache configuration.
     */
    private CacheConfigurationBuilder() {
    
    
        super();
    }
    
    
    /**
     * Returns the cache configuration built.
     */
    public ICacheConfiguration build() {
    
    
        return cacheConfiguration;
    }
    
    
    /**
     * Returns a cache configuration with expiration time on elements.
     * 
     * @return the cache configuration.
     */
    public CacheConfigurationBuilder expirationTime(final int _time, final TimeUnit _unit) {
    
    
        cacheConfiguration.time = _time;
        cacheConfiguration.timeUnit = _unit;
        
        return this;
    }
    
    
    /**
     * Returns a cache configuration with expiration time on elements.
     * 
     * @return the cache configuration.
     */
    public CacheConfigurationBuilder maximumSize(final int _maximumSize) {
    
    
        cacheConfiguration.maximumSize = _maximumSize;
        return this;
    }
    
    
    /**
     * Defines a custom cache indexer
     * 
     * @param _indexer
     *            the indexer
     * @return the configuration builder
     */
    public CacheConfigurationBuilder withCustomIndexer(final ICacheIndexer _indexer) {
    
    
        cacheConfiguration.setCustomIndexer(_indexer);
        return this;
    }
    
    
    /**
     * Enables cache stats.
     */
    public CacheConfigurationBuilder withStats() {
    
    
        cacheConfiguration.setEnableStats(true);
        return this;
    }
}
