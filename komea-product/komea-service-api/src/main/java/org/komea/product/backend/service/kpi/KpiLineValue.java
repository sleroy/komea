
package org.komea.product.backend.service.kpi;



import java.io.Serializable;



public class KpiLineValue<T> implements Serializable
{
    
    
    private T      entity;
    private Double value;
    
    
    
    public KpiLineValue() {
    
    
        super();
        
    }
    
    
    /**
     * @param _entity
     * @param _value
     */
    public KpiLineValue(final T _entity, final Double _value) {
    
    
        super();
        entity = _entity;
        value = _value;
    }
    
    
    /**
     * @return the entity
     */
    public T getEntity() {
    
    
        return entity;
    }
    
    
    /**
     * @return the value
     */
    public Double getValue() {
    
    
        return value;
    }
    
    
    /**
     * @param _entity
     *            the entity to set
     */
    public void setEntity(final T _entity) {
    
    
        entity = _entity;
    }
    
    
    /**
     * @param _value
     *            the value to set
     */
    public void setValue(final Double _value) {
    
    
        value = _value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "KpiLineValue [entity=" + entity + ", value=" + value + "]";
    }
    
    
}
