
package org.komea.product.wicket;



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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.database.model.Setting;
import org.komea.product.web.dto.SettingsDTO;
import org.slf4j.LoggerFactory;



/**
 * Settings page
 * 
 * @author sleroy
 */
public class SettingsPage extends LayoutPage
{
    
    
    private final class SettingForm extends Form<SettingsDTO>
    {
        
        
        private transient final List<TextField> textFields = new ArrayList<TextField>();
        
        
        
        SettingForm(final String _id, final IModel<SettingsDTO> _dto) {
        
        
            super(_id, _dto);
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
                    
                    final Setting object = model.getObject();
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
        
        
        @Override
        protected void onSubmit() {
        
        
            // Validation des settings
            boolean success = true;
            try {
                for (final Setting setting : getModel().getObject().getSettings()) {
                    service.update(setting);
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
    
    
    
    @SpringBean
    private ISettingService service;
    
    
    
    public SettingsPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        // method loadPersons is defined elsewhere
        final List<Setting> settings = service.getSettings();
        // Build DTO
        final SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setSettings(settings);
        
        
        add(new SettingForm("form", new CompoundPropertyModel<SettingsDTO>(settingsDTO)));
        
        
    }
}
