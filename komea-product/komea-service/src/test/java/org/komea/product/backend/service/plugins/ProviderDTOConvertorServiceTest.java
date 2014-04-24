/**
 * 
 */

package org.komea.product.backend.service.plugins;



import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.Test;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class ProviderDTOConvertorServiceTest
{
    
    
    /**
     * @author sleroy
     */
    private final class EventTypeDefImplementation implements EventTypeDef
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
    }
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.plugins.ProviderDTOConvertorService#loadProviderDescription(org.komea.product.backend.plugin.api.ProviderPlugin)}
     * .
     */
    @Test
    public final void testLoadProviderDescription() throws Exception {
    
    
        final ProviderDTOConvertorService providerDTOConvertorService =
                new ProviderDTOConvertorService();
        final ProviderPlugin providerPlugin = addProviderPlugin();
        final List<EventType> events = providerDTOConvertorService.loadEvents(providerPlugin);
        final EventType eventType = events.get(0);
        assertEquals("description", eventType.getDescription());
        assertEquals(true, eventType.getEnabled());
        assertEquals(EntityType.DEPARTMENT, eventType.getEntityType());
        assertEquals("key", eventType.getKey());
        assertEquals("name", eventType.getName());
        assertEquals(ProviderType.BUGTRACKER, eventType.getProviderType());
        assertEquals(Severity.BLOCKER, eventType.getSeverity());
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.plugins.ProviderDTOConvertorService#loadProviderDTO(org.komea.product.backend.plugin.api.ProviderPlugin)}
     * .
     */
    @Test
    public final void testLoadProviderDTO() throws Exception {
    
    
        final ProviderDTOConvertorService providerDTOConvertorService =
                new ProviderDTOConvertorService();
        final ProviderDto dto = providerDTOConvertorService.loadProviderDTO(addProviderPlugin());
        assertEquals(1, dto.getEventTypes().size());
        assertTrue(dto.getProperties().isEmpty());
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
    
    
    private ProviderPlugin addProviderPlugin() {
    
    
        final ProviderPlugin providerPlugin = new ProviderPlugin()
        {
            
            
            @Override
            public Class<? extends Annotation> annotationType() {
            
            
                return null;
            }
            
            
            @Override
            public EventTypeDef[] eventTypes() {
            
            
                return new EventTypeDef[]
                    { new EventTypeDefImplementation() };
            };
            
            
            @Override
            public String icon() {
            
            
                return "icon";
            }
            
            
            @Override
            public String name() {
            
            
                return "name";
            }
            
            
            @Override
            public ProviderType type() {
            
            
                return ProviderType.BUGTRACKER;
            }
            
            
            @Override
            public String url() {
            
            
                return "http://";
            }
        };
        return providerPlugin;
    }
}
