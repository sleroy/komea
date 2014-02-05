
package org.komea.product.backend.esper.reactor;



import org.komea.product.backend.service.kpi.IEPMetric;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPStatement;



public class EPMetric implements IEPMetric
{
    
    
    private final EPStatement   epStatement;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EPMetric.class);
    
    
    
    public EPMetric(final EPStatement _epStatement) {
    
    
        epStatement = _epStatement;
        
        
    }
    
    
    @Override
    public double getDoubleValue() {
    
    
        return getValue().doubleValue();
    }
    
    
    @Override
    public int getIntValue() {
    
    
        return getValue().intValue();
    }
    
    
    @Override
    public long getLongValue() {
    
    
        return getValue().longValue();
    }
    
    
    public Number getValue() {
    
    
        final Number metric_number = EPStatementResult.build(epStatement).singleResult();
        LOGGER.debug("Metric value at {} is {}", new PrettyTime(), metric_number);
        return metric_number;
    }
    
}
