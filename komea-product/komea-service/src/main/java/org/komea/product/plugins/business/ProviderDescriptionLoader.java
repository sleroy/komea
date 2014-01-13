
package org.komea.product.plugins.business;



import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.Provider;
import org.komea.product.plugin.api.ProviderPlugin;
import org.komea.product.plugins.exceptions.InvalidProviderDescriptionException;
import org.springframework.core.annotation.AnnotationUtils;



/**
 * Loads a provider description.
 * 
 * @author sleroy
 */
public class ProviderDescriptionLoader
{
    
    
    private final Object providerBean;
    
    
    
    public ProviderDescriptionLoader(final Object _providerBean) {
    
    
        super();
        providerBean = _providerBean;
    }
    
    
    /**
     * Loads the description from the bean.
     * 
     * @return
     */
    public ProviderDto load() {
    
    
        if (!AnnotationUtils.isAnnotationDeclaredLocally(ProviderPlugin.class,
                providerBean.getClass())) { throw new InvalidProviderDescriptionException(
                "@Provider annotation should be declared locally."); }
        final ProviderPlugin providerAnnotation =
                AnnotationUtils.findAnnotation(providerBean.getClass(), ProviderPlugin.class);
        final ProviderDto providerdto = new ProviderDto();
        final Provider provider = new Provider();
        providerdto.setProvider(provider);
        new ProviderPluginDescriptionLoader(providerAnnotation, provider).load();
        new ProviderPropertyDescriptionLoader(providerAnnotation, providerdto).load();
        new ProviderEventTypesDescriptionLoader(providerAnnotation, providerdto).load();
        return providerdto;
    }
    
    
}
