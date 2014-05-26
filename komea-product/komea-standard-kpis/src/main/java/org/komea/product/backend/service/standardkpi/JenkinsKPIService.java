/**
 *
 */
package org.komea.product.backend.service.standardkpi;

import javax.annotation.PostConstruct;
import org.komea.product.backend.api.standardkpi.IStandardKpiService;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.kpi.standard.jenkins.BuildPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfBrokenBuildPerUserPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfFailedBuildPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfFixedBuildPerUserPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfInterruptedBuildPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfJobConfigurationChangesPerWeek;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfSuccessfulBuildPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfUnstableBuildPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.ProjectBuildHealthInfluencePerUser;
import org.komea.product.plugins.kpi.standard.jenkins.SuccessfulBuildRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
@Service
@Transactional
public class JenkinsKPIService {

    @Autowired
    public IStandardKpiService standardKpiBuilderService;

    /**
     * @return
     */
    public Kpi healthRateOfUserActions() {

        return KpiBuilder.createAscending().nameAndKey("Project health influence")
                .description("Project Health influence of a developer")
                .produceValue(ValueType.PERCENT, ValueDirection.BETTER)
                .providerType(ProviderType.CI_BUILD).entityType(EntityType.PERSON).dailyKPI()
                .interval(-100d, 100d).query(ProjectBuildHealthInfluencePerUser.class).build();

    }

    @PostConstruct
    public void initJenkinsKPI() {

        saveOrUpdate(numberOfConfigurationChanges());
        saveOrUpdate(numberSuccessBuildPerDay());
        saveOrUpdate(numberOfInterruptedBuildPerDay());
        saveOrUpdate(numberOfBuildPerDay());
        saveOrUpdate(numberOfUnstableBuildPerDay());
        saveOrUpdate(numberOfFailedBuildPerDay());
        saveOrUpdate(numberOfBuildBrokenPerUser());
        saveOrUpdate(numberOfFixedBuildPerUser());
        saveOrUpdate(jenkinsSuccessBuildRate());
        saveOrUpdate(healthRateOfUserActions());

    }

    /**
     * @return
     */
    public Kpi jenkinsSuccessBuildRate() {

        return KpiBuilder.createAscending().nameAndKey("Successful builds (%)")
                .description("Success project build rate").providerType(ProviderType.CI_BUILD)
                .produceValue(ValueType.PERCENT, ValueDirection.BETTER)
                .entityType(EntityType.PROJECT).dailyKPI().query(SuccessfulBuildRate.class)
                .interval(0d, 100d).build();

    }

    /**
     * @return
     */
    public Kpi numberOfBuildBrokenPerUser() {

        return KpiBuilder.createAscending().nameAndKey("Broken builds")
                .description("Number of broken builds")
                .produceValue(ValueType.INT, ValueDirection.WORST)
                .providerType(ProviderType.CI_BUILD).entityType(EntityType.PERSON).dailyKPI()
                .interval(0d, 24 * 60 / 5d).query(NumberOfBrokenBuildPerUserPerDay.class).build();

    }

    // KPI : Durée maximale de bon fonctionnement (tps entre deux builds success average)
    // KPI : Durée de non-fonctionnement (moyenne)
    // KPI : Durée de non-fonctionnement (maximale)
    public Kpi numberOfBuildPerDay() {

        return KpiBuilder.createAscending().nameAndKey("Builds").description("Number of builds")
                .produceValue(ValueType.INT, ValueDirection.NONE)
                .providerType(ProviderType.CI_BUILD).entityType(EntityType.PROJECT)
                .query(BuildPerDay.class).dailyKPI().build();

    }

    /**
     * @return
     */
    public Kpi numberOfConfigurationChanges() {

        return KpiBuilder.createAscending().nameAndKey("Job conf changes")
                .description("Number of job configuration change")
                .produceValue(ValueType.INT, ValueDirection.NONE)
                .providerType(ProviderType.CI_BUILD).entityType(EntityType.PROJECT).dailyKPI()
                .interval(0d, 60d).query(NumberOfJobConfigurationChangesPerWeek.class).build();
    }

    public Kpi numberOfFailedBuildPerDay() {

        return KpiBuilder.createAscending().nameAndKey("Failed builds")
                .produceValue(ValueType.INT, ValueDirection.WORST)
                .description("Number of failed builds").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).dailyKPI().query(NumberOfFailedBuildPerDay.class)
                .interval(0d, 24 * 60 / 5d).build();
    }

    /**
     * @return
     */
    public Kpi numberOfFixedBuildPerUser() {

        return KpiBuilder.createAscending().nameAndKey("Fixed builds")
                .produceValue(ValueType.INT, ValueDirection.BETTER)
                .description("Number of fixed builds").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PERSON).dailyKPI()
                .query(NumberOfFixedBuildPerUserPerDay.class).build();

    }

    /**
     * @return
     */
    public Kpi numberOfUnstableBuildPerDay() {

        return KpiBuilder.createAscending().nameAndKey("Unstable builds")
                .produceValue(ValueType.INT, ValueDirection.WORST)
                .description("Number of unstable builds").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).dailyKPI().query(NumberOfUnstableBuildPerDay.class)
                .interval(0d, 24 * 60 / 5d).build();
    }

    public Kpi numberSuccessBuildPerDay() {

        return KpiBuilder.createAscending().nameAndKey("Successful builds")
                .produceValue(ValueType.INT, ValueDirection.BETTER)
                .description("Number of successful builds").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).dailyKPI().interval(0d, 24 * 60 / 5d)
                .query(NumberOfSuccessfulBuildPerDay.class).build();
    }

    public Kpi numberOfInterruptedBuildPerDay() {

        return KpiBuilder.createAscending().nameAndKey("Interrupted builds")
                .produceValue(ValueType.INT, ValueDirection.WORST)
                .description("Number of interrupted builds").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).dailyKPI().interval(0d, 24 * 60 / 5d)
                .query(NumberOfInterruptedBuildPerDay.class).build();
    }

    /**
     * @param _numberSuccessBuildPerDay
     */
    public void saveOrUpdate(final Kpi _kpi) {

        standardKpiBuilderService.saveOrUpdate(_kpi);
    }
}
