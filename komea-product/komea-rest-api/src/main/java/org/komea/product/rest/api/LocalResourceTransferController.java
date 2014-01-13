
package org.komea.product.rest.api;



import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/local_resources")
public class LocalResourceTransferController
{
    
    
    public LocalResourceTransferController() {
    
    
        super();
    }
    
    
    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    @ResponseBody
    byte[] requestResource(final HttpServletResponse _response, final String name)
            throws IOException {
    
    
        final InputStream contextClassLoader =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        return IOUtils.toByteArray(contextClassLoader);
    }
    
    
}
