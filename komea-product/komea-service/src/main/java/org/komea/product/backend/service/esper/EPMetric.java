
package org.komea.product.backend.service.esper;


import org.komea.product.backend.api.IEPLMetric;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;

public class EPMetric implements IEPLMetric
{
    
    private final EPStatement createEPL;
    
    public EPMetric(final EPStatement _createEPL) {
    
        super();
        createEPL = _createEPL;
    }
    
    @Override
    public EPStatement getStatement() {
    
        return createEPL;
    }
    
    @Override
    public double getValue() {
    
        SafeIterator<EventBean> safeIterator = null;
        try {
            safeIterator = createEPL.safeIterator();
            if (safeIterator.hasNext()) {
                return (Double) safeIterator.next().getUnderlying();
            }
        } finally {
            if (safeIterator != null) {
                safeIterator.close();
            }
        }
        
        return Double.NaN;
    }
    
}
