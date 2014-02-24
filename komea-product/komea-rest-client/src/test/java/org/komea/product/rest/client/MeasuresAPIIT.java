
package org.komea.product.rest.client;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.service.dto.KpiKey;



public class MeasuresAPIIT extends AbstractRestClientIntegrationTestCase
{
    
    
    @Test()
    public void testLastListMeasure() throws Exception {
    
    
        final IMeasuresAPI measuresAPI =
                RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("lines", EntityType.PERSON, 1);
        measuresAPI.lastMeasuresForKpiKey(kpiKey);
    }
    
}
