/**
 * 
 */

package org.komea.product.cep.filter;



import org.junit.Test;
import org.komea.product.cep.query.xpath.XPathUtils;
import org.komea.product.database.alert.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class XPathFilterTest
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(XPathFilterTest.class);
    
    
    
    /**
     * Test method for {@link org.komea.product.cep.filter.XPathFilter#XPathFilter(java.lang.String)}.
     */
    @Test
    public final void testXPathFilter() throws Exception {
    
    
        final Event event = new Event();
        event.setMessage("truc");
        event.setValue(12.0d);
        LOGGER.info(XPathUtils.dumpXpath(event));
        final XPathFilter xPathFilter = new XPathFilter("message ='truc'");
        assertTrue(xPathFilter.isFiltered(event));
        final XPathFilter xPathFilter2 = new XPathFilter("value =12.0");
        assertTrue(xPathFilter2.isFiltered(event));
    }
    
}
