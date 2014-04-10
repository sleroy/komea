/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import java.util.List;
import javax.annotation.PostConstruct;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.EventType;
import org.komea.product.plugins.bugzilla.sah.BugzillaServerConfiguration;
import org.komea.product.plugins.bugzilla.sah.EventTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
        eventTypes = {}
)
@PluginAdminPages(
        @PluginMountPage(
                pluginName = BugZillaProviderPlugin.BUGZILLA_PROVIDER_PLUGIN,
                page = BugZillaPage.class))
@Properties(group = "BugZilla Plugin",
        value = {
            @Property(
                    description = "Refresh period of Bugzilla job",
                    key = BugZillaProviderPlugin.SETTING_PROVIDER_PERIOD_NAME,
                    type = String.class,
                    value = BugZillaProviderPlugin.BUGZILLA_CRON_VALUE)
        }
)
public class BugZillaProviderPlugin {

    public static final String BUGZILLA_PROVIDER_PLUGIN = "BugZilla Provider plugin";

    private static final Logger LOGGER
            = LoggerFactory
            .getLogger(BugZillaProviderPlugin.class);

    protected static final String BUGZILLA_CRON_VALUE = "0 0/1 * * * ?";

    protected static final String SETTING_PROVIDER_PERIOD_NAME = "bugzilla_refresh_period";
    @Autowired
    private IEventTypeService evenTypeService;

    @PostConstruct
    public void init() {
        final BugzillaServerConfiguration configuration = new BugzillaServerConfiguration();
        final List<EventType> eventTypes = EventTypeBuilder.allEventTypes(configuration);
        for (final EventType eventType : eventTypes) {
            evenTypeService.registerEvent(eventType);
        }
        LOGGER.info("Loading bugZilla plugin");
    }

}
