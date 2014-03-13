/**
 * 
 */

package org.komea.product.cep.api.formula.tuple;



import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;



/**
 * This interface defines a tuple.
 * 
 * @author sleroy
 * @param <T>
 */
public interface ITuple
{
    
    
    /**
     * Returns the tuple as a bean. Properties will be setted on fields of the pojo.
     * 
     * @return the tuple as a bean
     */
    public <T> T asBean(Class<T> _pojoClass);
    
    
    /**
     * Returns the tuple as an unmodifiable map.
     * 
     * @return the map.
     */
    public Map<String, Object> asMap();
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj);
    
    
    /**
     * Returns the property
     * 
     * @param _propertyName
     *            the property name
     * @return the property.
     */
    public <T2> T2 get(String _propertyName);
    
    
    /**
     * Returns the first property of a tuple
     * 
     * @return the property or null
     */
    public <T> T getFirst();
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode();
    
    
    /**
     * Returns true if the tuple contains only one property.
     */
    public boolean isSingleton();
    
    
    /**
     * Returns the number of properties
     * 
     * @return the number of properties.
     */
    public int size();
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString();
    
    
    /**
     * Returns the list of values.
     * 
     * @return the list of values
     */
    public Collection<Object> values();
    
    
    /**
     * Appends a new entry and returns the new tuple.
     * 
     * @param _entry
     *            the entry
     * @return the new tuple;
     */
    ITuple append(Entry<String, Object> _entry);
}
