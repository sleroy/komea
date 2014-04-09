/**
 *
 */
package org.komea.product.backend.service.demodata;

import javax.annotation.PostConstruct;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.kpi.standard.BuildPerDay;
import org.komea.product.plugins.kpi.standard.BuildPerMonth;
import org.komea.product.plugins.kpi.standard.NumberOfBrokenBuildPerUser;
import org.komea.product.plugins.kpi.standard.NumberOfFixedBuildPerUser;
import org.komea.product.plugins.kpi.standard.ProjectHealthInfluencePerUser;
import org.komea.product.plugins.kpi.standard.SonarMetricKpi;
import org.komea.product.plugins.kpi.standard.SuccessfulBuildPerDay;
import org.komea.product.plugins.kpi.standard.SuccessfulBuildPerMonth;
import org.komea.product.plugins.kpi.standard.SuccessfulBuildRatePerWeek;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
@ProviderPlugin(
        eventTypes = {},
        icon = "provider",
        name = "KPI Provider plugin",
        type = ProviderType.NEWS,
        url = "/provider")
public class KpiDemoService {

    @Autowired
    private IKPIService kpiService;

    /**
     *
     */
    public KpiDemoService() {

        super();

    }

    public Kpi actualLineCoverage() {

        // metric_value
        // sonar
        // metricName
        return buildSonarMetricKpi("Line coverage", "line_coverage");
    }

    public Kpi buildSonarMetricKpi(final String _title, final String _metricName) {

        return KpiBuilder.createAscending().nameAndKeyDescription(_title)
                .entityType(EntityType.PROJECT).expirationYear()
                .providerType(ProviderType.QUALITY)
                .query("new " + SonarMetricKpi.class.getName() + "('" + _metricName + "')")
                .cronSixHours().build();
    }

    /**
     * @return
     */
    public Kpi healthRateOfUserActions() {

        return KpiBuilder.createAscending()
                .nameAndKeyDescription("Project Health influence of a developer")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationYear()
                .query(ProjectHealthInfluencePerUser.class).cronDays(1).build();

    }

    @PostConstruct
    public void initialize() {

        saveOrUpdate(numberSuccessBuildPerWeek());
        saveOrUpdate(numberSuccessBuildPerDay());
        saveOrUpdate(numberBuildPerDay());
        saveOrUpdate(numberBuildPerMonth());
        saveOrUpdate(actualLineCoverage());
        saveOrUpdate(buildSonarMetricKpi("Volumetry", "lines"));
        saveOrUpdate(buildSonarMetricKpi("Classes", "classes"));
        saveOrUpdate(buildSonarMetricKpi("Files", "files"));
        saveOrUpdate(buildSonarMetricKpi("Packages", "packages"));
        saveOrUpdate(buildSonarMetricKpi("Functions", "functions"));
        saveOrUpdate(buildSonarMetricKpi("Public Documentation API (%)", "public_api"));
        saveOrUpdate(buildSonarMetricKpi("Comment lines density (%)", "comment_lines_density"));
        saveOrUpdate(buildSonarMetricKpi("Public documented api density",
                "public_documented_api_density"));
        saveOrUpdate(buildSonarMetricKpi("Average file complexity", "file_complexity"));
        saveOrUpdate(buildSonarMetricKpi("Average class complexity", "class_complexity"));
        saveOrUpdate(buildSonarMetricKpi("Number of tests", "tests"));
        saveOrUpdate(buildSonarMetricKpi("Number of unit test errors", "test_errors"));
        saveOrUpdate(buildSonarMetricKpi("Number of skipped tests", "skipped_tests"));
        saveOrUpdate(buildSonarMetricKpi("Number of test failures", "tests_failures"));
        saveOrUpdate(buildSonarMetricKpi("Unit tests success (%)", "test_success_density"));
        saveOrUpdate(buildSonarMetricKpi("Branch coverage(%)", "branch_coverage"));
        saveOrUpdate(buildSonarMetricKpi("IT Branch coverage(%)", "it_branch_coverage"));
        saveOrUpdate(buildSonarMetricKpi("Branch coverage improvement", "new_branch_coverage"));
        saveOrUpdate(buildSonarMetricKpi("Line coverage improvement", "new_coverage"));
        saveOrUpdate(buildSonarMetricKpi("Duplicated code density", "duplicated_lines_density"));
        saveOrUpdate(buildSonarMetricKpi("Violation density", "violations_density"));
        saveOrUpdate(buildSonarMetricKpi("Blocker violations", "blocker_violations"));
        saveOrUpdate(buildSonarMetricKpi("Blocker violation progression", "new_blocker_violations"));
        saveOrUpdate(buildSonarMetricKpi("Violation progression", "new_violations"));
        saveOrUpdate(buildSonarMetricKpi("Business value", "business_value"));
        saveOrUpdate(successRateJenkinsPerWeekKpi());
        saveOrUpdate(successRateJenkinsPerMonthKpi());
        saveOrUpdate(numberOfBuildBrokenPerUser());
        saveOrUpdate(numberOfFixedBuildPerUser());
        saveOrUpdate(healthRateOfUserActions());
    }

    // KPI : Durée maximale de bon fonctionnement (tps entre deux builds success average)
    // KPI : Durée de non-fonctionnement (moyenne)
    // KPI : Durée de non-fonctionnement (maximale)
    public Kpi numberBuildPerDay() {

        return KpiBuilder.createAscending().nameAndKeyDescription("Number of build per day")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationMonth().query(BuildPerDay.class)
                .cronSixHours().build();

    }

    public Kpi numberBuildPerMonth() {

        return KpiBuilder.createAscending().nameAndKeyDescription("Number of build per month")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationYear().query(BuildPerMonth.class)
                .cronWeek().build();

    }

    /**
     * @return
     */
    public Kpi numberOfBuildBrokenPerUser() {

        return KpiBuilder.createAscending()
                .nameAndKeyDescription("Number of broken  builds in a month")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationMonth()
                .query(NumberOfBrokenBuildPerUser.class).cronDays(1).build();

    }

    /**
     * @return
     */
    public Kpi numberOfFixedBuildPerUser() {

        return KpiBuilder.createAscending()
                .nameAndKeyDescription("Number of fixed builds in a month")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationMonth()
                .query(NumberOfFixedBuildPerUser.class).cronDays(1).build();

    }

    public Kpi numberSuccessBuildPerDay() {

        return KpiBuilder.createAscending()
                .nameAndKeyDescription("Number of successful build per day")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationMonth()
                .query(SuccessfulBuildPerDay.class).cronSixHours().build();
    }

    public Kpi numberSuccessBuildPerWeek() {

        return KpiBuilder.createAscending()
                .nameAndKeyDescription("Number of successful build per week")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationYear()
                .query(SuccessfulBuildPerMonth.class).cronThreeDays().build();
    }

    /**
     * @return
     */
    public Kpi successRateJenkinsPerMonthKpi() {

        return KpiBuilder.createAscending()
                .nameAndKeyDescription("Success build rate in Jenkins per week")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationYear()
                .query(SuccessfulBuildPerMonth.class).cronDays(1).build();

    }

    /**
     * @return
     */
    public Kpi successRateJenkinsPerWeekKpi() {

        return KpiBuilder.createAscending()
                .nameAndKeyDescription("Success build rate in Jenkins per week")
                .providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).expirationYear()
                .query(SuccessfulBuildRatePerWeek.class).cronDays(1).build();

    }

    /**
     * @param _numberSuccessBuildPerDay
     */
    private void saveOrUpdate(final Kpi _kpi) {

        if (kpiService.findKPI(KpiKey.ofKpi(_kpi)) != null) {
            return;
        }
        kpiService.saveOrUpdate(_kpi);

    }
}
