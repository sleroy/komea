
package org.kormea.product.rest.api.controllers;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.model.PersonGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/teams")
public class TeamsController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamsController.class);
    
    /**
     * This method return the team list
     * 
     * @return the team list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<PersonGroup> allTeams() {
    
        LOGGER.debug("call rest method /teams/all/");
        // TODO
        return new ArrayList<PersonGroup>();
    }
    
}
