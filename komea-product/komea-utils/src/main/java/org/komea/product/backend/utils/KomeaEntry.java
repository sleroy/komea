
package org.komea.product.backend.utils;



import java.io.Serializable;
import java.util.Map.Entry;



public class KomeaEntry<T1, T2> implements Entry<T1, T2>, Serializable
{
    
    
    private final T1 key;
    
    
    private T2       value;
    
    
    
    public KomeaEntry(final T1 _key, final T2 _value) {
    
    
        super();
        key = _key;
        value = _value;
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
        final KomeaEntry other = (KomeaEntry) obj;
        if (key == null) {
            if (other.key != null) { return false; }
        } else if (!key.equals(other.key)) { return false; }
        if (value == null) {
            if (other.value != null) { return false; }
        } else if (!value.equals(other.value)) { return false; }
        return true;
    }
    
    
    @Override
    public T1 getKey() {
    
    
        return key;
    }
    
    
    @Override
    public T2 getValue() {
    
    
        return value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (key == null ? 0 : key.hashCode());
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }
    
    
    @Override
    public T2 setValue(final T2 _value) {
    
    
        return value = _value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "KomeaEntry [key=" + key + ", value=" + value + "]";
    }
    
    
}
