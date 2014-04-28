
package org.komea.product.wicket.settings;



import java.util.List;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.database.model.Setting;
import org.komea.product.web.dto.SettingsDTO;
import org.komea.product.wicket.LayoutPage;



/**
 * Settings page
 * 
 * @author sleroy
 */
public class SettingsPage extends LayoutPage
{
    
    
    @SpringBean
    private ISettingService service;
    
    
    
    public SettingsPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        // method loadPersons is defined elsewhere
        final List<Setting> settings = service.getSettings();
        // Build DTO
        
        final SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setSettings(settings);
        add(new SettingForm(getService(), "form", new CompoundPropertyModel<SettingsDTO>(
                settingsDTO)));
    }
    
    
    public ISettingService getService() {
    
    
        return service;
    }
    
    
    public void setService(final ISettingService _service) {
    
    
        service = _service;
    }
       @Override
    public String getTitle() {
        return getString("administration.title.settings");
    }
}
