
package org.komea.product.backend.service.ldap;



import java.util.List;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.komea.product.api.service.ldap.ILdapUserService;
import org.komea.product.api.service.ldap.LdapUser;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Service;



/**
 * ILdapUserService Implementation
 * 
 * @author JavaChap
 */
@Service
public class LdapUserService implements ILdapUserService
{
    
    
    /**
     * 
     */
    private static final String LDAP_CRON_REFRESH = "LDAP-CRON-REFRESH";


    private static class UserAttributesMapper implements AttributesMapper
    {
        
        
        @Override
        public Object mapFromAttributes(final Attributes attrs) throws NamingException {
        
        
            final LdapUser user = new LdapUserImpl();
            if (attrs.get("uid") != null) {
                user.setUserName((String) attrs.get("uid").get());
            }
            if (attrs.get("cn") != null) {
                user.setFirstName((String) attrs.get("cn").get());
            }
            if (attrs.get("sn") != null) {
                user.setLastName((String) attrs.get("sn").get());
            }
            if (attrs.get("mail") != null) {
                user.setEmail((String) attrs.get("mail").get());
            }
            return user;
        }
    }
    
    
    
    /**
     * 
     */
    private static final String  CRON_LDAP        = "0 0/5 * * * ?";
    
    
    private static final long    serialVersionUID = 4889152297004460837L;
    
    @Autowired
    private IPersonGroupService  groupService;
    
    
    @Autowired
    @Qualifier("ldapTemplate")
    private LdapTemplate         ldapTemplate;
    
    
    @Autowired
    private IPersonService       personService;
    
    
    @Autowired
    private ICronRegistryService registryService;
    
    
    
    //
    // @Override
    // public void delete(final LdapUser user) {
    //
    //
    // final Name dn = buildDn(user);
    // ldapTemplate.unbind(dn);
    // }
    
    
    @Override
    public boolean authenticate(final String userName, final String password) {
    
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"))
                .and(new EqualsFilter("uid", userName));
        return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
    }
    
    
    /**
     * Return the group service
     * 
     * @return the group service.
     */
    public IPersonGroupService getGroupService() {
    
    
        return groupService;
    }
    
    
    //
    // @Override
    // public LdapUser save(final LdapUser user) {
    //
    //
    // final Name dn = buildDn(user);
    // ldapTemplate.bind(dn, null, buildAttributes(user));
    //
    // // Update Groups
    // for (final String group : user.getGroups()) {
    // try {
    // final DistinguishedName groupDn = new DistinguishedName();
    // groupDn.add("ou", "Groups");
    // groupDn.add("cn", group);
    // final DirContextOperations context = ldapTemplate.lookupContext(groupDn);
    // context.addAttributeValue("memberUid", user.getUserName());
    // ldapTemplate.modifyAttributes(context);
    // } catch (final Exception e) {
    // e.printStackTrace();
    // }
    // }
    // return user;
    // }
    //
    
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    public ICronRegistryService getRegistryService() {
    
    
        return registryService;
    }
    
    
    @Override
    public LdapUser getUser(final String userName) {
    
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"))
                .and(new EqualsFilter("uid", userName));
        final List<LdapUser> users =
                ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(),
                        new UserAttributesMapper());
        if (!users.isEmpty()) { return users.get(0); }
        return null;
    }
    
    
    @Override
    public List<LdapUser> getUsers(final String pattern) {
    
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"));
        if (pattern != null) {
            filter.and(new LikeFilter("uid", pattern));
        }
        final List<LdapUser> users =
                ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(),
                        new UserAttributesMapper());
        return users;
    }
    
    
    @PostConstruct
    public void initialize() {
    
    
        final JobDataMap properties = new JobDataMap();
        properties.put("ldap", this);
        properties.put("person", personService);
        properties.put("group", groupService);
        
        registryService.registerCronTask(LDAP_CRON_REFRESH, CRON_LDAP, LdapCronRefreshJob.class,
                properties);
        
    }
    
    
    public void setGroupService(final IPersonGroupService _groupService) {
    
    
        groupService = _groupService;
    }
    
    
    public void setLdapTemplate(final LdapTemplate ldapTemplate) {
    
    
        this.ldapTemplate = ldapTemplate;
    }
    
    
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
    
    public void setRegistryService(final ICronRegistryService _registryService) {
    
    
        registryService = _registryService;
    }
    
    
    // public LdapUser update(final LdapUser user) {
    //
    //
    // final Name dn = buildDn(user);
    // ldapTemplate.rebind(dn, null, buildAttributes(user));
    // return user;
    // }
    
    
    // private Attributes buildAttributes(final LdapUser user) {
    //
    //
    // final Attributes attrs = new BasicAttributes();
    // final BasicAttribute ocattr = new BasicAttribute("objectclass");
    // ocattr.add("person");
    // ocattr.add("inetOrgPerson");
    // attrs.put(ocattr);
    // attrs.put("cn", user.getFirstName());
    // attrs.put("sn", user.getLastName());
    // attrs.put("userPassword", "{SHA}" + encrypt(user.getPassword()));
    // attrs.put("mail", user.getEmail());
    //
    // return attrs;
    // }
    //
    //
    // private Name buildDn(final LdapUser user) {
    //
    //
    // final DistinguishedName dn = new DistinguishedName();
    // dn.add("ou", "People");
    // if (user.getDepartment() != null) {
    // dn.add("ou", user.getDepartment());
    // }
    // dn.add("uid", user.getUserName());
    // return dn;
    // }
    //
    //
    // private String encrypt(final String plaintext) {
    //
    //
    // MessageDigest md = null;
    // try {
    // md = MessageDigest.getInstance("SHA");
    // } catch (final NoSuchAlgorithmException e) {
    // throw new RuntimeException(e.getMessage());
    // }
    // try {
    // md.update(plaintext.getBytes("UTF-8"));
    // } catch (final UnsupportedEncodingException e) {
    // throw new RuntimeException(e.getMessage());
    // }
    // final byte raw[] = md.digest();
    // final String hash = new String(Base64.encode(raw));
    // return hash;
    // }
}
