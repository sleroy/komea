/**
 * 
 */

package org.komea.product.plugins.git.bean;



import java.io.File;

import org.komea.product.backend.api.ISpringService;
import org.komea.product.plugins.git.utils.GitRepositoryProxy;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class GitRepositoryProxyFactory implements IScmRepositoryProxyFactory
{
    
    
    @Autowired
    private ISpringService springService;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory#getProxy(org.komea.product.plugins.repository.model.
     * ScmRepositoryDefinition, java.io.File)
     */
    @Override
    public IScmRepositoryProxy getProxy(
            final ScmRepositoryDefinition _repositoryDefinition,
            final File _storageFolder) {
    
    
        final GitRepositoryProxy gitRepositoryProxy =
                new GitRepositoryProxy(_repositoryDefinition, _storageFolder);
        springService.autowirePojo(gitRepositoryProxy);
        return gitRepositoryProxy;
    }
    
    
    public ISpringService getSpringService() {
    
    
        return springService;
    }
    
    
    public void setSpringService(final ISpringService _springService) {
    
    
        springService = _springService;
    }
    
}
