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

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;



/**
 * This class defines a tuple.
 * 
 * @author sleroy
 */
public class ArrayListTuple implements ITuple
{
    
    
    private final List<Object> propertyMap;
    
    
    
    /**
     * Properties map
     * 
     * @param _values
     *            the properties map.
     */
    public ArrayListTuple(final List<Object> _values) {
    
    
        super();
        
        Validate.notNull(_values);
        propertyMap = Collections.unmodifiableList(_values);
        
    }
    
    
    /**
     * Defines a tuple from an array.
     */
    public ArrayListTuple(final Object value, final Object... _values) {
    
    
        Validate.notNull(_values);
        propertyMap = Lists.asList(value, _values);
        
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
        final T beanPojo = BeanUtils.instantiate(_pojoClass);
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
     * @see org.komea.product.cep.api.formula.tuple.ITuple#isSingleton()
     */
    @Override
    public boolean isSingleton() {
    
    
        return propertyMap.size() == 1;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#isSingleton(java.lang.Object)
     */
    @Override
    public boolean isSingleton(final Object _valueInTuple) {
    
    
        return isSingleton() && this.getFirst().equals(_valueInTuple);
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
    
    
        return "ArrayListTuple [propertyMap=" + propertyMap + "]";
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
