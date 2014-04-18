/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.userinterface;


import javax.annotation.PostConstruct;

import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.plugins.testlink.core.TestLinkAlertFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rgalerme
 */
@ProviderPlugin(type = ProviderType.REQUIREMENTS, name = TestLinkProviderPlugin.TESTLINK_PROVIDER_PLUGIN, icon = "testlink", url = TestLinkAlertFactory.TESTLINK_URL, eventTypes = {
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of test cases with blocked status", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_BLOCKED_TESTS, name = "Number of blocked test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of test cases with failed status", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_FAILED_TESTS, name = "Number of failed test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of requirements", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_REQUIREMENTS, name = "Number of requirements", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of success tests", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_SUCCESS_TESTS, name = "Number of success test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of tested test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_TESTED_CASES, name = "Number of tested test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of total test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_TOTAL_TESTS, name = "Number of total tests cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of unassociated test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNASSOCIATED_TESTS, name = "Number of unassociated test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of unexecuted test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNEXECUTED_TESTS, name = "Number of unexecuted test cases", severity = Severity.INFO),
        @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of untested test cases", entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNTESTED_TESTS, name = "Number of untested test cases", severity = Severity.INFO) })
@PluginAdminPages(@PluginMountPage(pluginName = TestLinkProviderPlugin.TESTLINK_PROVIDER_PLUGIN, page = TestLinkPage.class))
@Properties(group = "TestLink Plugin", value = {
    @Property(description = "Refresh period of Testlink job", key = TestLinkProviderPlugin.SETTING_PROVIDER_PERIOD_NAME, type = String.class, value = TestLinkProviderPlugin.TESTLINK_CRON_VALUE) })
public class TestLinkProviderPlugin {
    
    public static final String    TESTLINK_PROVIDER_PLUGIN     = "TestLink Provider plugin";
    
    protected static final String TESTLINK_CRON_VALUE          = "0 0/1 * * * ?";
    
    protected static final String SETTING_PROVIDER_PERIOD_NAME = "testlink_refresh_period";
    
    private static final Logger   LOGGER                       = LoggerFactory.getLogger(TestLinkProviderPlugin.class);
    
    @PostConstruct
    public void init() {
    
        LOGGER.info("Loading testlink plugin");
    }
}
