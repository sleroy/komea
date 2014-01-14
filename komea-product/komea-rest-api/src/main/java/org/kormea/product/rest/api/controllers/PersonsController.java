
package org.kormea.product.rest.api.controllers;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/persons")
public class PersonsController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonsController.class);
    
    /**
     * This method return the person list
     * 
     * @return the person list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<Person> allPersons() {
    
        LOGGER.debug("call rest method /persons/all/");
        // TODO
        return new ArrayList<Person>();
    }
    
}
