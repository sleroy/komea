/**
 * 
 */

package org.komea.product.plugins.scm;



import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryProxyFactories;
import org.komea.product.plugins.scm.api.error.ScmAlreadyExistingScmRepositoryFactoryException;
import org.komea.product.plugins.scm.api.error.ScmRepositoryProxyTypeNotFoundException;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Defines the register of scm repository factories
 * 
 * @author sleroy
 */
@Service
public class ScmRepositoryFactories implements IScmRepositoryProxyFactories
{
    
    
    private static final Logger                           LOGGER       =
                                                                               LoggerFactory
                                                                                       .getLogger(ScmRepositoryFactories.class);
    
    
    private final Map<String, IScmRepositoryProxyFactory> factoriesMap =
                                                                               new HashMap<String, IScmRepositoryProxyFactory>();
    
    
    @Autowired
    private IKomeaFS                                      komeaFS;
    
    
    
    /**
     * Returns the Komea file system
     * 
     * @return the komea file system.
     */
    public IKomeaFS getKomeaFS() {
    
    
        return komeaFS;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.IScmRepositoryProxyFactories#newProxy(org.komea.product.plugins.repository.model.
     * ScmRepositoryDefinition)
     */
    @Override
    public IScmRepositoryProxy newProxy(final ScmRepositoryDefinition _repositoryDefinition) {
    
    
        Validate.notNull(_repositoryDefinition);
        final IScmRepositoryProxyFactory iScmRepositoryProxyFactory =
                factoriesMap.get(_repositoryDefinition.getType());
        if (iScmRepositoryProxyFactory == null) { throw new ScmRepositoryProxyTypeNotFoundException(
                "Scm repository factory not found for this type of scm : "
                        + _repositoryDefinition.getType()); }
        
        return iScmRepositoryProxyFactory.getProxy(_repositoryDefinition, getWorkspace());
    }
    
    
    @Override
    public void registerFactory(
            final String _providerType,
            final IScmRepositoryProxyFactory _proxyFactory) {
    
    
        LOGGER.debug("Register a factory for the type of scm {}", _providerType);
        if (factoriesMap.containsKey(_providerType)) { throw new ScmAlreadyExistingScmRepositoryFactoryException(
                _providerType); }
        factoriesMap.put(_providerType, _proxyFactory);
    }
    
    
    public void setKomeaFS(final IKomeaFS _komeaFS) {
    
    
        komeaFS = _komeaFS;
    }
    
    
    private File getWorkspace() {
    
    
        return komeaFS.getFileSystemFolder("scm-workspace");
    }
}
