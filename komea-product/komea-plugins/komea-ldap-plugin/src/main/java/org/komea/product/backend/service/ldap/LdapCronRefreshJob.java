/**
 *
 */

package org.komea.product.backend.service.ldap;



import java.util.Collections;

import org.apache.commons.lang.Validate;
import org.komea.product.api.service.ldap.ILdapConnector;
import org.komea.product.api.service.ldap.ILdapUserService;
import org.komea.product.api.service.ldap.LdapUser;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;



/**
 * This class defines a cron job for ldap server that populate user database.
 * 
 * @author sleroy
 */
@DisallowConcurrentExecution
public class LdapCronRefreshJob implements Job
{
    
    
    private static final Logger   LOGGER             = LoggerFactory.getLogger("ldap-cron");
    @Autowired
    private ILdapUserService      ldapService        = null;
    
    @Autowired
    private IPersonGroupService   personGroupService = null;
    
    @Autowired
    private IPersonRoleService    personRoleService  = null;
    
    @Autowired
    private IPersonService        personService      = null;
    
    @Autowired
    private final ISettingService settingService     = null;
    
    
    
    /**
     * Ldap Cron refresh job.
     */
    public LdapCronRefreshJob() {
    
    
        super();
    }
    
    
    /**
     * Generate missing department.
     * 
     * @param _personGroupService
     * @param _ldapDepartment
     * @return the person group.
     */
    public PersonGroup createMissingDepartment(
            final IPersonGroupService _personGroupService,
            final String _ldapDepartment) {
    
    
        if (Strings.isNullOrEmpty(_ldapDepartment)) { return null; }
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
        personService.saveOrUpdatePerson(personRequested, Collections.<Project> emptyList());
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        LOGGER.info("-@-@-@-@-@-@-@-@-@-@-@-@-@-@-@-@-");
        LOGGER.info("> Refreshing user and group database from LDAP");
        final JobDataMap jobDataMap = _context.getMergedJobDataMap();
        launchCronTask(jobDataMap);
        LOGGER.info("-@-@-@-@-@-@-@-@-@-@-@-@-@-@-@-@-");
    }
    
    
    /**
     * @return the ldapService
     */
    public ILdapUserService getLdapService() {
    
    
        return ldapService;
    }
    
    
    /**
     * @return the personGroupService
     */
    public IPersonGroupService getPersonGroupService() {
    
    
        return personGroupService;
    }
    
    
    /**
     * @return the personRoleService
     */
    public IPersonRoleService getPersonRoleService() {
    
    
        return personRoleService;
    }
    
    
    /**
     * @return the personService
     */
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    /**
     * Executes the cron job.
     * 
     * @param _jobDataMap
     *            the job datamap provided in argument to obtain
     *            informations (spring services)
     */
    public void launchCronTask(final JobDataMap _jobDataMap) {
    
    
        Validate.notNull(personGroupService);
        Validate.notNull(personService);
        Validate.notNull(ldapService);
        Validate.notNull(personRoleService);
        
        
        try {
            final ILdapConnector connector = ldapService.newConnector();
            for (final LdapUser ldapUser : connector.getUsers(null)) {
                if (Strings.isNullOrEmpty(ldapUser.getEmail())) {
                    continue;
                }
                // Test if the person exists in DB
                
                if (!personService.existUserByEmail(ldapUser.getEmail())) {
                    
                    createMissingLdapUser(ldapUser);
                    
                }
                
            }
            connector.close();
        } catch (final Exception e) {
            LOGGER.error("LDAP connection is failing for the reason {}", e.getMessage(), e);
        }
        
    }
    
    
    /**
     * @param _ldapService
     *            the ldapService to set
     */
    public void setLdapService(final ILdapUserService _ldapService) {
    
    
        ldapService = _ldapService;
    }
    
    
    /**
     * @param _personGroupService
     *            the personGroupService to set
     */
    public void setPersonGroupService(final IPersonGroupService _personGroupService) {
    
    
        personGroupService = _personGroupService;
    }
    
    
    /**
     * @param _personRoleService
     *            the personRoleService to set
     */
    public void setPersonRoleService(final IPersonRoleService _personRoleService) {
    
    
        personRoleService = _personRoleService;
    }
    
    
    /**
     * @param _personService
     *            the personService to set
     */
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
}
