/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.backend.service.ldap;

import java.util.List;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.api.service.ldap.ILdapUserService;
import org.komea.product.backend.service.ISettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rgalerme
 */
@Service
public class LdapService implements ILdapService {
    
    @Autowired
    private ISettingService settingService;
    
    @Override
    public void saveOrUpdate(LdapServer _ldapServer) {
        settingService.getProxy(ILdapUserService.LDAP_SERVER).setStringValue(_ldapServer.getLdapUrl());
        settingService.getProxy(ILdapUserService.LDAP_USER_DN).setStringValue(_ldapServer.getLdapUserDN());
        settingService.getProxy(ILdapUserService.LDAP_PASSWORD).setStringValue(_ldapServer.getLdapPassword());
        settingService.getProxy(ILdapUserService.LDAP_BASE).setStringValue(_ldapServer.getLdapBase());
        settingService.<LdapAuthTypeEnum> getProxy(ILdapUserService.LDAP_AUTH_TYPE).setValue(_ldapServer.getLdapAuthTypeEnum());
    }
    
    @Override
    public List<LdapServer> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void delete(LdapServer _ldapServer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void load(LdapServer _ldapServer) {
        _ldapServer.setLdapUrl(settingService.getProxy(ILdapUserService.LDAP_SERVER).getStringValue());
        _ldapServer.setLdapUserDN(settingService.getProxy(ILdapUserService.LDAP_USER_DN).getStringValue());
        _ldapServer.setLdapPassword(settingService.getProxy(ILdapUserService.LDAP_PASSWORD).getStringValue());
        _ldapServer.setLdapBase(settingService.getProxy(ILdapUserService.LDAP_BASE).getStringValue());
         final LdapAuthTypeEnum ldapAuthTypeEnum =
                    settingService.<LdapAuthTypeEnum> getProxy(ILdapUserService.LDAP_AUTH_TYPE).getValue();
         _ldapServer.setLdapAuthTypeEnum(ldapAuthTypeEnum);
    }
    
}
