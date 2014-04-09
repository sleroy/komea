
package org.komea.product.wicket.settings;



import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.database.model.Setting;
import org.komea.product.web.dto.SettingsDTO;
import org.slf4j.LoggerFactory;



/**
 * Formular to edit properties in the settings page.
 * 
 * @author sleroy
 */
public final class SettingForm extends Form<SettingsDTO>
{
    
    
    private final ISettingService           settingsService;
    private transient final List<TextField> textFields = new ArrayList<TextField>();
    
    
    
    public SettingForm(
            final ISettingService _settingsService,
            final String _id,
            final IModel<SettingsDTO> _dto) {
    
    
        super(_id, _dto);
        settingsService = _settingsService;
        
        
    }
    
    
    @Override
    protected void onInitialize() {
    
    
        super.onInitialize();
        final WebMarkupContainer webMarkupContainer = new WebMarkupContainer("success");
        webMarkupContainer.setVisible(false);
        add(webMarkupContainer);
        
        final WebMarkupContainer webMarkupContainer2 = new WebMarkupContainer("errors");
        webMarkupContainer2.setVisible(false);
        add(webMarkupContainer2);
        
        final ListView<Setting> listView = new ListView<Setting>("settings")
        {
            
            
            @Override
            protected void populateItem(final ListItem<Setting> _item) {
            
            
                final IModel<Setting> model = _item.getModel();
                _item.setDefaultModel(new CompoundPropertyModel<Setting>(model.getObject()));
                
                model.getObject();
                _item.add(new Label("settingKey"));
                _item.add(new Label("description"));
                
                final TextField<String> textField = new TextField<String>("value");
                textFields.add(textField);
                _item.add(textField);
                _item.setOutputMarkupId(true);
            }
        };
        // listView.setReuseItems(true);
        
        add(listView);
    }
    
    
    /**
     * Validation of the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {
    
    
        // Validation des settings
        boolean success = true;
        try {
            for (final Setting setting : getModel().getObject().getSettings()) {
                settingsService.update(setting);
            }
            success = true;
        } catch (final Exception e) {
            LoggerFactory.getLogger(SettingForm.class).error(e.getMessage(), e);
            success = false;
        }
        if (success) {
            get("success").setVisible(true);
        } else {
            get("errors").setVisible(true);
        }
    }
}
