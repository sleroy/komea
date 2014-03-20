/**
 * 
 */

package org.komea.product.wicket.utils;



import java.util.Collections;
import java.util.Iterator;
import java.util.List;



/**
 * @author sleroy
 */
public class IteratorUtil
{
    
    
    /**
     * Build an iterator over values contained between first and count
     * 
     * @param _values
     *            the values
     * @param _first
     *            the index of the first value
     * @param _count
     *            the number of value.
     * @return the iterator.
     */
    public static <T> Iterator<T> buildIterator(
            final List<T> _values,
            final long _first,
            final long _count) {
    
    
        if (_first <= 0 || _first > _values.size()) { return Collections.EMPTY_LIST.iterator(); }
        final int lastPosition = (int) (_first + _count);
        final List<T> subList =
                _values.subList((int) _first, Math.min(lastPosition, _values.size()));
        return subList.iterator();
        
    }
    
    
    /**
     * 
     */
    public IteratorUtil() {
    
    
        super();
    }
    
}
