/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.api.service.ldap;


import java.util.List;

import org.komea.product.backend.service.ldap.LdapServer;

/**
 * @author rgalerme
 */

public interface ILdapService {
    
    public void saveOrUpdate(LdapServer _ldapServer);
    public List<LdapServer> selectAll();
    public void delete(LdapServer _ldapServer);
    public LdapServer load();
}
