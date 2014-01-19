
package org.komea.product.web.admin.views;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.product.backend.service.ISettingService;
import org.komea.product.database.model.Setting;
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
 * This controller offers the possibility to access to some kind of ressources stored in the classpath (pictures).
 * 
 * @author sleroy
 */
@Controller
public class SettingsView
{
    
    
    private static class SettingsDTO
    {
        
        
        private Map<Integer, String> properties = new HashMap<Integer, String>();
        
        
        
        public SettingsDTO() {
        
        
            super();
        }
        
        
        public Map<Integer, String> getProperties() {
        
        
            return properties;
        }
        
        
        public void setProperties(final Map<Integer, String> _properties) {
        
        
            properties = _properties;
        }
    }
    
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsView.class);
    
    
    @Autowired
    private ISettingService     settings;
    
    
    
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
    @RequestMapping(value = "/settings/save", method = RequestMethod.POST)
    @Transactional
    public ModelAndView postSettings(@ModelAttribute("settings")
    final SettingsDTO _dto) throws IOException {
    
    
        final List<String> errors = new ArrayList<String>();
        for (final Entry<Integer, String> entry : _dto.getProperties().entrySet()) {
            LOGGER.info("Entry {} {}", entry.getKey(), entry.getValue());
            final Setting selectByPrimaryKey =
                    settings.getSettingDAO().selectByPrimaryKey(entry.getKey());
            if (!settings.updateValue(selectByPrimaryKey, entry.getValue())) {
                errors.add("Could not save the property "
                        + selectByPrimaryKey.getSettingKey() + "  : validation has failed");
            }
            
        }
        
        
        final ModelAndView requestSettings = requestSettings();
        requestSettings.addObject("saved", "saved");
        requestSettings.addObject("errors", errors);
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
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView requestSettings() throws IOException {
    
    
        final ModelAndView modelAndView = new ModelAndView("settings");
        modelAndView.addObject("settingslist", settings.getSettings());
        modelAndView.addObject("settings", new SettingsDTO());
        return modelAndView;
    }
}
