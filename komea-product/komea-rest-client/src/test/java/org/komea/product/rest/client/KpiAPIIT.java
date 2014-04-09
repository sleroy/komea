
package org.komea.product.rest.client;



import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.database.model.Kpi;
import org.komea.product.rest.client.api.IKpisAPI;



public class KpiAPIIT
{
    
    
    @Rule
    public ServerMethodRule serverInit = new ServerMethodRule();
    
    
    
    //
    
    @Test
    public void testGetAllKpis() throws Exception {
    
    
        final IKpisAPI kpisAPI = RestClientFactory.INSTANCE.createKpisAPI(serverInit.getAddress());
        Assert.assertNotNull(kpisAPI);
        final List<Kpi> projects = kpisAPI.allKpis();
        Assert.assertFalse(projects.isEmpty());
    }
    
}
