
package org.komea.product.backend.api;


import com.espertech.esper.client.EPStatement;

public interface IEPLMetric
{
    
    /**
     * Returns the statement.
     * 
     * @return the statement.
     */
    EPStatement getStatement();
    
    /**
     * Returns the value
     * 
     * @return the value.
     */
    double getValue();
}
