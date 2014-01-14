
package org.kormea.product.rest.api.controllers;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/kpis")
public class KpisController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KpisController.class);
    
    /**
     * This method return the kpi list
     * 
     * @return the kpi list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<Kpi> allKpis() {
    
        LOGGER.debug("call rest method /kpis/all/");
        // TODO
        return new ArrayList<Kpi>();
    }
    
}
