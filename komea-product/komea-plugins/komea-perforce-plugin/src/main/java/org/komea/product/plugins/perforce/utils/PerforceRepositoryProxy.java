
package org.komea.product.plugins.perforce.utils;



import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.ScmEventFactory;
import org.komea.product.plugins.scm.api.plugin.IScmCloner;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.api.plugin.IScmEventFactory;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * Fetchs the new feeds and produces alerts.
 * 
 * @author sleroy
 */

public class PerforceRepositoryProxy implements IScmRepositoryProxy
{
    
    
    private static final Logger           LOGGER = LoggerFactory
                                                         .getLogger("perforce-repository-reader");
    
    
    @Autowired
    private IPersonService                personService;
    
    
    private final ScmRepositoryDefinition repositoryDefinition;
    
    
    private final ScmEventFactory         scmEventFactory;
    
    
    private final File                    storageFolder;
    
    
    
    /**
     * Builds the GIT repositor proxy, an exception may be thrown when creating the GIT Proxy.
     */
    
    public PerforceRepositoryProxy(
            final ScmRepositoryDefinition _repositoryDefinition,
            final File _storageFolder) {
    
    
        super();
        repositoryDefinition = _repositoryDefinition;
        storageFolder = _storageFolder;
        scmEventFactory = new ScmEventFactory(repositoryDefinition);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
    
    
        // TODO Auto-generated method stub
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getAllCommitsFromABranch(java.lang.String, org.joda.time.DateTime)
     */
    @Override
    public List<IScmCommit> getAllCommitsFromABranch(
            final String _branchName,
            final DateTime _lastDate) {
    
    
        // TODO Auto-generated method stub
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getAllTagsFromABranch(java.lang.String)
     */
    @Override
    public Set<String> getAllTagsFromABranch(final String _branchName) {
    
    
        // TODO Auto-generated method stub
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getBranches()
     */
    @Override
    public List<String> getBranches() {
    
    
        // TODO Auto-generated method stub
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getEventFactory()
     */
    @Override
    public IScmEventFactory getEventFactory() {
    
    
        // TODO Auto-generated method stub
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getRepositoryDefinition()
     */
    @Override
    public ScmRepositoryDefinition getRepositoryDefinition() {
    
    
        // TODO Auto-generated method stub
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getScmCloner()
     */
    @Override
    public IScmCloner getScmCloner() {
    
    
        return new PerforceCloner(storageFolder, repositoryDefinition);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#testConnection()
     */
    @Override
    public void testConnection() {
    
    
        // TODO Auto-generated method stub
        
    }
}
