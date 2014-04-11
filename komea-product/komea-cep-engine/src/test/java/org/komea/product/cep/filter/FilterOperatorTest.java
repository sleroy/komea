
package org.komea.product.cep.filter;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.IEventFilter;



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
    
    
    
    @Test @Ignore
    public final void testAnd() throws Exception {
    
    
        final IEventFilter and = FilterOperator.and(trucFilter, sizeFour);
        Assert.assertTrue(and.isFiltered("truc"));
        Assert.assertFalse(and.isFiltered("trucA"));
        
    }
    
    
    @Test @Ignore
    public final void testNot() throws Exception {
    
    
        Assert.assertTrue(trucFilter.isFiltered("truc"));
        Assert.assertFalse(FilterOperator.not(trucFilter).isFiltered("truc"));
    }
    
    
    @Test @Ignore
    public final void testOr() throws Exception {
    
    
        final IEventFilter and = FilterOperator.or(trucFilter, sizeFour);
        Assert.assertTrue(and.isFiltered("truc"));
        Assert.assertFalse(and.isFiltered("trucA"));
        Assert.assertTrue(and.isFiltered("ZXYZ"));
        
        
    }
    
}
