/*
 * Copyright 2005 Peter Lanser
 * MantisConnect is copyrighted to Victor Boctor
 * This program is distributed under the terms and conditions of the GPL
 * See LICENSE file for details.
 * For commercial applications to link with or modify MantisConnect, they
 * require the purchase of a MantisConnect commercial license.
 */

package org.komea.product.plugins.mantis.core;



import org.apache.axis.components.net.DefaultHTTPTransportClientProperties;



/**
 * @author Peter Lanser, planser@users.sourceforge.net
 */
public class JMTTransportClientProperties extends DefaultHTTPTransportClientProperties
{
    
    
    @Override
    public String getProxyHost() {
    
    
        final SessionFactory factory = (SessionFactory) SessionFactory.getInstance();
        if (factory.getProxyHost() != null) {
            return factory.getProxyHost();
        } else {
            return super.getProxyHost();
        }
    }
    
    
    @Override
    public String getProxyPort() {
    
    
        return String.valueOf(((SessionFactory) SessionFactory.getInstance()).getProxyPort());
    }
    
}
