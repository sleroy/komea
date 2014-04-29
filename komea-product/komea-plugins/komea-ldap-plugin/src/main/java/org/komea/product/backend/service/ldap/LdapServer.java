/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.backend.service.ldap;

import java.io.Serializable;

/**
 *
 * @author rgalerme
 */
public class LdapServer implements Serializable {

    private String ldapUrl;
    private String ldapLogin;
    private String ldapPassword;
    private String ldapBase;
    private LdapAuthTypeEnum ldapAuthTypeEnum;

    public LdapServer() {
    }

    public LdapServer(String ldapUrl, String ldapLogin, String ldapPassword, String ldapBase) {
        this.ldapUrl = ldapUrl;
        this.ldapLogin = ldapLogin;
        this.ldapPassword = ldapPassword;
        this.ldapBase = ldapBase;
    }

    public LdapAuthTypeEnum getLdapAuthTypeEnum() {
        return ldapAuthTypeEnum;
    }

    public void setLdapAuthTypeEnum(LdapAuthTypeEnum ldapAuthTypeEnum) {
        this.ldapAuthTypeEnum = ldapAuthTypeEnum;
    }
    
    public String getLdapUrl() {
        return ldapUrl;
    }

    public void setLdapUrl(String ldapUrl) {
        this.ldapUrl = ldapUrl;
    }

    public String getLdapUserDN() {
        return ldapLogin;
    }

    public void setLdapUserDN(String ldapUserDN) {
        this.ldapLogin = ldapUserDN;
    }

    public String getLdapPassword() {
        return ldapPassword;
    }

    public void setLdapPassword(String ldapPassword) {
        this.ldapPassword = ldapPassword;
    }

    public String getLdapBase() {
        return ldapBase;
    }

    public void setLdapBase(String ldapBase) {
        this.ldapBase = ldapBase;
    }
    
    
}
