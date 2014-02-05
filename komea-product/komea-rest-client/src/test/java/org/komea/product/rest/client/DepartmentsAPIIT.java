
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.rest.client.api.IDepartmentsAPI;

public class DepartmentsAPIIT extends AbstractRestClientIntegrationTestCase
{
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void test() throws ConnectException, URISyntaxException {
    
        IDepartmentsAPI projectsAPI = RestClientFactory.INSTANCE.createDeparmtentsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        List<PersonGroup> projects = projectsAPI.allDepartments();
        Assert.assertTrue(projects.get(0) instanceof PersonGroup);
        Assert.assertFalse(projects.isEmpty());
    }
}
