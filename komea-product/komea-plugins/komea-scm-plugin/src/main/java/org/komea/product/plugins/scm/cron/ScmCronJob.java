
package org.komea.product.plugins.scm.cron;



// https://forums.terracotta.org/forums/posts/list/2768.page

import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.repository.model.ScmExecutionStatus;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryAnalysisService;
import org.komea.product.plugins.scm.api.IScmRepositoryProxyFactories;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This task checks the events happened in a scm repository since last execution.
 * 
 * @author sleroy
 */
@DisallowConcurrentExecution
public class ScmCronJob implements Job
{
    
    
    private static final Logger           LOGGER            = LoggerFactory
                                                                    .getLogger("scm-cron-repo");
    
    
    @Autowired
    private IScmRepositoryAnalysisService analysisService;
    
    
    @Autowired
    private IEventPushService             esperEngine       = null;
    
    @Autowired
    private IPersonService                personService     = null;
    
    
    private ScmRepositoryDefinition       repo              = null;
    
    
    @Autowired
    private IScmRepositoryService         repository        = null;
    
    @Autowired
    private IScmRepositoryProxyFactories  repositoryFactory = null;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        executeScmCron();
        
        
    }
    
    
    /**
     * Executes the scm cron
     */
    public void executeScmCron() {
    
    
        Validate.notNull(esperEngine);
        Validate.notNull(repository);
        Validate.notNull(repositoryFactory);
        Validate.notNull(repo);
        Validate.notNull(personService);
        IScmRepositoryProxy newProxy = null;
        final Date newTime = new Date();
        final ScmExecutionStatus scmExecutionStatus = new ScmExecutionStatus();
        try {
            
            newProxy = repositoryFactory.newProxy(repo);
            LOGGER.info("Verify if the repository has been cloned on the local disk.");
            if (!repo.isCloned()) {
                LOGGER.info("A cloning is required.");
                newProxy.getScmCloner().cloneRepository();
            }
            Validate.isTrue(repo.isCloned(), "Repository should have been cloned");
            
            LOGGER.info("Analysis of the repository : {} {}", repo.getRepoName(), repo.getUrl());
            analysisService.analysis(newProxy);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            scmExecutionStatus.setLastError(e);
        } finally {
            repo.setLastDateCheckout(newTime);
            repo.setLastExecutionStatus(scmExecutionStatus);
            repository.saveOrUpdate(repo);
            IOUtils.closeQuietly(newProxy);
            LOGGER.info("SCM Repository analysis [{}]", repo.getLastExecutionStatus().isSuccess()
                    ? "OK"
                        : "FAILED");
            
        }
    }
    
    
    public IScmRepositoryAnalysisService getAnalysisService() {
    
    
        return analysisService;
    }
    
    
    public IEventPushService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    public ScmRepositoryDefinition getRepo() {
    
    
        return repo;
    }
    
    
    public IScmRepositoryService getRepository() {
    
    
        return repository;
    }
    
    
    public IScmRepositoryProxyFactories getRepositoryFactory() {
    
    
        return repositoryFactory;
    }
    
    
    public void setAnalysisService(final IScmRepositoryAnalysisService _analysisService) {
    
    
        analysisService = _analysisService;
    }
    
    
    public void setEsperEngine(final IEventPushService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
    
    public void setRepo(final ScmRepositoryDefinition _fetch) {
    
    
        repo = _fetch;
    }
    
    
    public void setRepository(final IScmRepositoryService _repository) {
    
    
        repository = _repository;
    }
    
    
    public void setRepositoryFactory(final IScmRepositoryProxyFactories _repositoryFactory) {
    
    
        repositoryFactory = _repositoryFactory;
    }
    
    
}
