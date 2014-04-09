
package org.komea.product.web.rest.api;



import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * This controller offers the possibility to access to some kind of ressources stored in the classpath (pictures).
 * 
 * @author sleroy
 */
@Controller
@RequestMapping(value = "/local_resources")
public class LocalResourceTransferController
{
    
    
    private static final Logger LOGGER = LoggerFactory
                                               .getLogger(LocalResourceTransferController.class);
    
    
    
    public LocalResourceTransferController() {
    
    
        super();
    }
    
    
    /**
     * Returns the ressource given a name / relative path.
     * 
     * @param _response
     *            the http response
     * @param name
     *            the relative path / name of the resource
     * @return the resource data
     * @throws IOException
     */
    @RequestMapping(value = "/pic/{name}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] requestPicture(final HttpServletResponse _response, @PathVariable
    final String name) throws IOException {
    
    
        LOGGER.debug("Requesting resource {}", name);
        final Enumeration<URL> resourceAsStream =
                Thread.currentThread().getContextClassLoader()
                        .getResources("pics/" + name + ".png");
        if (!resourceAsStream.hasMoreElements()) { return new byte[0]; }
        
        final InputStream resourceStream =
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("pics/" + name + ".png");
        return IOUtils.toByteArray(resourceStream);
    }
    
    
}
