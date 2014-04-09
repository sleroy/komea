
package org.komea.product.plugins.git.bean;



import javax.annotation.PostConstruct;

import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.plugins.scm.api.IScmRepositoryProxyFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * Main class to define the GIT Provider Plugin.
 * 
 * @author sleroy
 */
@ProviderPlugin(
        eventTypes =
            { @EventTypeDef(
                    providerType = ProviderType.SCM,
                    description = "A new commit has been pushed on a GIT Server",
                    enabled = true,
                    entityType = EntityType.PROJECT,
                    key = "scm-new-commit",
                    name = "New commit on git server",
                    severity = Severity.INFO), @EventTypeDef(
                    providerType = ProviderType.SCM,
                    description = "Fetch on git server has failed",
                    enabled = true,
                    entityType = EntityType.PROJECT,
                    key = "scm-fetch-failed",
                    name = "Fetch on git server has failed.",
                    severity = Severity.INFO), @EventTypeDef(
                    providerType = ProviderType.SCM,
                    description = "Number of tags in a git branch. The plugin will try to detect how many tags are present on the git branch.",
                    enabled = true,
                    entityType = EntityType.PROJECT,
                    key = "scm-tag-perbranch-numbers",
                    name = "Number of tags per branch.",
                    severity = Severity.INFO), @EventTypeDef(
                    providerType = ProviderType.SCM,
                    description = "Event sent when a git repository is fetched.",
                    enabled = true,
                    entityType = EntityType.PROJECT,
                    key = "scm-fetch-repository",
                    name = "Number of tags per branch.",
                    severity = Severity.INFO), @EventTypeDef(
                    providerType = ProviderType.SCM,
                    description = "Number of customer tags . This plugin will try to detect custom tags present on a git repository.",
                    enabled = true,
                    entityType = EntityType.PROJECT,
                    key = "scm-customer-tag-numbers",
                    name = "Number of customer tags.",
                    severity = Severity.INFO), @EventTypeDef(
                    providerType = ProviderType.SCM,
                    description = "Number of customer branches . This plugin will try to detect the number of customer branches present on a git repository.",
                    enabled = true,
                    entityType = EntityType.PROJECT,
                    key = "scm-customer-branch-numbers",
                    name = "Number of customer branches.",
                    severity = Severity.INFO), @EventTypeDef(
                    providerType = ProviderType.SCM,
                    description = "Number of branches . This plugin will try to detect the number of branches present on a git repository.",
                    enabled = true,
                    entityType = EntityType.PROJECT,
                    key = "scm-branch-numbers",
                    name = "Number of branches.",
                    severity = Severity.INFO) },
        icon = "git",
        name = GitProviderPlugin.GIT_PROVIDER_PLUGIN,
        type = ProviderType.NEWS,
        url = "/git-provider")
public class GitProviderPlugin
{
    
    
    /**
     * GIT Provider plugin name;
     */
    public static final String           GIT_PROVIDER_PLUGIN = "GIT Provider plugin";
    
    public static final String           GIT_TYPE            = "GIT";
    
    private static final Logger          LOGGER              = LoggerFactory
                                                                     .getLogger("git-provider");
    
    
    @Autowired
    private ICronRegistryService         cronRegistryService;
    
    @Autowired
    private IEventPushService            esperEngine;
    
    
    @Autowired
    private IPersonService               personService;
    
    @Autowired
    private IScmRepositoryProxyFactories proxyFactories;
    
    @Autowired
    private ISettingService              registry;
    
    
    
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
    
    
        proxyFactories.registerFactory(GIT_TYPE, new GitRepositoryProxyFactory());
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
