/**
 * 
 */

package org.komea.product.backend.service.settings;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.service.ISettingListener;
import org.komea.product.database.model.Setting;



/**
 * @author sleroy
 */
public class ListenerOnSettingContainer
{
    
    
    private final String                 propertyname;
    private final List<ISettingListener> settingListeners = new ArrayList<ISettingListener>();
    
    
    
    /**
     * Setting listener registry.
     */
    public ListenerOnSettingContainer(final String _propertyname) {
    
    
        super();
        propertyname = _propertyname;
    }
    
    
    /**
     * Notify value changed.
     * 
     * @param _oldValue
     * @param _newValue
     */
    public void notify(final Setting _setting) {
    
    
        for (final ISettingListener listener : settingListeners) {
            listener.notifyPropertyChanged(_setting);
        }
    }
    
    
    /**
     * Register listener.
     */
    public void registerListener(final ISettingListener _listener) {
    
    
        settingListeners.add(_listener);
        
    }
}
