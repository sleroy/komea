/**
 * 
 */

package org.komea.product.cep.filter;



import java.io.Serializable;

import org.komea.product.cep.api.IEventFilter;



/**
 * Defines operator for filters.
 * 
 * @author sleroy
 */
public class FilterOperator
{
    
    
    /**
     * Builds an or filter; THe second filter is not evaluted if the first one is true.
     * 
     * @param _filter1
     *            the first filter
     * @param _filter2
     *            the second filter.
     * @return true
     */
    public static <T extends Serializable> IEventFilter<T> and(
            final IEventFilter<? super T> _filter1,
            final IEventFilter<? super T> _filter2) {
    
    
        return new IEventFilter<T>()
        {
            
            
            @Override
            public boolean isFiltered(final T _event) {
            
            
                return _filter1.isFiltered(_event) && _filter2.isFiltered(_event);
            }
        };
        
    }
    
    
    /**
     * Builds an or filter; THe second filter is not evaluted if the first one is true.
     * 
     * @param _filter1
     *            the first filter
     * @param _filter2
     *            the second filter.
     * @return true
     */
    public static <T extends Serializable> IEventFilter<T> not(
            final IEventFilter<? super T> _filter1) {
    
    
        return new IEventFilter<T>()
        {
            
            
            @Override
            public boolean isFiltered(final T _event) {
            
            
                return !_filter1.isFiltered(_event);
            }
        };
        
    }
    
    
    /**
     * Builds an or filter; THe second filter is not evaluted if the first one is true.
     * 
     * @param _filter1
     *            the first filter
     * @param _filter2
     *            the second filter.
     * @return true
     */
    public static <T extends Serializable> IEventFilter<T> or(
            final IEventFilter<? super T> _filter1,
            final IEventFilter<? super T> _filter2) {
    
    
        return new IEventFilter<T>()
        {
            
            
            @Override
            public boolean isFiltered(final T _event) {
            
            
                final boolean b1 = _filter1.isFiltered(_event);
                if (b1) { return b1; }
                return _filter2.isFiltered(_event);
            }
        };
        
    }
}
