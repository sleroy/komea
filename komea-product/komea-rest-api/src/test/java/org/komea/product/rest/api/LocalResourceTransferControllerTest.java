
package org.komea.product.rest.api;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;



public class LocalResourceTransferControllerTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private LocalResourceTransferController controller;
    
    
    
    @Test
    public void testRequestPicture() throws Exception {
    
    
        final MockMvc mockMvc = standaloneSetup(controller).build();
        final MockHttpServletRequestBuilder downloadResource = get("/local_resources/pic/example");
        final ResultActions perform = mockMvc.perform(downloadResource);
        System.out.println(perform);
        final MockHttpServletResponse response = perform.andReturn().getResponse();
        System.out.println(response);
        
        final FileInputStream fileInputStream =
                new FileInputStream("src/test/resources/example.png");
        IOUtils.contentEquals(fileInputStream,
                new ByteArrayInputStream(response.getContentAsByteArray()));
        IOUtils.closeQuietly(fileInputStream);
        ;
        
        
    }
    
}
