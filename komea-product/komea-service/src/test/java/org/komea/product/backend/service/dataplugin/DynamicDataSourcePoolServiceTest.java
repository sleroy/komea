/**
 *
 */

package org.komea.product.backend.service.dataplugin;



import org.junit.Test;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class DynamicDataSourcePoolServiceTest extends AbstractSpringIntegrationTestCase
{


    @Autowired
    AA[] services;



    @Test
    public void test() {
    
    
        System.out.println(services[0].getClass());
        System.out.println(services[1].getClass());
        System.out.println(services[2].getClass());
        assertEquals(3, services.length);
    }
}



@Component
class A implements AA
{
    
}



@Component
class A2 implements AA
{
    
}



@Component
class A3 implements AA
{
    
}



interface AA
{
    
}
