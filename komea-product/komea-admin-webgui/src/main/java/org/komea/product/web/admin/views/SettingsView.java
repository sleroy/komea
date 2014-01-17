
package org.komea.product.web.admin.views;



import java.io.IOException;

import org.komea.product.database.dao.SettingMapper;
import org.komea.product.database.model.SettingCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



/**
 * This controller offers the possibility to access to some kind of ressources stored in the classpath (pictures).
 * 
 * @author sleroy
 */
@Controller
public class SettingsView
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsView.class);
    
    
    @Autowired
    private SettingMapper       settings;
    
    
    
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
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView requestPicture() throws IOException {
    
    
        final ModelAndView modelAndView = new ModelAndView("settings");
        modelAndView.addObject("settings", settings.selectByExample(new SettingCriteria()));
        return modelAndView;
    }
}
