/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.service;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.core.BZBugCountKPI;
import org.komea.product.plugins.bugzilla.core.BZCheckerCron;
import org.komea.product.plugins.bugzilla.model.BzFilter;
import org.komea.product.plugins.bugzilla.model.BzSearch;
import org.komea.product.plugins.bugzilla.userinterface.BugZillaPage;
import org.quartz.JobDataMap;
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
        name = BZProviderPlugin.BUGZILLA_PROVIDER_PLUGIN,
        icon = "bugzilla",
        url = "/bugzilla-provider",
        eventTypes = {})
@PluginAdminPages(
        @PluginMountPage(
                pluginName = BZProviderPlugin.BUGZILLA_PROVIDER_PLUGIN,
                page = BugZillaPage.class))
public class BZProviderPlugin {

    public static final String BUGZILLA_PROVIDER_PLUGIN = "BugZilla Provider plugin";

    /**
     *
     */
    private static final String BUGZILLA_CRON = "BUGZILLA_CRON";

    private static Logger LOGGER
            = LoggerFactory
            .getLogger(BZProviderPlugin.class);

    protected static String BUGZILLA_CRON_VALUE = "0 0/1 * * * ?";

    /**
     * @return the logger
     */
    public static Logger getLogger() {

        return LOGGER;
    }

    /**
     * @param _logger the logger to set
     */
    public static void setLogger(final Logger _logger) {

        LOGGER = _logger;
    }

    @Autowired
    private IBZConfigurationDAO bugZillaConfiguration;

    @Autowired
    private IEventTypeService evenTypeService;

    @Autowired
    private ICronRegistryService registryService;

    @Autowired
    private IKPIService kpiService;

    /**
     * @return the bugZillaConfiguration
     */
    public IBZConfigurationDAO getBugZillaConfiguration() {

        return bugZillaConfiguration;
    }

    /**
     * @return the evenTypeService
     */
    public IEventTypeService getEvenTypeService() {

        return evenTypeService;
    }

    /**
     * @return the registryService
     */
    public ICronRegistryService getRegistryService() {

        return registryService;
    }

    @PostConstruct
    public void init() {

        LOGGER.info("Loading bugZilla plugin");
        registryService.removeCronTask(BUGZILLA_CRON);
        registryService.registerCronTask(BUGZILLA_CRON, BUGZILLA_CRON_VALUE,
                BZCheckerCron.class, new JobDataMap());
        registryService.forceNow(BUGZILLA_CRON);

        addKpi(bzTotalBugs());
        addKpi(bzOpenBugs());
        addKpi(bzOpenNotFixedBugs());
        addKpi(bzClosedBugs());
        for (final String severity : Arrays.asList("minor", "major", "critical", "blocker")) {
            addKpi(bzOpenBySeverityBugs(severity));
        }

    }

    private void addKpi(final Kpi _kpi) {
        if (!kpiService.exists(_kpi.getKpiKey())) {
            kpiService.saveOrUpdate(_kpi);
        }
    }

    private Kpi bzKpi(final String key, final String name, final String description,
            final ValueDirection valueDirection, final String formula) {
        return new Kpi(null, key, name, description, 0d, Double.valueOf(Integer.MAX_VALUE), valueDirection, ValueType.INT,
                EntityType.PROJECT, null, "0 0/5 * * * ?", 12, EvictionType.MONTHS, null, ProviderType.BUGTRACKER, formula);
    }

    private Kpi bzOpenBugs() {
        final IDynamicDataQuery query = BZBugCountKPI.create(
                BzSearch.create(
                        BzFilter.create("status", false, "new", "unconfirmed", "onhold", "accepted", "assigned", "opened", "reopened")
                )
        );
        return bzKpi("bugs_status_open", "Open bugs", "", ValueDirection.WORST, query.getFormula());
    }

    private Kpi bzOpenBySeverityBugs(final String severity) {
        final IDynamicDataQuery query = BZBugCountKPI.create(
                BzSearch.create(
                        BzFilter.create("status", false, "new", "unconfirmed", "onhold", "accepted", "assigned", "opened", "reopened"),
                        BzFilter.create("severity", true, severity)
                )
        );
        return bzKpi("bugs_status_open_severity_" + severity, "Open " + severity + " bugs", "", ValueDirection.WORST, query.getFormula());
    }

    private Kpi bzOpenNotFixedBugs() {
        final IDynamicDataQuery query = BZBugCountKPI.create(
                BzSearch.create(
                        BzFilter.create("status", true, "closed", "delivered", "resolved")
                )
        );
        return bzKpi("bugs_status_open_not_fixed", "Open NOT fixed bugs", "", ValueDirection.WORST, query.getFormula());
    }

    private Kpi bzClosedBugs() {
        final IDynamicDataQuery query = BZBugCountKPI.create(
                BzSearch.create(
                        BzFilter.create("status", true, "new", "unconfirmed", "onhold", "accepted", "assigned", "opened", "reopened")
                )
        );
        return bzKpi("bugs_status_closed", "Closed bugs", "", ValueDirection.BETTER, query.getFormula());
    }

    private Kpi bzTotalBugs() {
        final IDynamicDataQuery query = BZBugCountKPI.create();
        return bzKpi("bugs_total", "Total bugs", "", ValueDirection.WORST, query.getFormula());
    }

    /**
     * @param _bugZillaConfiguration the bugZillaConfiguration to set
     */
    public void setBugZillaConfiguration(final IBZConfigurationDAO _bugZillaConfiguration) {

        bugZillaConfiguration = _bugZillaConfiguration;
    }

    /**
     * @param _evenTypeService the evenTypeService to set
     */
    public void setEvenTypeService(final IEventTypeService _evenTypeService) {

        evenTypeService = _evenTypeService;
    }

    /**
     * @param _registryService the registryService to set
     */
    public void setRegistryService(final ICronRegistryService _registryService) {

        registryService = _registryService;
    }
}
