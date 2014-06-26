
package org.komea.product.backend.utils;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.core.convert.converter.Converter;

import com.google.common.collect.Lists;



public class CollectionUtil
{
    
    
    /**
     * Iterate on a container and apply a treatment/ function.
     *
     * @param _iterableContainer
     * @param _treatment
     */
    public static <S, R> List<R> convertAll(
            final Iterable<S> _iterableContainer,
            final Converter<S, R> _treatment) {
    
    
        final List<R> res = new ArrayList<R>();
        for (final S valueT : _iterableContainer) {
            res.add(_treatment.convert(valueT));
        }
        return res;
    }
    
    
    /**
     * Filter a list of elements
     *
     * @param _dataFilter
     * @return
     */
    public static <T> List<T> filter(final List<T> _originalList, final IFilter<T> _dataFilter) {
    
    
        Validate.notNull(_originalList);
        Validate.notNull(_dataFilter);
        
        final List<T> res = Lists.newArrayList();
        for (final T obj : _originalList) {
            if (_dataFilter.matches(obj)) {
                res.add(obj);
            }
        }
        return res;
    }
    
    
    /**
     * Returns the first element of a criteria.
     *
     * @param _listOfItems
     *            the list of items.
     */
    public static <T> T firstElement(final List<T> _listOfItems) {
    
    
        Validate.notNull(_listOfItems);
        if (_listOfItems.isEmpty()) {
            return null;
        }
        return _listOfItems.get(0);

    }
    
    
    /**
     * Iterate on a container and apply a treatment/ function.
     *
     * @param _iterableContainer
     * @param _treatment
     */
    public static <T> void iterate(
            final Iterable<T> _iterableContainer,
            final Treatment<T> _treatment) {
    
    
        for (final T valueT : _iterableContainer) {
            _treatment.apply(valueT);
        }
        
    }
    
    
    /**
     * Returns the first element of a list of only one elemnt or null.
     *
     * @param _elements
     *            the list
     * @return the unique element or null or an exception if the list contains more than one value
     * @throws IllegalArgumentException
     */
    public static <T> T singleOrNull(final List<T> _elements) throws IllegalArgumentException {
    
    
        if (_elements.size() > 1) {
            throw new IllegalStateException("Expected zero or one results in the list");
        }
        if (_elements.isEmpty()) {
            return null;
        }
        return _elements.get(0);
    }
}
