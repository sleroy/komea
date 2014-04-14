/**
 * 
 */

package org.komea.eventory.formula;



import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.jxpath.BasicVariables;
import org.apache.commons.jxpath.JXPathContext;
import org.junit.Test;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.engine.ICEPEventStorage;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.api.formula.tuple.IEventGroup;
import org.komea.eventory.filter.Event;
import org.komea.eventory.query.xpath.XPathTree;
import org.komea.eventory.query.xpath.XPathUtils;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class XPathFormulaTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.eventory.formula.XPathFormula#buildJXpathContext(java.util.Map, org.komea.eventory.query.xpath.XPathTree)} .
     */
    @Test
    public final void testBuildJXpathContext() throws Exception {
    
    
        final XPathFormula xPathFormula = new XPathFormula("count(/streams/event)");
        final IEventGroup eventGroup = mock(IEventGroup.class);
        final List liste = fakeEventList();
        when(eventGroup.getEvents()).thenReturn(liste);
        final XPathTree xPathTree = new XPathTree(eventGroup);
        System.out.println(XPathUtils.dumpXpath(xPathTree));
        final JXPathContext buildJXpathContext =
                xPathFormula.buildJXpathContext(Collections.<String, Object> emptyMap(), xPathTree);
        assertNotNull(buildJXpathContext);
        assertEquals(xPathTree, buildJXpathContext.getContextBean());
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.formula.XPathFormula#buildXPathVariables(java.util.Map)}.
     */
    @Test
    public final void testBuildXPathVariables() throws Exception {
    
    
        final XPathFormula xPathFormula = new XPathFormula("//*");
        final HashMap<String, Object> parameters = new HashMap<String, Object>();
        final String varName = "var";
        parameters.put(varName, "b");
        final BasicVariables buildXPathVariables = xPathFormula.buildXPathVariables(parameters);
        assertEquals("b", buildXPathVariables.getVariable(varName));
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.formula.XPathFormula#compute(org.komea.eventory.api.engine.ICEPStatement, java.util.Map)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testCompute() throws Exception {
    
    
        final XPathFormula xPathFormula = new XPathFormula("count(//event)");
        final ICEPStatement statement = mock(ICEPStatement.class);
        final List<Event> eventList = fakeEventList();
        final ICEPEventStorage<?> eventStorage = mock(ICEPEventStorage.class);
        final ICacheStorage iCacheStorage = mock(ICacheStorage.class);
        final List listStorages = Lists.newArrayList(eventStorage);
        
        when(eventStorage.getFilterName()).thenReturn("defaultstorage");
        when(eventStorage.getCache()).thenReturn(iCacheStorage);
        when(iCacheStorage.getAllValues()).thenReturn(eventList);
        
        
        when(statement.getEventStorages()).thenReturn(listStorages);
        final ICEPResult compute = xPathFormula.compute(statement, Collections.EMPTY_MAP);
        assertEquals(Double.valueOf(1), compute.asNumber());
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.formula.XPathFormula#XPathFormula(java.lang.String)}.
     */
    @Test
    public final void testXPathFormula() throws Exception {
    
    
        assertNotNull(new XPathFormula("//*"));
    }
    
    
    private List fakeEventList() {
    
    
        final Event event = new Event();
        event.setMessage("example");
        event.setValue(12);
        final List liste = Lists.newArrayList(event);
        return liste;
    }
}
