
package org.komea.product.rest.client;


import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.kpi.KPIValueTable;
import org.komea.product.database.api.IEntity;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.service.dto.KpiKey;

public class MeasuresAPIIT extends AbstractRestClientIntegrationTestCase {
    
    @Test
    public void testLastListMeasure() throws Exception {
    
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        KpiKey kpiKey = KpiKey.ofKpiName("NUMBER_OF_BUILD_PER_DAY");
        double val = measuresAPI.lastMeasuresForKpiKey(kpiKey);
        // Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        Assert.assertEquals(0, val, 0.1);
    }
    
    @Test
    public void testRealtimeMeasure() throws Exception {
    
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        KpiKey kpiKey = KpiKey.ofKpiName("NUMBER_OF_BUILD_PER_DAY");
        KPIValueTable<IEntity> timeValues = measuresAPI.getKpiRealTimeValues(kpiKey);
        Assert.assertNotNull(timeValues);
        Assert.assertEquals("NUMBER_OF_BUILD_PER_DAY", timeValues.getKpi().getKpiKey());
        // Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        // Assert.assertFalse(projects.isEmpty());
    }
    
}
