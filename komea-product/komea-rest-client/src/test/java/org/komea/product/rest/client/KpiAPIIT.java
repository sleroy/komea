
package org.komea.product.rest.client;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.model.Kpi;
import org.komea.product.rest.client.api.IKpisAPI;

public class KpiAPIIT extends AbstractRestClientIntegrationTestCase {
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void testGetAllKpis() throws Exception {
    
        IKpisAPI kpisAPI = RestClientFactory.INSTANCE.createKpisAPI("http://localhost:8585/komea");
        Assert.assertNotNull(kpisAPI);
        List<Kpi> projects = kpisAPI.allKpis();
        Assert.assertFalse(projects.isEmpty());
    }
    
}
