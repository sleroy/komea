
package org.komea.product.plugins.sample;



import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.IResourceProxy;
import org.komea.product.backend.plugin.api.IUpdateAction;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.plugin.api.ProviderResource;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.springframework.beans.factory.annotation.Autowired;



@ProviderPlugin(
        type = ProviderType.OTHER,
        name = "Sample provider plugin",
        icon = "/truc.gif",
        properties = {
                @Property(
                        key = "sample_cronFrequency",
                        value = "@hourly",
                        type = String.class,
                        description = "Sets the frequency"),
                @Property(
                        key = "sample_disabled",
                        value = "false",
                        type = Boolean.class,
                        description = "DIsabled") }, eventTypes = {
                @EventTypeDef(
                        category = "BUILD",
                        description = "Event to notify a build is started",
                        key = "BUILD_STARTED",
                        name = "Build started",
                        entityType = EntityType.PROJECT,
                        severity = Severity.INFO),
                @EventTypeDef(
                        category = "BUILD",
                        description = "Event to notify a build is finished with success",
                        key = "BUILD_FINISHED",
                        name = "Build finished",
                        entityType = EntityType.PROJECT,
                        severity = Severity.INFO),
                @EventTypeDef(
                        category = "BUILD",
                        description = "Event to notify a build is finished with failure",
                        key = "BUILD_FAILURE",
                        name = "Build failure",
                        entityType = EntityType.PROJECT,
                        severity = Severity.MAJOR) })
public class SampleProviderBean
{
    
    
    @EventTypeDef()
    private org.komea.product.database.model.EventType BUILD_FAILURE;
    
    
    @ProviderResource
    private IResourceProxy<SampleProviderConfig>       config;
    
    
    
    public SampleProviderBean() {
    
    
        super();
        
    }
    
    
    public org.komea.product.database.model.EventType getBUILD_FAILURE() {
    
    
        return BUILD_FAILURE;
    }
    
    
    @Autowired
    public void init() {
    
    
        final SampleProviderConfig sampleProviderConfig = config.get();
        
        
        config.update(new IUpdateAction<SampleProviderConfig>()
        {
            
            
            @Override
            public void update(final SampleProviderConfig _object) {
            
            
                _object.setIteration(12);
                
            }
            
        });
        
    }
    
    
    public void setBUILD_FAILURE(final org.komea.product.database.model.EventType _bUILD_FAILURE) {
    
    
        BUILD_FAILURE = _bUILD_FAILURE;
    }
    
}
