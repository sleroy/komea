/**
 * 
 */

package org.komea.product.backend.service.settings;



import java.util.HashMap;
import java.util.Map;

import org.komea.product.backend.service.ISettingListener;
import org.komea.product.database.model.Setting;



/**
 * @author sleroy
 */
public class SettingListenerGroupContainer
{
    
    
    private final Map<String, ListenerOnSettingContainer> listeners =
                                                                            new HashMap<String, ListenerOnSettingContainer>();
    
    
    
    /**
     * 
     */
    public SettingListenerGroupContainer() {
    
    
        super();
    }
    
    
    /**
     * Notify update
     * 
     * @param _object
     *            the object
     */
    public void notifyUpdate(final Setting _setting) {
    
    
        final ListenerOnSettingContainer listenerOnSettingContainer =
                listeners.get(_setting.getSettingKey());
        if (listenerOnSettingContainer != null) {
            synchronized (listenerOnSettingContainer) {
                listenerOnSettingContainer.notify(_setting);
            }
            
        }
    }
    
    
    public void register(final String _propertyName, final ISettingListener _listener) {
    
    
        ListenerOnSettingContainer listenerOnSettingContainer = listeners.get(_propertyName);
        if (listenerOnSettingContainer == null) {
            listenerOnSettingContainer = new ListenerOnSettingContainer(_propertyName);
            listeners.put(_propertyName, listenerOnSettingContainer);
        }
        listenerOnSettingContainer.registerListener(_listener);
    }
}
