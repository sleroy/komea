/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.utils.ClassUtils;
import org.komea.product.cep.api.formula.tuple.ITuple;



/**
 * This class defines a tuple.
 * 
 * @author sleroy
 */
public class ArrayListTuple implements ITuple
{
    
    
    private final List<Object> propertyMap;
    
    
    
    /**
     * Builds a tuple;
     */
    public ArrayListTuple() {
    
    
        super();
        propertyMap = Collections.emptyList();
    }
    
    
    /**
     * Properties map
     * 
     * @param _values
     *            the properties map.
     */
    public ArrayListTuple(final List<?> _values) {
    
    
        super();
        
        Validate.notNull(_values);
        propertyMap = Collections.unmodifiableList(_values);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#append(java.util.Map.Entry)
     */
    @Override
    public ITuple append(final Object _entry) {
    
    
        final ArrayList<Object> propertyMap2 = new ArrayList<Object>(propertyMap.size() + 1);
        propertyMap2.addAll(propertyMap);
        propertyMap2.add(_entry);
        return new ArrayListTuple(propertyMap2);
    }
    
    
    @Override
    public <T> T asBean(final String[] _keySet, final Class<T> _pojoClass) {
    
    
        Validate.isTrue(_keySet.length == propertyMap.size());
        final T beanPojo = ClassUtils.instantiate(_pojoClass);
        BeanUtil.populateBean(beanPojo, asMap(_keySet));
        return beanPojo;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.formula.tuple.ITuple#asMap()
     */
    @Override
    public Map<String, Object> asMap(final String[] _keySet) {
    
    
        Validate.isTrue(_keySet.length == propertyMap.size());
        final Map<String, Object> hashMap = new HashMap<String, Object>();
        for (int i = 0; i < _keySet.length; ++i) {
            hashMap.put(_keySet[i], propertyMap.get(i));
        }
        return hashMap;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final ITuple _o) {
    
    
        if (this == _o) { return 0; }
        if (_o == null) { return 1; }
        for (int i = 0; i < propertyMap.size(); ++i) {
            int res = 0;
            final Object object = propertyMap.get(i);
            if (object instanceof Comparable) {
                res = ((Comparable) object).compareTo(_o.values().get(i));
            } else {
                res = object.hashCode() - _o.values().hashCode();
            }
            if (res != 0) { return res; }
        }
        
        return propertyMap.isEmpty() ? hashCode() - _o.hashCode() : 0;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        final ArrayListTuple other = (ArrayListTuple) obj;
        if (propertyMap == null) {
            if (other.propertyMap != null) { return false; }
        } else if (!propertyMap.equals(other.propertyMap)) { return false; }
        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#getByIndex(int)
     */
    @Override
    public <T> T getFirst() {
    
    
        if (propertyMap.isEmpty()) { return null; }
        return (T) propertyMap.get(0);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (propertyMap == null ? 0 : propertyMap.hashCode());
        return result;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#isSingleton(java.lang.Object)
     */
    @Override
    public boolean hasSingletonValue(final Object _valueInTuple) {
    
    
        return isSingleton() && this.getFirst().equals(_valueInTuple);
    }
    
    
    /**
     * @return true if the tuple does not contains event.
     */
    public boolean isEmpty() {
    
    
        return propertyMap.isEmpty();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#isSingleton()
     */
    @Override
    public boolean isSingleton() {
    
    
        return propertyMap.size() == 1;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#size()
     */
    @Override
    public int size() {
    
    
        return propertyMap.size();
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "ArrayListTuple <propertyMap=" + propertyMap + ">";
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#values()
     */
    @Override
    public List<Object> values() {
    
    
        return propertyMap;
    }
}
