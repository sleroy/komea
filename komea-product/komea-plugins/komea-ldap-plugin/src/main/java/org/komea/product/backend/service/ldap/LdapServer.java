/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.backend.service.ldap;


import java.io.Serializable;

import org.komea.product.database.api.IHasId;

/**
 * @author rgalerme
 */
public class LdapServer implements Serializable, IHasId {
    
    /**
     * This field describes
     */
    private static final long serialVersionUID = 7015356862814507688L;
    private String            ldapUrl          = "";
    private String            ldapLogin        = "";
    private String            ldapPassword     = "";
    private String            ldapBase         = "";
    private LdapAuthTypeEnum  ldapAuthTypeEnum = LdapAuthTypeEnum.SIMPLE; // default is simple
    private Integer           id;
    
    public LdapServer() {
    
    }
    
    public LdapServer(final String ldapUrl, final String ldapLogin, final String ldapPassword, final String ldapBase) {
    
        this.ldapUrl = ldapUrl;
        this.ldapLogin = ldapLogin;
        this.ldapPassword = ldapPassword;
        this.ldapBase = ldapBase;
    }
    
    public LdapAuthTypeEnum getLdapAuthTypeEnum() {
    
        return ldapAuthTypeEnum;
    }
    
    public void setLdapAuthTypeEnum(final LdapAuthTypeEnum ldapAuthTypeEnum) {
    
        this.ldapAuthTypeEnum = ldapAuthTypeEnum;
    }
    
    public String getLdapUrl() {
    
        return ldapUrl;
    }
    
    public void setLdapUrl(final String ldapUrl) {
    
        this.ldapUrl = ldapUrl;
    }
    
    public String getLdapUserDN() {
    
        return ldapLogin;
    }
    
    public void setLdapUserDN(final String ldapUserDN) {
    
        ldapLogin = ldapUserDN;
    }
    
    public String getLdapPassword() {
    
        return ldapPassword;
    }
    
    public void setLdapPassword(final String ldapPassword) {
    
        this.ldapPassword = ldapPassword;
    }
    
    public String getLdapBase() {
    
        return ldapBase;
    }
    
    public void setLdapBase(final String ldapBase) {
    
        this.ldapBase = ldapBase;
    }
    
    @Override
    public Integer getId() {
    
        return id;
    }
    
    @Override
    public void setId(final Integer _id) {
    
        id = _id;
        
    }
    
    @Override
    public String toString() {
    
        return "LdapServer [ldapUrl=" + ldapUrl + ", ldapLogin=" + ldapLogin + ", ldapPassword=" + ldapPassword + ", ldapBase=" + ldapBase
                + ", ldapAuthTypeEnum=" + ldapAuthTypeEnum + "]";
    }
    
}
