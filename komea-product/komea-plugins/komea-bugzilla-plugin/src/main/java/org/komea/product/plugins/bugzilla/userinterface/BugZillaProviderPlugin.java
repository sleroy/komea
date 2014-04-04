/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

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
        name = BugZillaProviderPlugin.BUGZILLA_PROVIDER_PLUGIN,
        icon = "bugzilla",
        url = "/bugzilla-provider",
        eventTypes
        = {
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
                    severity = Severity.INFO),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO,
                    key = "closed_bugs",
                    name = "Closed bugs",
                    description = "Number of Closed bugs"),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO,
                    key = "open_bugs",
                    name = "Open Bugs",
                    description = "Number of Open bugs"),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO,
                    key = "open_not_fixed_bugs",
                    name = "Open not fixed Bbgs",
                    description = "Number of Open not fixed bugs"),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO,
                    key = "open_minor_severity_bugs",
                    name = "Open minor severity bugs",
                    description = "Number of Open minor severity bugs"),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO,
                    key = "open_major_severity_bugs",
                    name = "Open major severity bugs",
                    description = "Number of Open major severity bugs"),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO,
                    key = "open_critical_severity_bugs",
                    name = "Open critical severity bugs",
                    description = "Number of Open critical severity bugs"),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO,
                    key = "open_blocker_severity_bugs",
                    name = "Open blocker severity bugs",
                    description = "Number of Open blocker severity bugs"),
            @EventTypeDef(
                    providerType = ProviderType.BUGTRACKER,
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO,
                    key = "open_enhancement_severity_bugs",
                    name = "Open enhancement severity bugs",
                    description = "Number of Open enhancement severity bugs")
        }
)
@PluginAdminPages(
        @PluginMountPage(
                pluginName = BugZillaProviderPlugin.BUGZILLA_PROVIDER_PLUGIN,
                page = BugZillaPage.class))
@Properties(group = "BugZilla Plugin", value
        = {
            @Property(
                    description = "Refresh period of Bugzilla job",
                    key = BugZillaProviderPlugin.SETTING_PROVIDER_PERIOD_NAME,
                    type = String.class,
                    value = BugZillaProviderPlugin.BUGZILLA_CRON_VALUE)})
public class BugZillaProviderPlugin {

    public static final String BUGZILLA_PROVIDER_PLUGIN = "BubZilla Provider plugin";

    protected static final String BUGZILLA_CRON_VALUE = "0 0/1 * * * ?";

    protected static final String SETTING_PROVIDER_PERIOD_NAME = "bugzilla_refresh_period";

    private static final Logger LOGGER = LoggerFactory.getLogger(BugZillaProviderPlugin.class);

    @PostConstruct
    public void init() {

        LOGGER.info("Loading bugZilla plugin");
    }

}
