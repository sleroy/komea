/**
 * 
 */

package org.komea.product.plugins.rss.bean;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.ISettingService;



/**
 * @author sleroy
 *
 */
public interface IRssExampleFeedBean
{
    
    
    public abstract ISettingService getSettingsService();
    
    
    public abstract void initRssFeed();
    
    
    public abstract void setSettingsService(ISettingService _settingsService);
    
}
