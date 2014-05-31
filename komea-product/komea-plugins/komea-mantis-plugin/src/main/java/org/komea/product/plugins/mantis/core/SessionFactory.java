/*
 * Copyright 2005 Peter Lanser
 * MantisConnect is copyrighted to Victor Boctor
 * This program is distributed under the terms and conditions of the GPL
 * See LICENSE file for details.
 * For commercial applications to link with or modify MantisConnect, they
 * require the purchase of a MantisConnect commercial license.
 */

package org.komea.product.plugins.mantis.core;



import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;



/**
 * @author Peter Lanser, planser@users.sourceforge.net
 */
@Service
public class SessionFactory implements ISessionFactory
{
    
    
    private static SessionFactory instance;
    
    
    
    public static SessionFactory getInstance() {
    
    
        return instance;
    }
    
    
    
    private String proxyHost;
    
    
    private int    proxyPort;
    
    
    
    public SessionFactory() {
    
    
    }
    
    
    public String getProxyHost() {
    
    
        return proxyHost;
    }
    
    
    public int getProxyPort() {
    
    
        return proxyPort;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        instance = this;
        System.setProperty("org.apache.axis.components.net.TransportClientProperties",
                "org.komea.product.plugins.mantis.core.JMTTransportClientProperties");
    }
    
    
    @Override
    public ISession newSession(final URL url, final String user, final String pwd)
            throws JMTException {
    
    
        return new Session(url, user, pwd);
    }
    
    
    @Override
    public void setProxy(final String host, final int port) {
    
    
        proxyHost = host;
        proxyPort = port;
    }
    
    
}
