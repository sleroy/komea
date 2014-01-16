
package org.komea.product.backend.service;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.exceptions.InvalidProviderDescriptionException;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.IResourceProxy;
import org.komea.product.backend.plugin.api.IUpdateAction;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.plugin.api.ProviderResource;
import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.springframework.beans.factory.annotation.Autowired;



public class ProviderAPIServiceTest
{
    
    
    @ProviderPlugin(
            type = ProviderType.OTHER,
            name = "Sample provider plugin",
            icon = "/local_resources/pics/truc",
            properties = {
                    @Property(key = "cronFrequency", value = "@hourly", type = String.class),
                    @Property(key = "disabled", value = "false", type = Boolean.class) },
            eventTypes = {
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
    
    
    
    public class SampleProviderConfig implements Serializable
    {
        
        
        private int iteration;
        
        
        
        public int getIteration() {
        
        
            return iteration;
        }
        
        
        public void setIteration(final int _iteration) {
        
        
            iteration = _iteration;
        }
        
        
    }
    
    
    
    /**
     * Tests to check all informations are collected from a provider bean.
     */
    @Test
    public final void testLoad() {
    
    
        final IProviderAPIService providerAPIService = new ProviderAPIService();
        final ProviderDto providerDTO =
                providerAPIService.loadProviderDTO(new SampleProviderBean());
        Assert.assertEquals(providerDTO.getProvider().getName(), "Sample provider plugin");
        Assert.assertNotNull(providerDTO.getProvider().getIcon(), "/local_resources/pics/truc");
        Assert.assertEquals(providerDTO.getProvider().getProviderTypeEnum(), ProviderType.OTHER);
        Assert.assertEquals(2, providerDTO.getProperties().size());
        final PropertyDTO propertyDTO1 = providerDTO.getProperties().get(0);
        Assert.assertEquals("cronFrequency", propertyDTO1.getKey());
        Assert.assertEquals("@hourly", propertyDTO1.getValue());
        Assert.assertEquals("java.lang.String", propertyDTO1.getType());
        final PropertyDTO propertyDTO2 = providerDTO.getProperties().get(1);
        Assert.assertEquals("disabled", propertyDTO2.getKey());
        Assert.assertEquals("false", propertyDTO2.getValue());
        Assert.assertEquals("java.lang.Boolean", propertyDTO2.getType());
        Assert.assertEquals(3, providerDTO.getEventTypes().size());
        
    }
    
    
    /**
     * Tests to check failure case.
     */
    @Test(expected = InvalidProviderDescriptionException.class)
    public final void testLoadFail() {
    
    
        final IProviderAPIService providerAPIService = new ProviderAPIService();
        final ProviderDto providerDTO = providerAPIService.loadProviderDTO(new Object());
        
        
    }
}
