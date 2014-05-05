/**
 * 
 */

package org.komea.product.plugins.perforce.bean;



import java.io.File;

import org.komea.product.backend.service.ISpringService;
import org.komea.product.plugins.perforce.utils.PerforceRepositoryProxy;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class PerforceRepositoryProxyFactory implements IScmRepositoryProxyFactory
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
    
    
        final PerforceRepositoryProxy perforceRepositoryProxy =
                new PerforceRepositoryProxy(_repositoryDefinition, _storageFolder);
        springService.autowirePojo(perforceRepositoryProxy);
        return perforceRepositoryProxy;
    }
    
    
    public ISpringService getSpringService() {
    
    
        return springService;
    }
    
    
    public void setSpringService(final ISpringService _springService) {
    
    
        springService = _springService;
    }
    
}
