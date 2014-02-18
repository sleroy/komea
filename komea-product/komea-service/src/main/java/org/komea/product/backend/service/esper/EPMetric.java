
package org.komea.product.backend.service.esper;



import org.komea.product.backend.service.business.IEPMetric;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPStatement;



/**
 */
public class EPMetric implements IEPMetric
{
    
    
    private final EPStatement   epStatement;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EPMetric.class);
    
    
    
    /**
     * Constructor for EPMetric.
     * @param _epStatement EPStatement
     */
    public EPMetric(final EPStatement _epStatement) {
    
    
        epStatement = _epStatement;
        
        
    }
    
    
    /**
     * Method getDoubleValue.
     * @return double
     * @see org.komea.product.backend.service.business.IEPMetric#getDoubleValue()
     */
    @Override
    public double getDoubleValue() {
    
    
        return getValue().doubleValue();
    }
    
    
    /**
     * Method getIntValue.
     * @return int
     * @see org.komea.product.backend.service.business.IEPMetric#getIntValue()
     */
    @Override
    public int getIntValue() {
    
    
        return getValue().intValue();
    }
    
    
    /**
     * Method getLongValue.
     * @return long
     * @see org.komea.product.backend.service.business.IEPMetric#getLongValue()
     */
    @Override
    public long getLongValue() {
    
    
        return getValue().longValue();
    }
    
    
    /**
     * Method getValue.
     * @return Number
     */
    public Number getValue() {
    
    
        final Number metric_number = EPStatementResult.build(epStatement).singleResult();
        LOGGER.debug("Metric value at {} is {}", new PrettyTime(), metric_number);
        return metric_number;
    }
    
}
