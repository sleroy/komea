
package org.komea.product.plugins.scm;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This service defines the component that contains repositories.
 * 
 * @author sleroy
 */
@Service
public final class ScmRepositoryService implements IScmRepositoryService
{
    
    
    private static final Logger                        LOGGER    =
                                                                         LoggerFactory
                                                                                 .getLogger(ScmRepositoryService.class);
    
    
    private final Map<String, String>                  cronTasks = new HashMap();
    
    
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
    public ScmRepositoryDefinition findByName(final String _feedName) {
    
    
        for (final ScmRepositoryDefinition feed : getDAO().selectAll()) {
            if (_feedName.equals(feed.getKey())) { return feed; }
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
    
    
        final List<ScmRepositoryDefinition> gitRepositoryDefinitions =
                new ArrayList<ScmRepositoryDefinition>();
        for (final ScmRepositoryDefinition gitRepositoryDefinition : gitRepositoryDefinitions) {
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
    
    
        final String cronName = _repositoryDefinition.getRepoName();
        cronTasks.put(_repositoryDefinition.getKey(), cronName);
        getDAO().saveOrUpdate(_repositoryDefinition);
        return cronName;
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
    
    
        getDAO().saveOrUpdate(_gitRepository);
        
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
    
}
