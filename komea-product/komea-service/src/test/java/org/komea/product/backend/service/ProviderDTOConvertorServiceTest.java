
package org.komea.product.backend.service;



import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.springframework.core.annotation.AnnotationUtils;



/**
 */
public class ProviderDTOConvertorServiceTest
{
    
    
    /**
     */
    @ProviderPlugin(
            type = ProviderType.OTHER,
            name = "Sample provider plugin",
            icon = "/local_resources/pics/truc",
            url = "http://",
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
        
        
        
        public SampleProviderBean() {
        
        
            super();
            
        }
        
        
        /**
         * Method getBUILD_FAILURE.
         * @return org.komea.product.database.model.EventType
         */
        public org.komea.product.database.model.EventType getBUILD_FAILURE() {
        
        
            return BUILD_FAILURE;
        }
        
        
        /**
         * Method setBUILD_FAILURE.
         * @param _bUILD_FAILURE org.komea.product.database.model.EventType
         */
        public void setBUILD_FAILURE(final org.komea.product.database.model.EventType _bUILD_FAILURE) {
        
        
            BUILD_FAILURE = _bUILD_FAILURE;
        }
        
    }
    
    
    
    /**
     */
    public class SampleProviderConfig implements Serializable
    {
        
        
        private int iteration;
        
        
    }
    
    
    
    /**
     * Tests to check all informations are collected from a provider bean.
     */
    @Test
    public final void testLoad() {
    
    
        final IProviderDTOConvertorService providerAPIService = new ProviderDTOConvertorService();
        final ProviderDto providerDTO =
                providerAPIService.loadProviderDTO(AnnotationUtils.findAnnotation(
                        SampleProviderBean.class, ProviderPlugin.class));
        Assert.assertEquals(providerDTO.getProvider().getName(), "Sample provider plugin");
        Assert.assertNotNull(providerDTO.getProvider().getIcon(), "/local_resources/pics/truc");
        Assert.assertEquals(providerDTO.getProvider().getProviderType(), ProviderType.OTHER);
        
        
    }
    
    
}
