
package org.komea.eventory.filter;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.filter.FilterOperator;
import org.komea.product.cep.api.IEventFilter;

import static org.junit.Assert.assertNotNull;



public class FilterOperatorTest
{
    
    
    private final IEventFilter gniFilter  = new IEventFilter()
                                          {
                                              
                                              
                                              @Override
                                              public boolean isFiltered(final Serializable _event) {
                                              
                                              
                                                  return "gni".equals(_event);
                                              }
                                          };
    
    private final IEventFilter sizeFour   = new IEventFilter()
                                          {
                                              
                                              
                                              @Override
                                              public boolean isFiltered(final Serializable _event) {
                                              
                                              
                                                  return _event.toString().length() == 4;
                                              }
                                          };
    
    
    private final IEventFilter trucFilter = new IEventFilter()
                                          {
                                              
                                              
                                              @Override
                                              public boolean isFiltered(final Serializable _event) {
                                              
                                              
                                                  return "truc".equals(_event);
                                              }
                                          };
    
    
    
    @Test
    public final void testAnd() throws Exception {
    
    
        final IEventFilter and = FilterOperator.and(trucFilter, sizeFour);
        Assert.assertTrue(and.isFiltered("truc"));
        Assert.assertFalse(and.isFiltered("trucA"));
        assertNotNull(and.toString());
    }
    
    
    @Test
    public final void testNot() throws Exception {
    
    
        Assert.assertTrue(trucFilter.isFiltered("truc"));
        final IEventFilter notFilter = FilterOperator.not(trucFilter);
        Assert.assertFalse(notFilter.isFiltered("truc"));
        assertNotNull(notFilter.toString());
    }
    
    
    @Test
    public final void testOr() throws Exception {
    
    
        final IEventFilter orFilter = FilterOperator.or(trucFilter, sizeFour);
        Assert.assertTrue(orFilter.isFiltered("truc"));
        Assert.assertFalse(orFilter.isFiltered("trucA"));
        Assert.assertTrue(orFilter.isFiltered("ZXYZ"));
        assertNotNull(orFilter.toString());
        
        
    }
    
}
