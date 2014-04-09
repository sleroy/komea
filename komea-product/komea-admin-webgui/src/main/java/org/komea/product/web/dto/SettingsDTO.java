
package org.komea.product.web.dto;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.model.Setting;



public class SettingsDTO implements Serializable
{
    
    
    private List<Setting> settings = new ArrayList<Setting>();
    
    
    
    public SettingsDTO() {
    
    
        super();
    }
    
    
    public List<Setting> getSettings() {
    
    
        return settings;
    }
    
    
    public void setSettings(final List<Setting> _settings) {
    
    
        settings = _settings;
    }
    
    
}
