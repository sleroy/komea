
package org.komea.product.rest.client;


import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.errors.InternalServerException;

public class MeasuresAPIIT extends AbstractRestClientIntegrationTestCase
{
    
    @Test(expected = InternalServerException.class)
    public void test() throws Exception {
    
        IMeasuresAPI projectsAPI = RestClientFactory.INSTANCE.createMeasuresAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("lines", EntityType.PERSON, 1);
        double val = projectsAPI.lastMeasuresForEntity(kpiKey);
        System.out.println(val);
        // Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        // Assert.assertFalse(projects.isEmpty());
    }
}
