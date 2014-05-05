/**
 *
 */

package org.komea.product.backend.service.ldap;


import org.apache.commons.lang.Validate;
import org.komea.product.api.service.ldap.ILdapService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class defines a cron job for ldap server that populate user database.
 *
 * @author sleroy
 */
@DisallowConcurrentExecution
public class LdapCronRefreshJob implements Job {
    
    private static final Logger LOGGER = LoggerFactory.getLogger("ldap-cron");
    
    @Autowired
    private ILdapService        ldapService;
    
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
    
        LOGGER.info("-@-@-@-@-@-@-@-@-@-@-@-@-@-@-@-@-");
        LOGGER.info("> Refreshing user and group database from LDAP");
        final JobDataMap jobDataMap = _context.getMergedJobDataMap();
        launchCronTask(jobDataMap);
        LOGGER.info("-@-@-@-@-@-@-@-@-@-@-@-@-@-@-@-@-");
    }
    
    /**
     * Executes the cron job.
     *
     * @param _jobDataMap
     *            the job datamap provided in argument to obtain
     *            informations (spring services)
     */
    public void launchCronTask(final JobDataMap _jobDataMap) {
    
        Validate.notNull(ldapService);
        
        try {
            ldapService.importInformations();
            
        } catch (final Exception e) {
            LOGGER.error("LDAP connection is failing for the reason {}", e.getMessage(), e);
        }
        
    }
    
}
