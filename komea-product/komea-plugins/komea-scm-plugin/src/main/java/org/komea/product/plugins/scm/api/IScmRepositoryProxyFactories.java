/**
 * 
 */

package org.komea.product.plugins.scm.api;



import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory;



/**
 * This interface provides an scm repository fro ma repository proxy definition.
 * 
 * @author sleroy
 */
public interface IScmRepositoryProxyFactories
{
    
    
    /**
     * Returns the repository factory or throws an exception if no proxy has been found for this type of SCM.
     * 
     * @param _repositoryDefinition
     *            the repository definition.
     * @return the proxy or an exception.
     */
    IScmRepositoryProxy newProxy(ScmRepositoryDefinition _repositoryDefinition);
    
    
    /**
     * Register a factory for a type of scm repository.
     * 
     * @param _providerType
     *            the provider type
     * @param _proxyFactory
     *            the proxy factory.
     */
    void registerFactory(String _providerType, IScmRepositoryProxyFactory _proxyFactory);
    
}
