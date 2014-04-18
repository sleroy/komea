/**
 * 
 */

package org.komea.product.plugins.testlink.core;


import javax.annotation.PostConstruct;

import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
@ProviderPlugin(eventTypes = {
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of test cases with blocked status", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_BLOCKED_TESTS, name = "Number of blocked test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of test cases with failed status", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_FAILED_TESTS, name = "Number of failed test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of requirements", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_REQUIREMENTS, name = "Number of requirements", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of success tests", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_SUCCESS_TESTS, name = "Number of success test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of tested test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_TESTED_CASES, name = "Number of tested test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of total test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_TOTAL_TESTS, name = "Number of total tests cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of unassociated test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNASSOCIATED_TESTS, name = "Number of unassociated test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of unexecuted test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNEXECUTED_TESTS, name = "Number of unexecuted test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of untested test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNTESTED_TESTS, name = "Number of untested test cases", severity = Severity.INFO)

}, icon = "testlink", name = "Testlink plugin", type = ProviderType.REQUIREMENTS, url = TestLinkAlertFactory.TESTLINK_URL)
public class TestLinkPlugin {
    
    /**
     * 
     */
    private static final String  CRON_EXPR     = "0 0/1 * * * ?";
    /**
     * 
     */
    private static final String  TESTLINK_CRON = "TESTLINK_CRON";
    @Autowired
    private ICronRegistryService registryService;
    
    @PostConstruct
    public void init() {
    
        registryService.removeCronTask(TESTLINK_CRON);
        registryService.registerCronTask(TESTLINK_CRON, CRON_EXPR, TestLinkCheckerCron.class, new JobDataMap());
        
    }
}
