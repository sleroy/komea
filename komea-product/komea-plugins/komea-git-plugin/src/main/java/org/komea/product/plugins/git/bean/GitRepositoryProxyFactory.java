/**
 * 
 */

package org.komea.product.plugins.git.bean;



import java.io.File;

import org.komea.product.plugins.git.utils.GItRepositoryProxy;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory;



/**
 * @author sleroy
 */
public class GitRepositoryProxyFactory implements IScmRepositoryProxyFactory
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory#getProxy(org.komea.product.plugins.repository.model.
     * ScmRepositoryDefinition, java.io.File)
     */
    @Override
    public IScmRepositoryProxy getProxy(
            final ScmRepositoryDefinition _repositoryDefinition,
            final File _storageFolder) {
    
    
        return new GItRepositoryProxy(_repositoryDefinition, _storageFolder);
    }
    
}
