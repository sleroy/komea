/*
 * Copyright 2005 Peter Lanser
 * MantisConnect is copyrighted to Victor Boctor
 * This program is distributed under the terms and conditions of the GPL
 * See LICENSE file for details.
 * For commercial applications to link with or modify MantisConnect, they
 * require the purchase of a MantisConnect commercial license.
 */

package org.komea.product.plugins.mantis.core;



/**
 * @author Peter Lanser, planser@users.sourceforge.net
 */
public class JMTException extends Exception
{
    
    
    private static final long serialVersionUID = 4885948644508250778L;
    
    
    
    public JMTException(final String msg) {
    
    
        super(msg);
    }
    
    
    public JMTException(final String msg, final Throwable t) {
    
    
        super(msg, t);
    }
    
    
    public JMTException(final Throwable t) {
    
    
        super(t);
    }
    
}
