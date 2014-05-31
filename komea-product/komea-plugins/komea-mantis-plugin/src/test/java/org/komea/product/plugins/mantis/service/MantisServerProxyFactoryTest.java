/**
 * 
 */

package org.komea.product.plugins.mantis.service;



import org.junit.Test;
import org.komea.product.plugins.mantis.core.Enum;
import org.komea.product.plugins.mantis.core.ISession;
import org.komea.product.plugins.mantis.core.SessionFactory;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.futureware.mantis.rpc.soap.client.ObjectRef;

import static org.junit.Assert.assertNotNull;



/**
 * @author sleroy
 */
public class MantisServerProxyFactoryTest
{
    
    
    private static final Logger LOGGER = LoggerFactory
                                               .getLogger(MantisServerProxyFactoryTest.class);
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.service.MantisServerProxyFactory#connexion(org.komea.product.plugins.mantis.model.MantisServerConfiguration)}
     * .
     */
    @Test
    public final void testConnexion() throws Exception {
    
    
        final MantisServerProxyFactory mantisServerProxyFactory = buildFactory();
        final MantisServerConfiguration serv = buildToceaConfiguration();
        assertNotNull(mantisServerProxyFactory.newTestConnector(serv));
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.service.MantisServerProxyFactory#newConnector(org.komea.product.plugins.mantis.model.MantisServerConfiguration)}
     * .
     */
    @Test
    public final void testNewConnector() throws Exception {
    
    
        final MantisServerProxyFactory mantisServerProxyFactory = buildFactory();
        final MantisServerConfiguration serv = buildToceaConfiguration();
        final ISession newConnector = mantisServerProxyFactory.connexion(serv);
        
        System.out.println(newConnector.getVersion());
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.service.MantisServerProxyFactory#newConnector(org.komea.product.plugins.mantis.model.MantisServerConfiguration)}
     * .
     */
    @Test
    public final void testNewSEnums() throws Exception {
    
    
        final MantisServerProxyFactory mantisServerProxyFactory = buildFactory();
        final MantisServerConfiguration serv = buildToceaConfiguration();
        final ISession newConnector = mantisServerProxyFactory.connexion(serv);
        
        dump(newConnector.getEnum(Enum.SEVERITIES));
        dump(newConnector.getEnum(Enum.PRIORITIES));
        dump(newConnector.getEnum(Enum.RESOLUTIONS));
        dump(newConnector.getEnum(Enum.STATUS));
        
    }
    
    
    private MantisServerProxyFactory buildFactory() {
    
    
        final MantisServerProxyFactory mantisServerProxyFactory = new MantisServerProxyFactory();
        final SessionFactory sessionFactory = new SessionFactory();
        sessionFactory.init();
        mantisServerProxyFactory.setSessionFactory(sessionFactory);
        return mantisServerProxyFactory;
    }
    
    
    private MantisServerConfiguration buildToceaConfiguration() {
    
    
        final MantisServerConfiguration serv = new MantisServerConfiguration();
        serv.setAddress("http://bugreport.tocea.fr/api/soap/mantisconnect.php");
        serv.setLogin("sleroy");
        serv.setPassword("ensapono");
        return serv;
    }
    
    
    /**
     * @param _enum
     */
    private void dump(final ObjectRef[] _enum) {
    
    
        for (final ObjectRef ref : _enum) {
            LOGGER.info("{}->{}", ref.getName(), ref.getId());
        }
        
    }
}
