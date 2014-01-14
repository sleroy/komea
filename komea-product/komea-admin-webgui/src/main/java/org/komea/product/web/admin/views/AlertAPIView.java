
package org.komea.product.web.admin.views;



import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * This controller offers the possibility to access to some kind of ressources stored in the classpath (pictures).
 * 
 * @author sleroy
 */
@Controller
@RequestMapping(value = "/admin")
public class AlertAPIView
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertAPIView.class);
    
    
    
    public AlertAPIView() {
    
    
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
    @RequestMapping(value = "/alert_api", method = RequestMethod.GET)
    public String requestPicture() throws IOException {
    
    
        return "alert_api";
    }
    
}
