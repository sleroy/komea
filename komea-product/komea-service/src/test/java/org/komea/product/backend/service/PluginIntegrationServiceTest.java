
package org.komea.product.backend.service;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.IEventTypeService;
import org.komea.product.backend.service.IProviderAPIService;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.database.dao.ProviderMapper;
import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.Category;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;



@RunWith(MockitoJUnitRunner.class)
public class PluginIntegrationServiceTest
{
    
    
    @Mock
    private IProviderAPIService providerAPIService;
    
    @Mock
    private ApplicationContext  appContextMock;
    
    @Mock
    private ProviderMapper      providerMapperMock;
    
    @Mock
    private IEventTypeService   eventTypeService;
    
    @Mock
    private ISettingService     settingService;
    
    
    
    /**
     * Test : registering provider action
     */
    @Test
    public void testRegisterProvider() {
    
    
        final PluginIntegrationService pluginService = new PluginIntegrationService();
        pluginService.setContext(appContextMock);
        pluginService.setProviderMapper(providerMapperMock);
        pluginService.setProviderAPIService(providerAPIService);
        pluginService.setEventTypeService(eventTypeService);
        pluginService.setSettingsService(settingService);
        final Provider provider = new Provider();
        provider.setIcon("icon.gif");
        provider.setName("name");
        provider.setProviderKey("key");
        provider.setUrl("url://");
        
        
        final ArrayList<EventType> eventTypes = new ArrayList<EventType>();
        final EventType eventType = new EventType();
        eventType.setCategory(Category.BUILD.name());
        eventType.setDescription("eventDesc");
        eventType.setEnabled(true);
        eventType.setEntityType(EntityType.PERSON);
        eventType.setEventKey("EVENT_KEY");
        eventType.setIdProvider(0);
        eventType.setName("eventName");
        eventType.setSeverity(Severity.MINOR);
        eventTypes.add(eventType);
        // Execution of the registration
        final ProviderDto providerDTO = new ProviderDto(provider, eventTypes);
        final ArrayList<PropertyDTO> properties = new ArrayList<PropertyDTO>();
        properties.add(new PropertyDTO("testProp", "value", "java.lang.String"));
        providerDTO.setProperties(properties);
        
        // Validation of the DTO
        final Set<ConstraintViolation<ProviderDto>> constraintViolationException =
                Validation.buildDefaultValidatorFactory().getValidator().validate(providerDTO);
        if (!constraintViolationException.isEmpty()) {
            System.err.println(constraintViolationException);
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(
                    constraintViolationException));
        }
        pluginService.registerProvider(providerDTO);
        // Capture the argument provided to the DAO.
        final ArgumentCaptor<ProviderCriteria> criteriaCaptor =
                ArgumentCaptor.forClass(ProviderCriteria.class);
        
        Mockito.verify(providerMapperMock, Mockito.times(1)).countByExample(
                criteriaCaptor.capture());
        Mockito.verify(settingService, Mockito.times(1)).getOrCreate(Matchers.anyString(),
                Matchers.anyString(), Matchers.anyString());
        Mockito.verify(eventTypeService, Mockito.times(1)).registerEvent(
                Matchers.any(Provider.class), Matchers.any(EventType.class));
        
        
        /**
         * Validate DAO entries
         */
        
        final List<ProviderCriteria> allValues = criteriaCaptor.getAllValues();
        Assert.assertEquals("Only one value is expected from ProviderMapper.countValues", 1,
                allValues.size());
        // Check the criteria value
        final ProviderCriteria firstCriteria = allValues.get(0);
        Assert.assertEquals("One criteria expected in the DAO", 1, firstCriteria.getOredCriteria()
                .size());
        Assert.assertTrue(firstCriteria.getOredCriteria().get(0).getCriteria().get(0).getValue()
                .equals("key"));
        
        
    }
    
    
    /**
     * Test : Validation of a invalid provider.
     */
    @Test
    public void testRegisterWrongProvider() {
    
    
        final PluginIntegrationService pluginService = new PluginIntegrationService();
        
        pluginService.setContext(appContextMock);
        pluginService.setProviderMapper(providerMapperMock);
        pluginService.setProviderAPIService(providerAPIService);
        final Provider provider = new Provider();
        
        provider.setName(null);
        provider.setProviderKey(null);
        provider.setUrl("url://");
        
        final ArrayList<EventType> eventTypes = new ArrayList<EventType>();
        
        final ProviderDto providerDTO = new ProviderDto(null, eventTypes);
        final Set<ConstraintViolation<ProviderDto>> validate =
                Validation.buildDefaultValidatorFactory().getValidator().validate(providerDTO);
        if (!validate.isEmpty()) {
            System.err.println(validate);
        }
        Assert.assertFalse("Provider DTO should not be valid.", validate.isEmpty());
        final Set<ConstraintViolation<Provider>> validate2 =
                Validation.buildDefaultValidatorFactory().getValidator().validate(provider);
        if (!validate2.isEmpty()) {
            System.err.println(validate2);
        }
        Assert.assertFalse("Provider should not be valid", validate2.isEmpty());
        
        
    }
    
    
}
