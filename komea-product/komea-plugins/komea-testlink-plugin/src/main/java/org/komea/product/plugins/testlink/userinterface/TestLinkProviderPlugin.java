/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.userinterface;

import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.testlink.core.TestLinkAlertFactory;
import org.komea.product.plugins.testlink.core.TestsByStatusKPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rgalerme
 */
@ProviderPlugin(type = ProviderType.REQUIREMENTS, name = TestLinkProviderPlugin.TESTLINK_PROVIDER_PLUGIN,
        icon = "testlink", url = TestLinkAlertFactory.TESTLINK_URL, eventTypes = { //    @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of requirements",
        //            entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_REQUIREMENTS,
        //            name = "Number of requirements", severity = Severity.INFO),
        //    @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of tested test cases",
        //            entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_TESTED_CASES,
        //            name = "Number of tested test cases", severity = Severity.INFO),
        //    @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of unassociated test cases",
        //            entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNASSOCIATED_TESTS,
        //            name = "Number of unassociated test cases", severity = Severity.INFO),
        //    @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of unexecuted test cases",
        //            entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNEXECUTED_TESTS,
        //            name = "Number of unexecuted test cases", severity = Severity.INFO),
        //    @EventTypeDef(providerType = ProviderType.REQUIREMENTS, description = " of untested test cases",
        //            entityType = EntityType.PROJECT, key = TestLinkAlertFactory.TESTLINK_UNTESTED_TESTS,
        //            name = "Number of untested test cases", severity = Severity.INFO)
        })
@PluginAdminPages(
        @PluginMountPage(pluginName = TestLinkProviderPlugin.TESTLINK_PROVIDER_PLUGIN, page = TestLinkPage.class))
public class TestLinkProviderPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLinkProviderPlugin.class);

    public static final String TESTLINK_PROVIDER_PLUGIN = "TestLink Provider plugin";

    @Autowired
    private IKPIService kpiService;

    @PostConstruct
    public void init() {

        LOGGER.info("Loading testlink plugin");
        addKpi(testsByStatusKpi(ExecutionStatus.BLOCKED, ValueDirection.WORST));
        addKpi(testsByStatusKpi(ExecutionStatus.FAILED, ValueDirection.WORST));
        addKpi(testsByStatusKpi(ExecutionStatus.NOT_RUN, ValueDirection.WORST));
        addKpi(testsByStatusKpi(ExecutionStatus.PASSED, ValueDirection.BETTER));
        addKpi(totalTestsKpi());
    }

    private Kpi totalTestsKpi() {
        final TestsByStatusKPI testsByStatusKPI = new TestsByStatusKPI("");
        return new Kpi(null, "test_cases_total", "Total test cases", "Number of test cases",
                0d, Double.valueOf(Integer.MAX_VALUE), ValueDirection.BETTER, ValueType.INT,
                EntityType.PROJECT, null, "0 0 0/6 * * ?", 12, EvictionType.MONTHS, null,
                ProviderType.REQUIREMENTS, testsByStatusKPI.getFormula());
    }

    private Kpi testsByStatusKpi(final ExecutionStatus status, final ValueDirection valueDirection) {
        final String statusName = status.name().toLowerCase();
        final TestsByStatusKPI testsByStatusKPI = new TestsByStatusKPI(statusName);
        return new Kpi(null, "test_cases_" + statusName, statusName + " test cases", "Number of " + statusName + " test cases",
                0d, Double.valueOf(Integer.MAX_VALUE), valueDirection, ValueType.INT,
                EntityType.PROJECT, null, "0 0 0/6 * * ?", 12, EvictionType.MONTHS, null,
                ProviderType.REQUIREMENTS, testsByStatusKPI.getFormula());
    }

    private void addKpi(final Kpi _kpi) {
        if (!kpiService.exists(_kpi.getKpiKey())) {
            kpiService.saveOrUpdate(_kpi);
        }
    }
}
