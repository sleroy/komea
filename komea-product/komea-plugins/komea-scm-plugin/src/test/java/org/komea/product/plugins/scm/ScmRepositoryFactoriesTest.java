/**
 * 
 */

package org.komea.product.plugins.scm;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.error.ScmAlreadyExistingScmRepositoryFactoryException;
import org.komea.product.plugins.scm.api.error.ScmRepositoryProxyTypeNotFoundException;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class ScmRepositoryFactoriesTest
{
    
    
    /**
     * 
     */
    private static final String    DEMO_PROXY = "DEMO_PROXY";
    @Mock
    private IKomeaFS               komeaFS;
    @InjectMocks
    private ScmRepositoryFactories scmRepositoryFactories;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.scm.ScmRepositoryFactories#getKomeaFS()}.
     */
    @Test @Ignore
    public final void testGetKomeaFS() throws Exception {
    
    
        assertEquals("komeaFS", komeaFS, scmRepositoryFactories.getKomeaFS());
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryFactories#newProxy(org.komea.product.plugins.repository.model.ScmRepositoryDefinition)}
     * .
     */
    @Test @Ignore(expected = ScmRepositoryProxyTypeNotFoundException.class)
    public final void testNewProxyWithNoFactory() throws Exception {
    
    
        final ScmRepositoryDefinition repositoryDefinition = new ScmRepositoryDefinition();
        repositoryDefinition.setType("HNU");
        scmRepositoryFactories.newProxy(repositoryDefinition);
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryFactories#registerFactory(java.lang.String, org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory)}
     * .
     */
    @Test @Ignore(expected = ScmAlreadyExistingScmRepositoryFactoryException.class)
    public final void testRegisterFactoryDoubleInsertion() throws Exception {
    
    
        final IScmRepositoryProxyFactory proxyFactory = mock(IScmRepositoryProxyFactory.class);
        scmRepositoryFactories.registerFactory(DEMO_PROXY, proxyFactory);
        scmRepositoryFactories.registerFactory(DEMO_PROXY, proxyFactory);
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryFactories#registerFactory(java.lang.String, org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxyFactory)}
     * .
     */
    @Test @Ignore
    public final void testRegisterFactoryNormalInsertion() throws Exception {
    
    
        final IScmRepositoryProxyFactory proxyFactory = mock(IScmRepositoryProxyFactory.class);
        
        scmRepositoryFactories.registerFactory(DEMO_PROXY, proxyFactory);
        final ScmRepositoryDefinition repositoryDefinition = new ScmRepositoryDefinition();
        repositoryDefinition.setType(DEMO_PROXY);
        
        final IScmRepositoryProxy mockRepositoryProxy = Mockito.mock(IScmRepositoryProxy.class);
        when(
                proxyFactory.getProxy(Matchers.eq(repositoryDefinition),
                        Matchers.any(java.io.File.class))).thenReturn(mockRepositoryProxy);
        final IScmRepositoryProxy proxy = scmRepositoryFactories.newProxy(repositoryDefinition);
        assertNotNull(proxy);
        assertEquals("Assume mock returned ", mockRepositoryProxy, proxy);
    }
    
    
}
