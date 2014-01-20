
package org.komea.product.web.admin.views;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.dao.PersonGroupMapper;
import org.komea.product.database.model.PersonGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



/**
 * This controller provides administration page on user groups.
 * 
 * @author sleroy
 */
@Controller
public class UserGroupView
{
    
    
    /**
     * This class defines the DTO to be manipulated in the form.
     * 
     * @author sleroy
     */
    private static class PersonsGroupFormDTO
    {
        
        
        private final List<PersonGroup> groups = new ArrayList<PersonGroup>();
        
        
        
        public PersonsGroupFormDTO() {
        
        
            super();
        }
        
        
        public List<PersonGroup> getPersonGroups() {
        
        
            return groups;
        }
    }
    
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupView.class);
    
    @Autowired
    private PersonGroupMapper   groupMapper;
    
    
    
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
    @RequestMapping(value = "/usergroups/save", method = RequestMethod.POST)
    @Transactional
    public ModelAndView postPersonGroups(@ModelAttribute("personGroups")
    final PersonsGroupFormDTO _dto) throws IOException {
    
    
        final ModelAndView requestSettings = requestUserGroups();
        requestSettings.addObject("saved", "saved");
        requestSettings.addObject("errors", new ArrayList());
        return requestSettings;
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
    @RequestMapping(value = "/usergroups", method = RequestMethod.GET)
    public ModelAndView requestUserGroups() throws IOException {
    
    
        final ModelAndView modelAndView = new ModelAndView("usergroups");
        final PersonsGroupFormDTO formDTO = new PersonsGroupFormDTO();
        modelAndView.addObject("personGroups", formDTO);
        return modelAndView;
    }
}
