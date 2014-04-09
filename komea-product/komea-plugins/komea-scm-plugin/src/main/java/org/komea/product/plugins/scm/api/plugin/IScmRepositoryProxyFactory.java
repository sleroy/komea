/**
 * 
 */

package org.komea.product.plugins.scm.api.plugin;



import java.io.File;

import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;



/**
 * This interface defines a factory to deliver proxy above a scm repository proxy.
 * 
 * @author sleroy
 */
public interface IScmRepositoryProxyFactory
{
    
    
    /**
     * Returns the proxy for a repository definition.
     * 
     * @param _repositoryDefinition
     *            the repository definition
     * @param _storageFolder
     *            the folder to store the cloned directories for this type of scm.
     * @return the proxy.
     */
    IScmRepositoryProxy getProxy(ScmRepositoryDefinition _repositoryDefinition, File _storageFolder);
}
