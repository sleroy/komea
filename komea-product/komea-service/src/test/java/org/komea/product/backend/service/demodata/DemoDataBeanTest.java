
package org.komea.product.backend.service.demodata;



import org.junit.Before;
import org.junit.Test;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



public class DemoDataBeanTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private DemoDataBean bean;
    
    
    
    @Before
    public void setUp() throws Exception {
    
    
    }
    
    
    @Test
    public final void testDemoDataBean() {
    
    
        bean.sendAlert();
        
    }
    
    
}
