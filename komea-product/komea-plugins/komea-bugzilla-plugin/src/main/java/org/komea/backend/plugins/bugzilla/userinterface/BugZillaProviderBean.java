/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla.userinterface;

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
 * This class defines the bugzilla provider plugin. Informations are collected
 * at runtime.
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
@ProviderPlugin(
        type = ProviderType.BUGTRACKER,
        name = BugZillaProviderBean.BUGZILLA_PROVIDER_PLUGIN,
        icon = "/bugzilla.gif",
        url = "/bugzilla-provider",
        eventTypes = {
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    description = "Total Number of Bugs in BugZilla server",
                    key = "BUGZILLA_TOTAL_BUGS",
                    name = "Bugzilla Total Bugs",
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    description = "Number of unconfirmed bugs",
                    key = "BUGZILLA_TOTAL_BUGS",
                    name = "BugZilla Unconfirmed bugs",
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO),})
@PluginAdminPages(
        @PluginMountPage(
                pluginName = BugZillaProviderBean.BUGZILLA_PROVIDER_PLUGIN,
                page = BugZillaPage.class))
@Properties(group = "BugZilla Plugin", value
        = {
            @Property(
                    description = "Refresh period of GIT job",
                    key = BugZillaProviderBean.SETTING_PROVIDER_PERIOD_NAME,
                    type = String.class,
                    value = BugZillaProviderBean.BUGZILLA_CRON_VALUE)})
public class BugZillaProviderBean {

    public static final String BUGZILLA_PROVIDER_PLUGIN = "BubZilla Provider plugin";

    protected static final String BUGZILLA_CRON_VALUE = "0 0/1 * * * ?";

    protected static final String SETTING_PROVIDER_PERIOD_NAME = "bugzilla_refresh_period";

    private static final Logger LOGGER = LoggerFactory.getLogger(BugZillaProviderBean.class);

    @PostConstruct
    public void init() {

        LOGGER.info("Loading bugZilla plugin");
    }

}
