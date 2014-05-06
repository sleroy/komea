/**
 * 
 */

package org.komea.product.backend.service;

import javax.annotation.PostConstruct;

import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.backend.service.ldap.LdapAuthTypeEnum;
import org.komea.product.backend.service.ldap.LdapServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sleroy
 */
@Service
public class LdapDefaultConfig {



	@Autowired
	private ILdapService userService;

	/**
     * 
     */
	public LdapDefaultConfig() {

		super();
	}

	

	/**
	 * Init
	 */
	@PostConstruct
	public void init() {

		LdapServer ldapServer = new LdapServer();
		ldapServer.setLdapUrl("ldap://192.168.1.134:389");
		ldapServer.setLdapAuthTypeEnum(LdapAuthTypeEnum.SIMPLE);
		ldapServer.setLdapBase("dc=tocea,dc=com");
		ldapServer.setLdapUserDN("");
		ldapServer.setLdapPassword("");
		getUserService().saveOrUpdate(ldapServer);

	}



	public ILdapService getUserService() {
		return userService;
	}



	public void setUserService(ILdapService userService) {
		this.userService = userService;
	}


}
