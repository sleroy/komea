
package org.komea.product.rest.client;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.rest.client.api.IDepartmentsAPI;

public class DepartmentsAPIIT extends AbstractRestClientIntegrationTestCase {
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void test() throws Exception {
    
        IDepartmentsAPI projectsAPI = RestClientFactory.INSTANCE.createDeparmtentsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        List<DepartmentDto> projects = projectsAPI.allDepartments();
        Assert.assertTrue(projects.get(0) instanceof DepartmentDto);
        Assert.assertFalse(projects.isEmpty());
    }
}
