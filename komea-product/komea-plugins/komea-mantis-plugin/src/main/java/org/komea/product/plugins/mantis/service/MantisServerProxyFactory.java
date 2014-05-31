/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.mantis.service;



import java.net.URL;

import org.komea.product.plugins.mantis.api.IMantisServerProxy;
import org.komea.product.plugins.mantis.api.IMantisServerProxyFactory;
import org.komea.product.plugins.mantis.core.ISession;
import org.komea.product.plugins.mantis.core.ISessionFactory;
import org.komea.product.plugins.mantis.core.MantisServerProxy;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@Service
public class MantisServerProxyFactory implements IMantisServerProxyFactory
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MantisServerProxyFactory.class);
    
    @Autowired
    private ISessionFactory     sessionFactory;
    
    
    
    /**
     * Method connexion.
     * 
     * @param _server
     *            MantisServerConfiguration
     * @return MantisConnect
     */
    public ISession connexion(final MantisServerConfiguration _server) {
    
    
        ISession conn = null;
        try {
            conn =
                    sessionFactory.newSession(new URL(_server.getAddress()), _server.getLogin(),
                            _server.getPassword());
            
        } catch (final Exception ex) {
            throw new MantisPluginException(ex);
        }
        return conn;
    }
    
    
    /**
     * Method newConnector.
     * 
     * @param serv
     *            MantisServerConfiguration
     * @return IMantisServerProxy
     * @see org.komea.backend.IMantisServerProxyFactory.bugzilla.api.IBugZillaServerProxyFactory#newConnector(MantisServerConfiguration)
     */
    @Override
    public IMantisServerProxy newConnector(final MantisServerConfiguration serv) {
    
    
        return new MantisServerProxy(connexion(serv));
    }
    
    
    @Override
    public IMantisServerProxy newTestConnector(final MantisServerConfiguration serv) {
    
    
        return new MantisServerProxy(testConnexion(serv));
    }
    
    
    private ISession testConnexion(final MantisServerConfiguration _server) {
    
    
        ISession connexion = connexion(_server);
        try {
            
            LOGGER.info("Connected to server mantis {} : {}", _server.getAddress(),
                    connexion.getVersion());
        } catch (final Exception e) {
            LOGGER.error("Mantis connexion {}...", e.getMessage(), e);
            connexion = null;
        }
        return connexion;
    }


    public ISessionFactory getSessionFactory() {
    
    
        return sessionFactory;
    }


    public void setSessionFactory(ISessionFactory _sessionFactory) {
    
    
        sessionFactory = _sessionFactory;
    }
}
