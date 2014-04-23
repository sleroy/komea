/**
 * 
 */

package org.komea.product.backend.service.plugins;



import java.lang.annotation.Annotation;

import org.junit.Test;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class ProviderDTOConvertorServiceTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.plugins.ProviderDTOConvertorService#loadEvents(org.komea.product.backend.plugin.api.ProviderPlugin)}
     * .
     */
    @Test
    public final void testLoadEvents() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.plugins.ProviderDTOConvertorService#loadProviderDescription(org.komea.product.backend.plugin.api.ProviderPlugin)}
     * .
     */
    @Test
    public final void testLoadProviderDescription() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.plugins.ProviderDTOConvertorService#loadProviderDTO(org.komea.product.backend.plugin.api.ProviderPlugin)}
     * .
     */
    @Test
    public final void testLoadProviderDTO() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.plugins.ProviderDTOConvertorService#newEventType(org.komea.product.backend.plugin.api.EventTypeDef)}
     * .
     */
    @Test
    public final void testNewEventType() throws Exception {
    
    
        final ProviderDTOConvertorService providerDTOConvertorService =
                new ProviderDTOConvertorService();
        final EventTypeDef eventTypeDef = new EventTypeDef()
        {
            
            
            @Override
            public Class<? extends Annotation> annotationType() {
            
            
                // TODO Auto-generated method stub
                return null;
            }
            
            
            @Override
            public String description() {
            
            
                return "description";
            }
            
            
            @Override
            public boolean enabled() {
            
            
                return true;
            }
            
            
            @Override
            public EntityType entityType() {
            
            
                return EntityType.DEPARTMENT;
            }
            
            
            @Override
            public String key() {
            
            
                return "key";
            }
            
            
            @Override
            public String name() {
            
            
                return "name";
            }
            
            
            @Override
            public ProviderType providerType() {
            
            
                return ProviderType.BUGTRACKER;
            }
            
            
            @Override
            public Severity severity() {
            
            
                return Severity.BLOCKER;
            }
        };
        final EventType newEventType = providerDTOConvertorService.newEventType(eventTypeDef);
        assertEquals(ProviderType.BUGTRACKER, newEventType.getProviderType());
        assertEquals("description", newEventType.getDescription());
        assertEquals(true, newEventType.getEnabled());
        assertEquals(EntityType.DEPARTMENT, newEventType.getEntityType());
        assertEquals("key", newEventType.getKey());
        assertEquals("name", newEventType.getName());
        assertEquals(Severity.BLOCKER, newEventType.getSeverity());
    }
    
}
