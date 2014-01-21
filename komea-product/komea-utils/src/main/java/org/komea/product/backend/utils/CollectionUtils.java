
package org.komea.product.backend.utils;



import java.util.List;



public class CollectionUtils
{
    
    
    /**
     * Returns the first element of a list of only one elemnt or null.
     * 
     * @param _elements
     *            the list
     * @return the unique element or null or an exception if the list contains more than one value
     * @throws IllegalArgumentException
     */
    public static <T> T singleOrNull(final List<T> _elements) throws IllegalArgumentException {
    
    
        if (_elements.size() > 1) { throw new IllegalStateException(
                "Expected zero or one results in the list"); }
        if (_elements.isEmpty()) { return null; }
        return _elements.get(0);
    }
}
