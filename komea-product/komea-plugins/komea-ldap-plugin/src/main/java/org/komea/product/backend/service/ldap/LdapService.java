/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.backend.service.ldap;


import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rgalerme
 */
@Service
public class LdapService implements ILdapService {
    
    private IDAOObjectStorage<LdapServer> configurationStorage;
    
    @Autowired
    private IPluginStorageService         pluginStorage;
    
    @PostConstruct
    public void init() {
    
        configurationStorage = pluginStorage.registerDAOStorage("LDAP", LdapServer.class);
        
    }
    
    @Override
    public void saveOrUpdate(final LdapServer _ldapServer) {
    
        Validate.notNull(_ldapServer);
        configurationStorage.saveOrUpdate(_ldapServer);
    }
    
    @Override
    public List<LdapServer> selectAll() {
    
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void delete(final LdapServer _ldapServer) {
    
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public LdapServer load() {
    
        return configurationStorage.selectAll().get(0);
    }
    
}
