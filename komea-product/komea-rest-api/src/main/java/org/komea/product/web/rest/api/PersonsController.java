
package org.komea.product.web.rest.api;



import java.util.List;

import org.komea.product.database.dao.PersonMapper;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/persons")
public class PersonsController
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonsController.class);
    
    @Autowired
    private PersonMapper        personDAO;
    
    
    
    /**
     * This method return the person list
     * 
     * @return the person list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<Person> allPersons() {
    
    
        LOGGER.debug("call rest method /persons/all/");
        return personDAO.selectByExample(new PersonCriteria());
    }
    
}
