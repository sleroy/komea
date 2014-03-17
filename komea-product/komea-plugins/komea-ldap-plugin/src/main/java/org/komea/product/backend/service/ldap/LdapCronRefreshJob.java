/**
 *
 */

package org.komea.product.backend.service.ldap;



import java.util.Collections;

import org.apache.commons.lang.Validate;
import org.komea.product.api.service.ldap.ILdapUserService;
import org.komea.product.api.service.ldap.LdapUser;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;



/**
 * This class defines a cron job for ldap server that populate user database.
 * 
 * @author sleroy
 */
@DisallowConcurrentExecution
public class LdapCronRefreshJob implements Job
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LdapCronRefreshJob.class);
    
    
    
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
     * @return
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
    public void createMissingLdapUser(
            final ILdapUserService ldapService,
            final IPersonService _personService,
            final IPersonGroupService personGroupService,
            final LdapUser ldapUser) {
    
    
        LOGGER.info("Creation of the user {} from LDAP", ldapUser);
        final Person personRequested = new Person();
        personRequested.setFirstName(ldapUser.getFirstName());
        personRequested.setLastName(ldapUser.getLastName());
        personRequested.setLogin(ldapUser.getUserName());
        personRequested.setPassword(ldapUser.getPassword());
        final String ldapDepartment = ldapUser.getDepartment();
        final PersonGroup department = createMissingDepartment(personGroupService, ldapDepartment);
        _personService.saveOrUpdatePerson(personRequested, Collections.<Project> emptyList(), null,
                department);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        final JobDataMap jobDataMap = _context.getMergedJobDataMap();
        launchCronTask(jobDataMap);
    }
    
    
    /**
     * Executes the cron job.
     * 
     * @param _jobDataMap
     *            the job datamap provided in argument to obtain
     *            informations (spring services)
     */
    public void launchCronTask(final JobDataMap _jobDataMap) {
    
    
        final ILdapUserService ldapService = (ILdapUserService) _jobDataMap.get("ldap");
        final IPersonService personService = (IPersonService) _jobDataMap.get("person");
        final IPersonGroupService personGroupService =
                (IPersonGroupService) _jobDataMap.get("group");
        Validate.notNull(personGroupService);
        Validate.notNull(personService);
        Validate.notNull(ldapService);
        try {
            for (final LdapUser ldapUser : ldapService.getUsers(null)) {
                if (Strings.isNullOrEmpty(ldapUser.getEmail())) {
                    continue;
                }
                // Test if the person exists in DB
                
                if (personService.findUserByEmail(ldapUser.getEmail()) != null) {
                    
                    createMissingLdapUser(ldapService, personService, personGroupService, ldapUser);
                    
                }
                
            }
        } catch (final Exception e) {
            LOGGER.error("LDAP connection is failing for the reason {}", e.getMessage(), e);
        }
        
    }
    
}
