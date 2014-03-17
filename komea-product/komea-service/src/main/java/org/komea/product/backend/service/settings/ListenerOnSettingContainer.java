/**
 * 
 */

package org.komea.product.backend.service.settings;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.service.ISettingListener;
import org.komea.product.database.model.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;



/**
 * @author sleroy
 */
public class ListenerOnSettingContainer
{
    
    
    private static final Logger          LOGGER           =
                                                                  LoggerFactory
                                                                          .getLogger(ListenerOnSettingContainer.class);
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
            try {
                listener.notifyPropertyChanged(_setting);
            } catch (final Throwable eThrowable) {
                LOGGER.error("Error inside a setting listener for " + propertyname, eThrowable);
                throw new FatalBeanException("Error inside a setting listener", eThrowable);
            }
        }
    }
    
    
    /**
     * Register listener.
     */
    public void registerListener(final ISettingListener _listener) {
    
    
        settingListeners.add(_listener);
        
    }
}
