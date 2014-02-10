
package org.komea.product.backend.service.kpi;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.esper.EPMetric;
import org.komea.product.backend.service.esper.EsperEngineBean;
import org.komea.product.backend.service.esper.QueryDefinition;

import com.espertech.esper.client.EPStatement;



public class EPMetricTest
{
    
    
    @Test
    public final void test() {
    
    
        final EsperEngineBean esperEngineBean = new EsperEngineBean();
        esperEngineBean.init();
        esperEngineBean.createOrUpdateEPLQuery(new QueryDefinition("SELECT COUNT(*) FROM Event",
                "DEMO_ESPER"));
        
        final EPStatement statement = esperEngineBean.getStatement("DEMO_ESPER");
        final EPMetric epMetric = new EPMetric(statement);
        Assert.assertEquals(0, epMetric.getIntValue());
    }
}
