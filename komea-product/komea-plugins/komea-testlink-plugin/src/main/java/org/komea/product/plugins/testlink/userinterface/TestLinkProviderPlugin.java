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
import org.komea.product.backend.service.kpi.KpiBuilder;
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
        icon = "testlink", url = TestLinkAlertFactory.TESTLINK_URL, eventTypes = {})
@PluginAdminPages(
        @PluginMountPage(pluginName = TestLinkProviderPlugin.TESTLINK_PROVIDER_PLUGIN,
                page = TestLinkPage.class))
public class TestLinkProviderPlugin {

    public static final String TESTLINK_PROVIDER_PLUGIN = "TestLink Provider plugin";

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLinkProviderPlugin.class);

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

    private void addKpi(final Kpi _kpi) {

        if (!kpiService.exists(_kpi.getKpiKey())) {
            kpiService.saveOrUpdate(_kpi);
        }
    }

    private Kpi testsByStatusKpi(final ExecutionStatus status, final ValueDirection valueDirection) {

        final String statusName = status.name().toLowerCase();
        final TestsByStatusKPI testsByStatusKPI = new TestsByStatusKPI(statusName);
        return KpiBuilder.create().key("test_cases_" + statusName)
                .description("Number of " + statusName + " test cases").name(statusName + " test cases")
                .interval(0d, 10000d).produceValue(ValueType.INT, valueDirection).forProject().dailyKPI()
                .providerType(ProviderType.REQUIREMENTS).queryScript(testsByStatusKPI.getFormula()).build();

    }

    private Kpi totalTestsKpi() {

        final TestsByStatusKPI testsByStatusKPI = new TestsByStatusKPI("");
        return KpiBuilder.create().name("Total test cases").description("Number of test cases").key("test_cases_total")
                .interval(0d, 10000d).produceValue(ValueType.INT, ValueDirection.BETTER).forProject().dailyKPI()
                .providerType(ProviderType.REQUIREMENTS).queryScript(testsByStatusKPI.getFormula()).build();

    }
}
