
package org.komea.product.plugins.scm;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.plugins.scm.cron.ScmScheduleCronJob;
import org.komea.product.plugins.scm.userinterface.ScmPage;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



/**
 * This service defines the component that contains repositories.
 * 
 * @author sleroy
 */
@ProviderPlugin(
        eventTypes = {
                @EventTypeDef(
                        providerType = ProviderType.SCM,
                        description = "A new commit has been pushed",
                        enabled = true,
                        entityType = EntityType.PROJECT,
                        key = "scm-new-commit",
                        name = "New commit on scm server",
                        severity = Severity.INFO),
                @EventTypeDef(
                        providerType = ProviderType.SCM,
                        description = "Fetch on scm server has failed",
                        enabled = true,
                        entityType = EntityType.PROJECT,
                        key = "scm-fetch-failed",
                        name = "Fetch on scm server has failed.",
                        severity = Severity.INFO),
                @EventTypeDef(
                        providerType = ProviderType.SCM,
                        description = "Number of tags in a scm branch. The plugin will try to detect how many tags are present on the scm branch.",
                        enabled = true,
                        entityType = EntityType.PROJECT,
                        key = "scm-tag-perbranch-numbers",
                        name = "Number of tags per branch.",
                        severity = Severity.INFO),
                @EventTypeDef(
                        providerType = ProviderType.SCM,
                        description = "Event sent when a scm repository is fetched.",
                        enabled = true,
                        entityType = EntityType.PROJECT,
                        key = "scm-fetch-repository",
                        name = "Number of tags per branch.",
                        severity = Severity.INFO),
                @EventTypeDef(
                        providerType = ProviderType.SCM,
                        description = "Number of customer tags . This plugin will try to detect custom tags present on a scm repository.",
                        enabled = true,
                        entityType = EntityType.PROJECT,
                        key = "scm-customer-tag-numbers",
                        name = "Number of customer tags.",
                        severity = Severity.INFO),
                @EventTypeDef(
                        providerType = ProviderType.SCM,
                        description = "Number of customer branches . This plugin will try to detect the number of customer branches present on a scm repository.",
                        enabled = true,
                        entityType = EntityType.PROJECT,
                        key = "scm-customer-branch-numbers",
                        name = "Number of customer branches.",
                        severity = Severity.INFO),
                @EventTypeDef(
                        providerType = ProviderType.SCM,
                        description = "Number of branches . This plugin will try to detect the number of branches present on a scm repository.",
                        enabled = true,
                        entityType = EntityType.PROJECT,
                        key = "scm-branch-numbers",
                        name = "Number of branches.",
                        severity = Severity.INFO) },
        icon = "scm",
        name = ScmRepositoryService.NAME,
        type = ProviderType.NEWS,
        url = ScmRepositoryService.SCM_URL)
@PluginAdminPages(@PluginMountPage(pluginName = ScmRepositoryService.NAME, page = ScmPage.class))
@Transactional
public final class ScmRepositoryService implements IScmRepositoryService
{
    
    
    public static final String                         NAME                    = "Scm plugin";
    
    public static final String                         SCM_URL                 = "/scm-provider";
    
    /**
     * Repository cron.
     */
    private static final String                        CRON_DEFAULT_EXPRESSION = "0 0/2 * * * ?";
    
    
    private static final Logger                        LOGGER                  =
                                                                                       LoggerFactory
                                                                                               .getLogger(ScmRepositoryService.class);
    
    
    /**
     * 
     */
    private static final String                        SCM_AUTOUPDATING_CRON   =
                                                                                       "SCM_AUTOUPDATING_CRON";
    
    
    @Autowired
    private ICronRegistryService                       cronRegistryService;
    
    
    private final Map<String, String>                  cronTasks               =
                                                                                       new ConcurrentHashMap<String, String>();
    
    
    private IDAOObjectStorage<ScmRepositoryDefinition> daoStorage;
    
    
    @Autowired
    private IPluginStorageService                      pluginStorageService;
    
    
    
    /**
     * Builds a scm repository service.
     * 
     * @param _scmRepositoryName
     *            the repository name
     * @param _implementationClass
     *            the class of objects stored in the repository.
     */
    public ScmRepositoryService() {
    
    
        super();
        
        
    }
    
    
    @Override
    public boolean existScmKey(final String _key) {
    
    
        return findByName(_key) != null;
    }
    
    
    @Override
    public ScmRepositoryDefinition findByName(final String _feedName) {
    
    
        for (final ScmRepositoryDefinition feed : getDAO().selectAll()) {
            if (_feedName.equals(feed.getKey())) {
                return feed;
            }
        }
        return null;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepositoryService#getAllRepositories()
     */
    @Override
    public List<ScmRepositoryDefinition> getAllRepositories() {
    
    
        return getDAO().selectAll();
    }
    
    
    public ICronRegistryService getCronRegistryService() {
    
    
        return cronRegistryService;
    }
    
    
    /**
     * Returns the dao
     * 
     * @return the dao.
     */
    public IDAOObjectStorage<ScmRepositoryDefinition> getDAO() {
    
    
        return daoStorage;
        
    }
    
    
    public IPluginStorageService getPluginStorageService() {
    
    
        return pluginStorageService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.IScmRepositoryService#getRepositoriesNotAssociated()
     */
    @Override
    public List<ScmRepositoryDefinition> getRepositoriesNotAssociated() {
    
    
        final List<ScmRepositoryDefinition> allRepositories = getAllRepositories();
        
        final List<ScmRepositoryDefinition> gitRepositoryDefinitions =
                new ArrayList<ScmRepositoryDefinition>(allRepositories.size());
        for (final ScmRepositoryDefinition gitRepositoryDefinition : allRepositories) {
            if (!isAssociatedToCron(gitRepositoryDefinition)) {
                gitRepositoryDefinitions.add(gitRepositoryDefinition);
            }
        }
        return gitRepositoryDefinitions;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.debug("Initialization of scm repository");
        daoStorage =
                pluginStorageService.registerDAOStorage("scm-repository",
                        ScmRepositoryDefinition.class);
        Validate.notNull(daoStorage);
        
        
        cronRegistryService.registerCronTask(SCM_AUTOUPDATING_CRON, CRON_DEFAULT_EXPRESSION,
                ScmScheduleCronJob.class, new JobDataMap());
        updateRepositories();
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.git.repositories.api.IGitRepositoryService#isAssociatedToCron(org.komea.product.plugins.repository.model.
     * ScmRepositoryDefinition)
     */
    @Override
    public boolean isAssociatedToCron(final ScmRepositoryDefinition _repositoryDefinition) {
    
    
        return cronTasks.containsKey(_repositoryDefinition.getKey());
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.git.repositories.api.IGitRepositoryService#initializeCronName(org.komea.product.plugins.repository.model.
     * ScmRepositoryDefinition)
     */
    @Override
    public synchronized String registerCronJobOfScm(
            final ScmRepositoryDefinition _repositoryDefinition) {
    
    
        Validate.notNull(_repositoryDefinition);
        Validate.notEmpty(_repositoryDefinition.getKey());
        final String cronName = _repositoryDefinition.getKey();
        cronTasks.put(_repositoryDefinition.getKey(), cronName);
        getDAO().saveOrUpdate(_repositoryDefinition);
        return cronName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.git.repositories.api.IGitRepositoryService#remove(org.komea.product.plugins.git.model.ScmRepositoryDefinition
     * )
     */
    @Override
    public void remove(final ScmRepositoryDefinition _object) {
    
    
        getDAO().delete(_object);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepositoryService#saveOrUpdate(org.komea.product.plugins.repository.model.
     * ScmRepositoryDefinition)
     */
    @Override
    public void saveOrUpdate(final ScmRepositoryDefinition _gitRepository) {
    
    
        if (!getDAO().exists(_gitRepository)
                && !getDAO().find(new ScmKeySearchFilter(_gitRepository)).isEmpty()) {
            throw new IllegalArgumentException("The key must be unique for a scm repository.");
        }
        getDAO().saveOrUpdate(_gitRepository);
        
    }
    
    
    public void setCronRegistryService(final ICronRegistryService _cronRegistryService) {
    
    
        cronRegistryService = _cronRegistryService;
    }
    
    
    /**
     * Sets the plugin storage service.
     * 
     * @param _pluginStorageService
     *            the plugin storage service.
     */
    public void setPluginStorageService(final IPluginStorageService _pluginStorageService) {
    
    
        pluginStorageService = _pluginStorageService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.IScmRepositoryService#updateRepositories()
     */
    @Override
    public void updateRepositories() {
    
    
        cronRegistryService.forceNow(SCM_AUTOUPDATING_CRON);
    }
    
}
