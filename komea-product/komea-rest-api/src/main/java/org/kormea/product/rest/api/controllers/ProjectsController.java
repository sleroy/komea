
package org.kormea.product.rest.api.controllers;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/projects")
public class ProjectsController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectsController.class);
    
    /**
     * This method return the project list
     * 
     * @return the project list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<Project> allProjects() {
    
        LOGGER.debug("call rest method /projects/all/");
        // TODO
        return new ArrayList<Project>();
    }
    
}
