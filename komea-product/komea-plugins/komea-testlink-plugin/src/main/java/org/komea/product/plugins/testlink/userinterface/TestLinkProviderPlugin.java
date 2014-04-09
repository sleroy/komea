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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgalerme
 */
@ProviderPlugin(
        type = ProviderType.NEWS,
        name = TestLinkProviderPlugin.TESTLINK_PROVIDER_PLUGIN,
        icon = "testlink",
        url = "/testlink-provider",
        eventTypes = {
            @EventTypeDef(
                    providerType = ProviderType.NEWS,
                    description = "Total Number of Test in server",
                    key = "TESTLINK_TOTAL_TEST",
                    name = "TestLink Total Test",
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO),})
@PluginAdminPages(
        @PluginMountPage(
                pluginName = TestLinkProviderPlugin.TESTLINK_PROVIDER_PLUGIN,
                page = TestLinkPage.class))
@Properties(group = "TestLink Plugin", value
        = {
            @Property(
                    description = "Refresh period of Testlink job",
                    key = TestLinkProviderPlugin.SETTING_PROVIDER_PERIOD_NAME,
                    type = String.class,
                    value = TestLinkProviderPlugin.TESTLINK_CRON_VALUE)})
public class TestLinkProviderPlugin {
        public static final String TESTLINK_PROVIDER_PLUGIN = "TestLink Provider plugin";

    protected static final String TESTLINK_CRON_VALUE = "0 0/1 * * * ?";

    protected static final String SETTING_PROVIDER_PERIOD_NAME = "testlink_refresh_period";

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLinkProviderPlugin.class);

    @PostConstruct
    public void init() {

        LOGGER.info("Loading testlink plugin");
    }
}
