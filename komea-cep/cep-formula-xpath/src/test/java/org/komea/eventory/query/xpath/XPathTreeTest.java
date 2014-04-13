/**
 * 
 */

package org.komea.eventory.query.xpath;



import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.engine.ICEPEventStorage;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.filter.Event;
import org.komea.eventory.formula.tuple.EventGroup;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class XPathTreeTest
{
    
    
    @Mock
    private ICEPStatement statement;
    
    
    
    /**
     * Test method for {@link org.komea.eventory.query.xpath.XPathTree#dumpTree()}.
     */
    @Test
    public void testDumpTree() throws Exception {
    
    
        final ICacheStorage cacheStorage = Mockito.mock(ICacheStorage.class);
        final ICEPEventStorage eventStorage = Mockito.mock(ICEPEventStorage.class);
        when(eventStorage.getFilterName()).thenReturn("filter");
        when(eventStorage.getCache()).thenReturn(cacheStorage);
        when(cacheStorage.getAllValues()).thenReturn(Collections.EMPTY_LIST);
        when(statement.getEventStorages()).thenReturn(Lists.newArrayList(eventStorage));
        final XPathTree xPathTree = new XPathTree(statement);
        final String string = xPathTree.dumpTree();
        System.out.println("/streams[1]\n" + "/event[1]/name");
        assertEquals("/streams[1]\n" + "/streams[1]/name\n" + "", string);
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.query.xpath.XPathTree#XPathTree(org.komea.eventory.api.engine.ICEPStatement)}.
     */
    @Test
    public void testXPathTree() throws Exception {
    
    
        final ICacheStorage cacheStorage = Mockito.mock(ICacheStorage.class);
        final ICEPEventStorage eventStorage = Mockito.mock(ICEPEventStorage.class);
        when(eventStorage.getFilterName()).thenReturn("filter");
        when(eventStorage.getCache()).thenReturn(cacheStorage);
        when(cacheStorage.getAllValues()).thenReturn(Collections.EMPTY_LIST);
        when(statement.getEventStorages()).thenReturn(Lists.newArrayList(eventStorage));
        final XPathTree xPathTree = new XPathTree(statement);
        assertEquals(1, xPathTree.getStreams().size());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.eventory.query.xpath.XPathTree#XPathTree(org.komea.eventory.api.formula.tuple.IEventGroup)}.
     */
    @Test
    public void testXPathTreeIEventGroup() throws Exception {
    
    
        final EventGroup value = new EventGroup();
        value.addEvent(mock(Event.class));
        final XPathTree xPathTree = new XPathTree(value);
        assertEquals(1, xPathTree.getStreams().size());
        assertEquals(1, xPathTree.getStreams().get(0).getEvent().size());
        
    }
    
}
