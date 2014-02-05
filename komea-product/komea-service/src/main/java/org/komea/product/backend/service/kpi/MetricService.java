
package org.komea.product.backend.service.kpi;



import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.reactor.EPMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MetricService implements IMetricService
{
    
    
    @Autowired
    private IEsperEngine esperEngine;
    
    
    
    public MetricService() {
    
    
        super();
    }
    
    
    @Override
    public IEPMetric findMeasure(final String _measureName) {
    
    
        return new EPMetric(esperEngine.getStatementOrFail(_measureName));
        
    }
    
    
    public IEsperEngine getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public void setEsperEngine(final IEsperEngine _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
}
