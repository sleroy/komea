
package org.komea.product.web.rest.api;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.model.PersonGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/teams")
public class TeamsController
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamsController.class);
    
    @Autowired
    private PersonGroupDao      groupDAO;
    
    
    
    /**
     * This method return the team list
     * 
     * @return the team list
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<PersonGroup> allTeams() {
    
    
        LOGGER.debug("call rest method /teams/all/");
        // TODO
        return new ArrayList<PersonGroup>();
    }
    
    
    public PersonGroupDao getGroupDAO() {
    
    
        return groupDAO;
    }
    
    
    public void setGroupDAO(final PersonGroupDao _groupDAO) {
    
    
        groupDAO = _groupDAO;
    }
    
}
