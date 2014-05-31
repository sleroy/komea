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



/**
 * @author Peter Lanser, planser@users.sourceforge.net
 */
public interface ISessionFactory
{
    
    
    public ISession newSession(URL url, String user, String pwd) throws JMTException;
    
    
    public void setProxy(String host, int port);
    
}
