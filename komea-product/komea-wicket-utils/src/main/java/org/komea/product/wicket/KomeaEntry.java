
package org.komea.product.wicket;



import java.io.Serializable;
import java.util.Map.Entry;



public class KomeaEntry<T1 extends Serializable, T2 extends Serializable> implements Entry<T1, T2>,
        Serializable
{
    
    
    private final T1 key;
    
    
    private T2       value;
    
    
    
    public KomeaEntry(final T1 _key, final T2 _value) {
    
    
        super();
        key = _key;
        value = _value;
    }
    
    
    @Override
    public T1 getKey() {
    
    
        return key;
    }
    
    
    @Override
    public T2 getValue() {
    
    
        return value;
    }
    
    
    @Override
    public T2 setValue(final T2 _value) {
    
    
        return value = _value;
    }
    
    
}
