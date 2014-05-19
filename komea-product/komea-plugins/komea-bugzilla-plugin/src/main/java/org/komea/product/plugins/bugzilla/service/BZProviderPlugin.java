/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.userinterface.BugZillaPage;
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
@ProviderPlugin(type = ProviderType.BUGTRACKER, name = BZProviderPlugin.BUGZILLA_PROVIDER_PLUGIN, icon = "bugzilla",
        url = "/bugzilla-provider", eventTypes = {})
@PluginAdminPages(@PluginMountPage(pluginName = BZProviderPlugin.BUGZILLA_PROVIDER_PLUGIN, page = BugZillaPage.class))
public class BZProviderPlugin {

	public static final String	BUGZILLA_PROVIDER_PLUGIN	= "BugZilla Provider plugin";

	private static final Logger	LOGGER	                 = LoggerFactory.getLogger(BZProviderPlugin.class);

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {

		return LOGGER;
	}

	/**
	 * @param _logger
	 *            the logger to set
	 */
	public static void setLogger(final Logger _logger) {

		LOGGER = _logger;
	}

	@Autowired
	private IBZConfigurationDAO	bugZillaConfiguration;

	@Autowired
	private IEventTypeService	evenTypeService;

	@Autowired
	private IKPIService	        kpiService;

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

	@PostConstruct
	public void init() {

		LOGGER.info("Loading bugZilla plugin");

		addKpi(bzTotalBugs());
		addKpi(bzOpenBugs());
		addKpi(bzOpenNotFixedBugs());
		addKpi(bzClosedBugs());
		for (final String severity : Arrays.asList("minor", "major", "critical", "blocker")) {
			addKpi(bzOpenBySeverityBugs(severity));
		}

	}

	/**
	 * @param _bugZillaConfiguration
	 *            the bugZillaConfiguration to set
	 */
	public void setBugZillaConfiguration(final IBZConfigurationDAO _bugZillaConfiguration) {

		bugZillaConfiguration = _bugZillaConfiguration;
	}

	/**
	 * @param _evenTypeService
	 *            the evenTypeService to set
	 */
	public void setEvenTypeService(final IEventTypeService _evenTypeService) {

		evenTypeService = _evenTypeService;
	}

	private void addKpi(final Kpi _kpi) {

		if (!kpiService.exists(_kpi.getKpiKey())) {
			kpiService.saveOrUpdate(_kpi);
		}
	}

	private Kpi bzClosedBugs() {

		return bzKpi(
		        "bugs_status_closed",
		        "Closed bugs",
		        "Number of closed bugs",
		        ValueDirection.BETTER,
		        "BZBugCountKPI.create(Search.create(Filter.create(\"status\", true, \"new\",\n"
		                + "		        \"unconfirmed\", \"onhold\", \"accepted\", \"assigned\", \"opened\", \"reopened\")));");
	}

	private Kpi bzKpi(final String key, final String name, final String description,
	        final ValueDirection valueDirection, final String formula) {

		return KpiBuilder.create().name(name).key(key).description(description).interval(0d, 100000d).dailyKPI()
		        .providerType(ProviderType.BUGTRACKER).queryScript(formula).forProject()
		        .produceValue(ValueType.INT, ValueDirection.BETTER).build();

	}

	private Kpi bzOpenBugs() {

		return bzKpi(
		        "bugs_status_open",
		        "Open bugs",
		        "Number of open bugs",
		        ValueDirection.WORST,
		        "BZBugCountKPI.create(Search.create(Filter.create(\"status\", false, \"new\",\n"
		                + "		        \"unconfirmed\", \"onhold\", \"accepted\", \"assigned\", \"opened\", \"reopened\")));");
	}

	private Kpi bzOpenBySeverityBugs(final String severity) {

		return bzKpi(
		        "bugs_status_open_severity_" + severity,
		        "Open " + severity + " bugs",
		        "Number of open bugs with " + severity + " severity",
		        ValueDirection.WORST,
		        "BZBugCountKPI.create(Search.create(Filter.create(\"status\", false, \"new\",\n"
		                + "		        \"unconfirmed\", \"onhold\", \"accepted\", \"assigned\", \"opened\", \"reopened\"), Filter.create(\"severity\", true,\n"
		                + "		        severity)))");
	}

	private Kpi bzOpenNotFixedBugs() {

		return bzKpi("bugs_status_open_not_fixed", "Open NOT fixed bugs", "Number of open but not fixed bugs",
		        ValueDirection.WORST,
		        "final BZBugCountKPI query = BZBugCountKPI.create(Search.create(Filter.create(\"status\", true, \"closed\",\n"
		                + "		        \"delivered\", \"resolved\")));");
	}

	private Kpi bzTotalBugs() {

		return bzKpi("bugs_total", "Total bugs", "Total number of bugs", ValueDirection.WORST,
		        "final BZBugCountKPI query = BZBugCountKPI.create();");
	}
}
