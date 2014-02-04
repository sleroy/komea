
package org.komea.product.backend.service.kpi;



/**
 * This interface is a simple wrapper over an EPStatement to obtain the value.
 * 
 * @author sleroy
 */
public interface IEPMetric
{
    
    
    /**
     * Returns the value
     * 
     * @return the value;
     */
    double getDoubleValue();
    
    
    /**
     * Returns the value
     * 
     * @return the value;
     */
    int getIntValue();
    
    
    /**
     * Returns the value
     * 
     * @return the value;
     */
    long getLongValue();
    
    
}
