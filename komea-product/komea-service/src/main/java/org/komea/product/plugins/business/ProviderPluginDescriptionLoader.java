
package org.komea.product.plugins.business;



import org.komea.product.database.model.Provider;
import org.komea.product.plugin.api.ProviderPlugin;



public class ProviderPluginDescriptionLoader
{
    
    
    private final Provider       provider;
    private final ProviderPlugin providerAnnotation;
    
    
    
    public ProviderPluginDescriptionLoader(
            final ProviderPlugin _providerAnnotation,
            final Provider _provider) {
    
    
        providerAnnotation = _providerAnnotation;
        provider = _provider;
        
        
    }
    
    
    public void load() {
    
    
        provider.setIcon(providerAnnotation.icon().uri());
        provider.setName(providerAnnotation.name());
        provider.setKey(providerAnnotation.key());
        provider.setUrl(providerAnnotation.url());
        
    }
    
}
