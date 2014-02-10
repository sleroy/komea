
package org.komea.product.web.rest.api;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.model.KpiAlertType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/alerttypes")
public class AlertTypeController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertTypeController.class);
    
    /**
     * This method return the alert type list
     * 
     * @return the alert type list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<KpiAlertType> allAlertIypes() {
    
        LOGGER.debug("call rest method /alerttypes/all/");
        // TODO
        return new ArrayList<KpiAlertType>();
    }
    
}
