/**
 * 
 */

package org.komea.product.backend.service.ldap;



import java.util.Collections;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.base.Strings;



/**
 * This class defines a cron job for ldap server that populate user database.
 * 
 * @author sleroy
 */
@DisallowConcurrentExecution
public class LdapCronRefreshJob implements Job
{
    
    
    /**
     * Ldap Cron refresh job.
     */
    public LdapCronRefreshJob() {
    
    
        super();
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
     *            the job datamap provided in argument to obtain informations (spring services)
     */
    public void launchCronTask(final JobDataMap _jobDataMap) {
    
    
        final ILdapUserService ldapService = (ILdapUserService) _jobDataMap.get("ldap");
        final IPersonService personService = (IPersonService) _jobDataMap.get("person");
        final IPersonGroupService personGroupService =
                (IPersonGroupService) _jobDataMap.get("group");
        Validate.notNull(personGroupService);
        Validate.notNull(personService);
        Validate.notNull(ldapService);
        for (final LdapUser ldapUser : ldapService.getUsers(null)) {
            if (Strings.isNullOrEmpty(ldapUser.getEmail())) {
                continue;
            }
            // Test if the person exists in DB
            final PersonCriteria personCriteria = new PersonCriteria();
            personCriteria.createCriteria().andEmailEqualTo(ldapUser.getEmail());
            if (personService.selectByCriteria(personCriteria).isEmpty()) {
                
                createMissingLdapUser(ldapService, personService, personGroupService, ldapUser);
                
            }
            
            
        }
        
    }
    
    
    /**
     * Generate missing department.
     * 
     * @param personGroupService
     * @param ldapDepartment
     * @return
     */
    private PersonGroup createMissingDepartment(
            final IPersonGroupService personGroupService,
            final String ldapDepartment) {
    
    
        PersonGroup department =
                org.komea.product.backend.utils.CollectionUtil.singleOrNull(personGroupService
                        .searchPersonGroups(Collections.singletonList(ldapDepartment),
                                EntityType.DEPARTMENT));
        
        if (department == null) {
            department = new PersonGroup();
            department.setType(PersonGroupType.DEPARTMENT);
            department.setDescription("Department " + ldapDepartment + " created from ldap");
            department.setName(ldapDepartment);
            department.setPersonGroupKey(ldapDepartment.toUpperCase());
            personGroupService.saveOrUpdate(department);
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
    private void createMissingLdapUser(
            final ILdapUserService ldapService,
            final IPersonService _personService,
            final IPersonGroupService personGroupService,
            final LdapUser ldapUser) {
    
    
        final Person personRequested = new Person();
        personRequested.setFirstName(ldapUser.getFirstName());
        personRequested.setLastName(ldapUser.getLastName());
        personRequested.setLogin(ldapUser.getUserName());
        personRequested.setPassword(ldapUser.getPassword());
        final String ldapDepartment = ldapUser.getDepartment();
        final PersonGroup department = createMissingDepartment(personGroupService, ldapDepartment);
        _personService.saveOrUpdate(personRequested, null, null, department);
        
    }
    
}
