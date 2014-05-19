/**
 * 
 */

package org.komea.product.backend.service.esper;



import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.TimeUnit;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.product.cep.tester.CEPQueryTester;
import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;



/**
 * @author sleroy
 */
public class RetentionQueryTest
{
    
    
    /**
     * @author sleroy
     */
    private final class CacheConfigurationMatcher extends
            CustomTypeSafeMatcher<ICacheConfiguration>
    {
        
        
        private final TimeUnit timeUnit;
        private final Integer  unit;
        
        
        
        /**
         * @param _description
         */
        public CacheConfigurationMatcher(final TimeUnit _timeUnit, final Integer _unit) {
        
        
            super("Cache configuration is invalid.");
            timeUnit = _timeUnit;
            unit = _unit;
        }
        
        
        @Override
        protected boolean matchesSafely(final ICacheConfiguration _item) {
        
        
            final boolean timeMatch = Matchers.equalTo(unit).matches(_item.getTime());
            if (!timeMatch) {
                System.err.println("Time is different " + unit + " <-> " + _item.getTime());
            }
            final boolean timeUnitMatch = Matchers.equalTo(timeUnit).matches(_item.getTimeUnit());
            if (!timeUnitMatch) {
                System.err.println("Time unit is different "
                        + timeUnit + " <-> " + _item.getTimeUnit());
            }
            return timeMatch && timeUnitMatch;
        }
    }
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.RetentionQuery#buildCacheRetention(org.komea.product.database.enums.RetentionPeriod)}.
     */
    @Test
    public void testBuildCacheRetention() throws Exception {
    
    
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.NEVER),
                new CacheConfigurationMatcher(null, null));
        
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.ONE_DAY),
                new CacheConfigurationMatcher(TimeUnit.DAYS, 1));
        
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.ONE_HOUR),
                new CacheConfigurationMatcher(TimeUnit.HOURS, 1));
        
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.ONE_MONTH),
                new CacheConfigurationMatcher(TimeUnit.DAYS, 30));
        
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.ONE_WEEK),
                new CacheConfigurationMatcher(TimeUnit.DAYS, 7));
        
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.ONE_YEAR),
                new CacheConfigurationMatcher(TimeUnit.DAYS, 365));
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.SIX_HOURS),
                new CacheConfigurationMatcher(TimeUnit.HOURS, 6));
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.THREE_DAYS),
                new CacheConfigurationMatcher(TimeUnit.DAYS, 3));
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.THREE_MONTHS),
                new CacheConfigurationMatcher(TimeUnit.DAYS, 90));
        assertThat(RetentionQuery.buildCacheRetention(RetentionPeriod.TWO_WEEKS),
                new CacheConfigurationMatcher(TimeUnit.DAYS, 15));
        
    }
    
    
    @Test()
    public void testQueryRetention() {
    
    
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        newTest.withQuery(new RetentionQuery(Severity.BLOCKER, RetentionPeriod.ONE_HOUR))
                .sendEvent(JenkinsEventFactory.sendBuildComplete("bla", 12, "truc"), 3)
                .sendEvent(JenkinsEventFactory.sendBuildFailed("bla", 12, "truc"), 2).dump()
                .expectStorageSize(2);
        newTest.getMockEventTypes().get("build_failed").setSeverity(Severity.BLOCKER);
        newTest.runTest();
    }
}
