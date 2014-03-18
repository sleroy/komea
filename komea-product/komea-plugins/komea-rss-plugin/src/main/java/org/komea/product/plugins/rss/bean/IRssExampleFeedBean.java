/**
 * 
 */

package org.komea.product.plugins.rss.bean;



import org.komea.product.backend.service.ISettingService;



/**
 * @author sleroy
 */
public interface IRssExampleFeedBean
{
    
    
    public abstract void initRssFeed();
    
    
    public abstract void setSettingsService(ISettingService _settingsService);
    
}
