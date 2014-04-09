
package org.komea.product.service.dto;



import java.io.Serializable;



public class EventTypeStatistic implements Serializable
{
    
    
    private String provider;
    
    
    private String type;
    
    private int    value;
    
    
    
    public EventTypeStatistic() {
    
    
        super();
    }
    
    
    /**
     * @return the provider
     */
    public String getProvider() {
    
    
        return provider;
    }
    
    
    /**
     * @return the type
     */
    public String getType() {
    
    
        return type;
    }
    
    
    /**
     * @return the value
     */
    public int getValue() {
    
    
        return value;
    }
    
    
    /**
     * @param _provider
     *            the provider to set
     */
    public void setProvider(final String _provider) {
    
    
        provider = _provider;
    }
    
    
    /**
     * @param _type
     *            the type to set
     */
    public void setType(final String _type) {
    
    
        type = _type;
    }
    
    
    /**
     * @param _value
     *            the value to set
     */
    public void setValue(final int _value) {
    
    
        value = _value;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "EventTypeStatistic [provider="
                + provider + ", type=" + type + ", value=" + value + "]";
    }
}
