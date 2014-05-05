/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.api.service.ldap;


import java.io.IOException;

import org.komea.product.backend.service.ldap.LdapServer;

/**
 * @author rgalerme
 *         Provide service to manipulate LDAP
 */

public interface ILdapService {
    
    /**
     * This method save or update an LDAP configuration
     * 
     * @param _ldapServer
     */
    public void saveOrUpdate(LdapServer _ldapServer);
    
    /**
     * This method delete an LDAP configuration
     * 
     * @param _ldapServer
     */
    public void delete(LdapServer _ldapServer);
    
    /**
     * This method load an LDAP configuration
     * 
     * @return
     */
    public LdapServer load();
    public void initConection();
    public void importInformations() throws IOException;
}
