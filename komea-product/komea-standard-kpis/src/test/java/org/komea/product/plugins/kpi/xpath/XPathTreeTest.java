/**
 * 
 */

package org.komea.product.plugins.kpi.xpath;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.cep.api.ICEPEventStorage;
import org.komea.product.cep.api.ICEPStatement;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

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
     * Test method for {@link org.komea.product.plugins.kpi.xpath.XPathTree#dumpTree()}.
     */
    @Test @Ignore
    @Ignore
    public void testDumpTree() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.kpi.xpath.XPathTree#XPathTree(org.komea.product.cep.api.ICEPStatement)}.
     */
    @Test @Ignore
    public void testXPathTree() throws Exception {
    
    
        final ICEPEventStorage mock = Mockito.mock(ICEPEventStorage.class);
        when(statement.getEventStorages()).thenReturn(Lists.newArrayList(mock));
        new XPathTree(statement);
        
        
    }
    
}
