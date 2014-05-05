/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.backend.service.ldap;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.komea.product.api.service.ldap.ILdapConnector;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.api.service.ldap.LdapUser;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

/**
 * @author rgalerme
 */
@Service
public class LdapService implements ILdapService {
    
    private static final Logger           LOGGER = LoggerFactory.getLogger("ldap-plugin");
    
    @Autowired
    private IPersonGroupService           personGroupService;
    
    @Autowired
    private IPersonRoleService            personRoleService;
    
    @Autowired
    private IPersonService                personService;
    
    private IDAOObjectStorage<LdapServer> configurationStorage;
    
    @Autowired
    private IPluginStorageService         pluginStorage;
    
    @Autowired
    private ILdapConnector                ldapConnector;
    
    /**
     * Generate missing department.
     *
     * @param _personGroupService
     * @param _ldapDepartment
     * @return the person group.
     */
    public PersonGroup createMissingDepartment(final IPersonGroupService _personGroupService, final String _ldapDepartment) {
    
        if (Strings.isNullOrEmpty(_ldapDepartment)) {
            return null;
        }
        PersonGroup department = _personGroupService.selectByKey(_ldapDepartment);
        
        if (department == null) {
            LOGGER.info("Creation of the department from ldap {}", _ldapDepartment);
            department = new PersonGroup();
            department.setType(PersonGroupType.DEPARTMENT);
            department.setDescription("Department " + _ldapDepartment + " created from ldap");
            department.setName(_ldapDepartment);
            department.setPersonGroupKey(_ldapDepartment.toUpperCase());
            _personGroupService.saveOrUpdate(department);
        }
        return department;
    }
    
    /**
     * Create missing ldap users.
     *
     * @param _ldapService
     * @param _personGroupService
     * @param _personGroupService2
     * @param _ldapUser
     */
    public void createMissingLdapUser(final LdapUser ldapUser) {
    
        LOGGER.info("Creation of the user {} from LDAP", ldapUser);
        final Person personRequested = new Person();
        personRequested.setFirstName(ldapUser.getFirstName());
        personRequested.setLastName(ldapUser.getLastName());
        personRequested.setLogin(ldapUser.getUserName());
        String password = ldapUser.getPassword();
        if (password == null) {
            password = "";
        }
        personRequested.setPassword(password);
        personRequested.setEmail(ldapUser.getEmail());
        personRequested.setUserBdd(UserBdd.LDAP);
        final PersonRole defaultUserRole = personRoleService.getDefaultUserRole();
        if (defaultUserRole != null) {
            personRequested.setIdPersonRole(defaultUserRole.getId());
        }
        final String ldapDepartment = ldapUser.getDepartment();
        final PersonGroup department = createMissingDepartment(personGroupService, ldapDepartment);
        if (department != null) {
            personRequested.setIdPersonGroup(department.getId());
        }
        personService.saveOrUpdatePersonAndItsProjects(personRequested, Collections.<Project> emptyList());
        
    }
    
    @Override
    public void delete(final LdapServer _ldapServer) {
    
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void importInformations() throws IOException {
    
        LOGGER.info("import LDAP informations into komea");
        if (!ldapConnector.isInit()) {
            LOGGER.warn("LDAP is not initialized");
            return;
        }
        for (final LdapUser ldapUser : ldapConnector.getUsers(null)) {
            if (Strings.isNullOrEmpty(ldapUser.getEmail())) {
                continue;
            }
            // Test if the person exists in DB
            if (!personService.existUserByEmail(ldapUser.getEmail())) {
                
                createMissingLdapUser(ldapUser);
                
            }
            
        }
        ldapConnector.close();
        
    }
    
    @PostConstruct
    public void init() {
    
        configurationStorage = pluginStorage.registerDAOStorage("LDAP", LdapServer.class);
        
    }
    
    @Override
    public void initConnection() {
    
        ldapConnector.initConnection();
        
    }
    
    @Override
    public LdapServer load() {
    
        List<LdapServer> ldapConfigurations = configurationStorage.selectAll();
        return ldapConfigurations.isEmpty() ? new LdapServer() : configurationStorage.selectAll().get(0);
    }
    
    @Override
    public void saveOrUpdate(final LdapServer _ldapServer) {
    
        Validate.notNull(_ldapServer);
        configurationStorage.saveOrUpdate(_ldapServer);
    }
    
}
