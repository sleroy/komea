
package org.komea.product.plugins.business;



import java.util.ArrayList;

import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.plugin.api.Property;
import org.komea.product.plugin.api.ProviderPlugin;



public class ProviderPropertyDescriptionLoader
{
    
    
    private final ProviderPlugin providerAnnotation;
    private final ProviderDto    providerDto;
    
    
    
    public ProviderPropertyDescriptionLoader(
            final ProviderPlugin _providerAnnotation,
            final ProviderDto _providerdto) {
    
    
        providerAnnotation = _providerAnnotation;
        providerDto = _providerdto;
        
        
    }
    
    
    public void load() {
    
    
        final Property[] properties = providerAnnotation.properties();
        providerDto.setProperties(new ArrayList<PropertyDTO>());
        if (properties != null) {
            for (final Property property : properties) {
                providerDto.getProperties()
                        .add(new PropertyDTO(property.key(), property.value(), property.type()
                                .getName()));
            }
        }
        
    }
    
}
