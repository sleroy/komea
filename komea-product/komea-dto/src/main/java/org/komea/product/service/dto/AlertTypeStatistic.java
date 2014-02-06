
package org.komea.product.service.dto;

import java.io.Serializable;



public class AlertTypeStatistic implements Serializable
{
    
    
    private String type;
    
    
    private String provider;
    
    private long   number;
    
    
    
    public AlertTypeStatistic() {
    
    
        super();
    }
    
    
    /**
     * @param _type
     * @param _provider
     * @param _number
     */
    public AlertTypeStatistic(final String _type, final String _provider, final long _number) {
    
    
        super();
        type = _type;
        provider = _provider;
        number = _number;
    }
    
    
    /**
     * @return the number
     */
    public long getNumber() {
    
    
        return number;
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
     * @param _number
     *            the number to set
     */
    public void setNumber(final long _number) {
    
    
        number = _number;
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
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "AlertTypeStatistic [type="
                + type + ", provider=" + provider + ", number=" + number + "]";
    }
}
