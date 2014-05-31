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
public class Enum
{
    
    
    public static Enum ACCESS_LEVELS       = new Enum(7);
    
    public static Enum CUSTOM_FIELD_TYPES  = new Enum(11);
    
    public static Enum ETAS                = new Enum(5);
    
    public static Enum PRIORITIES          = new Enum(1);
    
    public static Enum PROJECT_STATUS      = new Enum(8);
    
    public static Enum PROJECT_VIEW_STATES = new Enum(9);
    
    public static Enum PROJECTIONS         = new Enum(4);
    
    public static Enum REPRODUCIBILITIES   = new Enum(3);
    
    public static Enum RESOLUTIONS         = new Enum(6);
    
    public static Enum SEVERITIES          = new Enum(2);
    
    public static Enum STATUS              = new Enum(0);
    
    public static Enum VIEW_STATES         = new Enum(10);
    
    private final int  code;
    
    
    
    private Enum(final int code) {
    
    
        this.code = code;
    }
    
    
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Enum)) {
            return false;
        }
        final Enum that = (Enum) obj;
        return getCode() == that.getCode();
    }
    
    
    public int getCode() {
    
    
        return code;
    }
    
    
    @Override
    public int hashCode() {
    
    
        return getCode();
    }
    
}
