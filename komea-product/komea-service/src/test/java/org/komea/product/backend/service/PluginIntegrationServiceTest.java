
package org.komea.product.backend.service;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.plugin.api.InjectSetting;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.backend.service.plugins.IProviderDTOConvertorService;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EventCategory;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;



/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PluginIntegrationServiceTest
{
    
    
    /**
     */
    private class BeanWithInjection
    {
        
        
        private ISettingProxy<String> storage_path;
        
        
        
        /**
         * Method getStorage_path.
         * 
         * @return ISettingProxy<String>
         */
        @InjectSetting
        public ISettingProxy<String> getStorage_path() {
        
        
            return storage_path;
        }
        
    }
    
    
    
    @Mock
    private ApplicationContext           appContextMock;
    
    @Mock
    private IEventTypeService            eventTypeService;
    
    @Mock
    private IProviderDTOConvertorService providerAPIService;
    
    @Mock
    private ProviderDao                  providerMapperMock;
    @Mock
    private ISettingService              settingService;
    
    
    
    /**
     * This test validates the injection of setting in a bean.
     */
    @Test
    @Ignore("Regressions on component")
    public void testInjectionSettings() {
    
    
        // final SettingService initPluginIntegrationService = initPluginIntegrationService();
        // final BeanWithInjection beanWithInjection = new BeanWithInjection();
        // final ISettingProxy mock = Mockito.mock(ISettingProxy.class);
        //
        // Mockito.when(settingService.getProxy(Matchers.anyString())).thenReturn(mock);
        //
        // // Add missing mocking
        // initPluginIntegrationService.injectSettings(beanWithInjection);
        // Assert.assertEquals(beanWithInjection.getStorage_path(), mock);
        
    }
    
    
    /**
     * Test : registering provider action
     */
    @Test
    public void testRegisterProvider() {
    
    
        final PluginIntegrationService pluginService = initPluginIntegrationService();
        
        final Provider provider = new Provider();
        provider.setIcon("icon.gif");
        provider.setName("name");
        provider.setProviderType(ProviderType.BUGTRACKER);
        provider.setUrl("url://");
        
        final ArrayList<EventType> eventTypes = new ArrayList<EventType>();
        final EventType eventType = new EventType();
        eventType.setCategory(EventCategory.BUILD.name());
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
        properties.add(new PropertyDTO("testProp", "value", "java.lang.String", "test prop"));
        providerDTO.setProperties(properties);
        // / Update ID from provider pojo
        Mockito.when(providerMapperMock.insert(provider)).then(new Answer()
        {
            
            
            @Override
            public Object answer(final InvocationOnMock _invocation) throws Throwable {
            
            
                provider.setId(1);
                return null;
            }
        });
        
        // Validation of the DTO
        final Set<ConstraintViolation<ProviderDto>> constraintViolationException =
                Validation.buildDefaultValidatorFactory().getValidator().validate(providerDTO);
        if (!constraintViolationException.isEmpty()) { throw new ConstraintViolationException(
                new HashSet<ConstraintViolation<?>>(constraintViolationException)); }
        pluginService.registerProvider(providerDTO);
        
        Mockito.verify(eventTypeService, Mockito.times(1)).registerEvent(
                Matchers.any(Provider.class), Matchers.any(EventType.class));
        
    }
    
    
    /**
     * Test : Validation of a invalid provider.
     */
    @Test
    public void testRegisterWrongProvider() {
    
    
        initPluginIntegrationService();
        final Provider provider = new Provider();
        
        provider.setName(null);
        
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
    
    
    /**
     * Method removeProvider.
     */
    @Test
    public void testRemoveProvider() {
    
    
        final PluginIntegrationService pluginService = initPluginIntegrationService();
        final Provider provider = new Provider();
        provider.setId(1);
        provider.setUrl("url://mockProvider");
        provider.setName("Mock provider");
        provider.setProviderType(ProviderType.NEWS);
        provider.setIcon("icon");
        Mockito.when(
                pluginService.getProviderMapper().selectByCriteria(
                        Matchers.any(ProviderCriteria.class))).thenReturn(
                Collections.singletonList(provider));
        pluginService.removeProvider(provider);
        Mockito.verify(pluginService.getProviderMapper(), Mockito.times(1)).deleteByCriteria(
                Matchers.any(ProviderCriteria.class));
        final ArgumentCaptor<EventTypeCriteria> eventTypeCriteriaCaptor =
                ArgumentCaptor.forClass(EventTypeCriteria.class);
        Mockito.verify(pluginService.getEventTypeService(), Mockito.times(1)).deleteByCriteria(
                eventTypeCriteriaCaptor.capture());
        eventTypeCriteriaCaptor.getValue();
        
    }
    
    
    /**
     * Method initPluginIntegrationService.
     * 
     * @return PluginIntegrationService
     */
    private PluginIntegrationService initPluginIntegrationService() {
    
    
        final PluginIntegrationService pluginService = new PluginIntegrationService();
        pluginService.setProviderMapper(providerMapperMock);
        pluginService.setProviderAPIService(providerAPIService);
        pluginService.setEventTypeService(eventTypeService);
        pluginService.setSettingsService(settingService);
        
        return pluginService;
    }
}
