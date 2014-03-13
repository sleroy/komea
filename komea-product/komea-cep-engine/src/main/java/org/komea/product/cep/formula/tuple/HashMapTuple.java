/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jodd.bean.BeanUtil;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.springframework.beans.BeanUtils;



/**
 * This class defines a tuple.
 * 
 * @author sleroy
 */
public class HashMapTuple implements ITuple
{
    
    
    private final Map<String, Object> propertyMap;
    
    
    
    /**
     * Build a tuple
     * 
     * @param _items
     *            the items
     */
    public HashMapTuple(final List<String> _keys, @SuppressWarnings("rawtypes")
    final List _items) {
    
    
        Validate.isTrue(_keys.size() == _items.size(),
                "Expected same number of values in parameters");
        
        propertyMap = new LinkedHashMap<String, Object>();
        for (int i = 0; i < _keys.size(); ++i) {
            propertyMap.put(_keys.get(i), _items.get(i));
        }
    }
    
    
    /**
     * Properties map
     * 
     * @param _propertiesMap
     *            the properties map.
     */
    public HashMapTuple(final Map<String, Object> _propertiesMap) {
    
    
        super();
        
        Validate.notNull(_propertiesMap);
        propertyMap = Collections.unmodifiableMap(_propertiesMap);
    }
    
    
    /**
     * Defines a tuple
     * 
     * @param _strings
     *            the strings
     * @param _name
     *            the name
     * @param _eventType
     *            the event type
     */
    public HashMapTuple(final String[] _strings, final Object... _values) {
    
    
        propertyMap = new LinkedHashMap<String, Object>();
        Validate.notNull(_strings);
        Validate.notNull(_values);
        Validate.isTrue(_strings.length == _values.length, "expected "
                + _strings.length + " values in both parameters");
        for (int i = 0; i < _strings.length; ++i) {
            propertyMap.put(_strings[i], _values[i]);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#append(java.util.Map.Entry)
     */
    @Override
    public ITuple append(final Entry<String, Object> _entry) {
    
    
        final HashMap hashMap = new HashMap(propertyMap);
        hashMap.put(_entry.getKey(), _entry.getValue());
        
        return new HashMapTuple(hashMap);
    }
    
    
    @Override
    public <T> T asBean(final Class<T> _pojoClass) {
    
    
        final T beanPojo = BeanUtils.instantiate(_pojoClass);
        BeanUtil.populateBean(beanPojo, propertyMap);
        return beanPojo;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.formula.tuple.ITuple#asMap()
     */
    @Override
    public Map<String, Object> asMap() {
    
    
        return propertyMap;
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
        final HashMapTuple other = (HashMapTuple) obj;
        if (propertyMap == null) {
            if (other.propertyMap != null) { return false; }
        } else if (!propertyMap.equals(other.propertyMap)) { return false; }
        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.formula.tuple.ITuple#getProperty(java.lang.String)
     */
    @Override
    public <T2> T2 get(final String _propertyName) {
    
    
        return (T2) propertyMap.get(_propertyName);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#getByIndex(int)
     */
    @Override
    public <T> T getFirst() {
    
    
        return (T) propertyMap.values().iterator().next();
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
    
    
        final StringBuilder sBuilder = new StringBuilder();
        for (final Entry<String, Object> entry : propertyMap.entrySet()) {
            sBuilder.append("\n\t").append(entry.getKey()).append(" -> ").append(entry.getValue())
                    .append("");
        }
        
        return "HashMapTuple [" + sBuilder.toString() + "]";
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#values()
     */
    @Override
    public Collection<Object> values() {
    
    
        return propertyMap.values();
    }
}
