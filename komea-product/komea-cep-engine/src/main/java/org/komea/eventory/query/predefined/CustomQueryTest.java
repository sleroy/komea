/**
 * 
 */

package org.komea.eventory.query.predefined;



import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.komea.eventory.formula.tuple.GroupByFormula;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.database.alert.EventBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class CustomQueryTest
{
    
    
    /**
     * Test method for {@link org.komea.eventory.query.predefined.CustomQuery#CustomQuery()}.
     */
    @Test(
        expected = IllegalArgumentException.class)
    public final void testCustomQuery() throws Exception {
    
    
        final CustomQuery customQuery = new CustomQuery();
        assertTrue(customQuery.getFilterDefinitions().isEmpty());
        assertNotNull(customQuery.getFormula());
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.query.predefined.CustomQuery#elFilter(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testElFilter() throws Exception {
    
    
        final CustomQuery customQuery = new CustomQuery();
        customQuery.elFilter("message=='truc'", "days(1)");
        final IFilterDefinition filterDefinition = customQuery.getFilterDefinitions().get(0);
        evaluateTestFilter(filterDefinition);
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.query.predefined.CustomQuery#groupBy(java.lang.String)}.
     */
    @Test
    public final void testGroupBy() throws Exception {
    
    
        final CustomQuery customQuery = new CustomQuery();
        customQuery.elFilter("message=='truc'", "days(1)");
        customQuery.groupBy("project");
        customQuery.formula("count(*)");
        final IFilterDefinition filterDefinition = customQuery.getFilterDefinitions().get(0);
        evaluateTestFilter(filterDefinition);
        
        assertNotNull(customQuery.getFormula() instanceof GroupByFormula);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.eventory.query.predefined.CustomQuery#indexedElFilter(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testIndexedElFilter() throws Exception {
    
    
        final CustomQuery customQuery = new CustomQuery();
        customQuery.indexedElFilter("message=='truc'", "projet", "days(1)");
        final IFilterDefinition filterDefinition = customQuery.getFilterDefinitions().get(0);
        evaluateTestFilter(filterDefinition);
        assertNotNull(filterDefinition.getCacheConfiguration().getCustomIndexer());
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.query.predefined.CustomQuery#uniqueXpathFilter(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testIndexedXpathFilter() throws Exception {
    
    
        final CustomQuery customQuery = new CustomQuery();
        customQuery.indexedXpathFilter("message='truc'", "project", "days(1)");
        final IFilterDefinition filterDefinition = customQuery.getFilterDefinitions().get(0);
        evaluateTestFilter(filterDefinition);
        assertNotNull(filterDefinition.getCacheConfiguration().getCustomIndexer());
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.query.predefined.CustomQuery#xpathFilter(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testXpathFilter() throws Exception {
    
    
        final CustomQuery customQuery = new CustomQuery();
        customQuery.xpathFilter("message='truc'", "days(1)");
        final IFilterDefinition filterDefinition = customQuery.getFilterDefinitions().get(0);
        evaluateTestFilter(filterDefinition);
        
    }
    
    
    private void evaluateTestFilter(final IFilterDefinition filterDefinition) {
    
    
        assertEquals("stream0", filterDefinition.getFilterName());
        assertEquals(Integer.valueOf(1), filterDefinition.getCacheConfiguration().getTime());
        assertEquals(TimeUnit.DAYS, filterDefinition.getCacheConfiguration().getTimeUnit());
        assertTrue(filterDefinition.getFilter().isFiltered(
                EventBuilder.newAlert().message("truc").build()));
    }
    
}
