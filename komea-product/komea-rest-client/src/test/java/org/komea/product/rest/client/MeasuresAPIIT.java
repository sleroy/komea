
package org.komea.product.rest.client;


import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.MeasureResultDto;
import org.komea.product.service.dto.errors.InternalServerException;

import com.google.common.collect.Lists;

public class MeasuresAPIIT extends AbstractRestClientIntegrationTestCase {
    
    @Test
    // (expected = InternalServerException.class)
    public void testLastMeasure() throws Exception {
    
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("lines", EntityType.PERSON, 1);
        List<MeasureResultDto> measures = measuresAPI.lastMeasuresForKpiKeys(Lists.newArrayList(kpiKey));
        Assert.assertEquals(12D, measures.get(0).getMeasure().getValue().doubleValue(), 0.1);
        // Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        // Assert.assertFalse(projects.isEmpty());
    }
    @Test(expected = InternalServerException.class)
    public void testLastListMeasure() throws Exception {
    
        IMeasuresAPI measuresAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(measuresAPI);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("lines", EntityType.PERSON, 1);
        double val = measuresAPI.lastMeasuresForKpiKey(kpiKey);
        System.out.println(val);
        // Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        // Assert.assertFalse(projects.isEmpty());
    }
    
    @Ignore
    @Test(expected = InternalServerException.class)
    public void testHistoricalMeasuresByDate() throws Exception {
    
        IMeasuresAPI projectsAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("lines", EntityType.PERSON, 1);
        // double val = projectsAPI;
        // System.out.println(val);
        // Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        // Assert.assertFalse(projects.isEmpty());
    }
}
