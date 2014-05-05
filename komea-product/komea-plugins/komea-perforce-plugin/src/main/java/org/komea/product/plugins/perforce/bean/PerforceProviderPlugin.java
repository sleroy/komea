
package org.komea.product.plugins.perforce.bean;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.scm.api.IScmRepositoryProxyFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Main class to define the Perforce Provider Plugin.
 * 
 * @author sleroy
 */
@Service
public class PerforceProviderPlugin
{
    
    
    /**
     * Perforce Provider plugin name;
     */
    public static final String             PERFORCE_PROVIDER_PLUGIN = "Perforce Provider plugin";
    
    public static final String             PERFORCE_TYPE            = "PERFORCE";
    
    private static final Logger            LOGGER                   =
                                                                            LoggerFactory
                                                                                    .getLogger("perforce-provider");
    
    
    @Autowired
    private ICronRegistryService           cronRegistryService;
    
    @Autowired
    private IEventPushService              esperEngine;
    
    
    @Autowired
    private PerforceRepositoryProxyFactory perforceProxyFactory;
    
    @Autowired
    private IPersonService                 personService;
    
    @Autowired
    private IScmRepositoryProxyFactories   proxyFactories;
    
    @Autowired
    private ISettingService                registry;
    
    
    
    /**
     * @return the cronRegistryService
     */
    public ICronRegistryService getCronRegistryService() {
    
    
        return cronRegistryService;
    }
    
    
    /**
     * @return the esperEngine
     */
    public IEventPushService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    public IScmRepositoryProxyFactories getProxyFactories() {
    
    
        return proxyFactories;
    }
    
    
    /**
     * @return the registry
     */
    public ISettingService getRegistry() {
    
    
        return registry;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        proxyFactories.registerFactory(PERFORCE_TYPE, perforceProxyFactory);
    }
    
    
    /**
     * @param _cronRegistryService
     *            the cronRegistryService to set
     */
    public void setCronRegistryService(final ICronRegistryService _cronRegistryService) {
    
    
        cronRegistryService = _cronRegistryService;
    }
    
    
    /**
     * @param _esperEngine
     *            the esperEngine to set
     */
    public void setEsperEngine(final IEventPushService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
    
    public void setProxyFactories(final IScmRepositoryProxyFactories _proxyFactories) {
    
    
        proxyFactories = _proxyFactories;
    }
    
    
    /**
     * @param _registry
     *            the registry to set
     */
    public void setRegistry(final ISettingService _registry) {
    
    
        registry = _registry;
    }
    
}
