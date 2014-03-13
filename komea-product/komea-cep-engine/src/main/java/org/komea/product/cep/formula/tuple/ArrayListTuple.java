/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jodd.bean.BeanUtil;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.utils.KomeaEntry;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.springframework.beans.BeanUtils;



/**
 * This class defines a tuple.
 * 
 * @author sleroy
 */
public class ArrayListTuple implements ITuple
{
    
    
    private final List<Entry<String, Object>> propertyMap;
    
    
    
    /**
     * Builds an tuple from its own data implementation.
     * 
     * @param _propertyMapValues
     *            the data.
     */
    public ArrayListTuple(final ArrayList<Entry<String, Object>> _propertyMapValues) {
    
    
        propertyMap = _propertyMapValues;
        
    }
    
    
    /**
     * Properties map
     * 
     * @param _propertiesMap
     *            the properties map.
     */
    public ArrayListTuple(final Map<String, Object> _propertiesMap) {
    
    
        super();
        
        Validate.notNull(_propertiesMap);
        propertyMap = new ArrayList<Map.Entry<String, Object>>(_propertiesMap.size());
        for (final Entry<String, Object> entry : _propertiesMap.entrySet()) {
            propertyMap.add(new KomeaEntry(entry.getKey(), entry.getValue()));
        }
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
    public ArrayListTuple(final String[] _strings, final Object... _values) {
    
    
        Validate.notNull(_strings);
        Validate.notNull(_values);
        Validate.isTrue(_strings.length == _values.length, "expected "
                + _strings.length + " values in both parameters");
        propertyMap = new ArrayList<Map.Entry<String, Object>>(_strings.length);
        for (int i = 0; i < _strings.length; ++i) {
            propertyMap.add(new KomeaEntry(_strings[i], _values[i]));
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#append(java.util.Map.Entry)
     */
    @Override
    public ITuple append(final Entry<String, Object> _entry) {
    
    
        final ArrayList<Entry<String, Object>> propertyMap2 =
                new ArrayList<Map.Entry<String, Object>>(propertyMap.size() + 1);
        propertyMap2.addAll(propertyMap);
        propertyMap2.add(_entry);
        return new ArrayListTuple(propertyMap2);
    }
    
    
    @Override
    public <T> T asBean(final Class<T> _pojoClass) {
    
    
        final T beanPojo = BeanUtils.instantiate(_pojoClass);
        BeanUtil.populateBean(beanPojo, asMap());
        return beanPojo;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.formula.tuple.ITuple#asMap()
     */
    @Override
    public Map<String, Object> asMap() {
    
    
        final Map<String, Object> hashMap = new HashMap<String, Object>();
        for (final Entry<String, Object> entry : propertyMap) {
            hashMap.put(entry.getKey(), entry.getValue());
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
     * @see org.komea.product.cep.formula.tuple.ITuple#getProperty(java.lang.String)
     */
    @Override
    public <T2> T2 get(final String _propertyName) {
    
    
        Validate.notEmpty(_propertyName);
        for (final Entry<String, Object> entry : propertyMap) {
            if (_propertyName.equals(entry.getKey())) { return (T2) entry.getValue(); }
        }
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.ITuple#getByIndex(int)
     */
    @Override
    public <T> T getFirst() {
    
    
        return (T) propertyMap.get(0).getValue();
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
        for (final Entry<String, Object> entry : propertyMap) {
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
    
    
        final List<Object> aList = new ArrayList<Object>();
        for (final Entry<String, Object> entry : propertyMap) {
            aList.add(entry.getValue());
        }
        return aList;
    }
}
