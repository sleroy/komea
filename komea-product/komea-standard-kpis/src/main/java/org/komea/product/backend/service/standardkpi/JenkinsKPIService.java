/**
 * 
 */

package org.komea.product.backend.service.standardkpi;



import javax.annotation.PostConstruct;

import org.komea.product.backend.api.standardkpi.IStandardKpiService;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.kpi.standard.jenkins.BuildPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfBrokenBuildPerUserPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfFailedBuildPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfFixedBuildPerUserPerDay;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfJobConfigurationChangesPerWeek;
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfSuccessfulBuildPerDay;
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
public class JenkinsKPIService
{
    
    
    @Autowired
    public IStandardKpiService standardKpiBuilderService;
    
    
    
    /**
     * @return
     */
    public Kpi healthRateOfUserActions() {
    
    
        return KpiBuilder.createAscending().nameAndKey("PHID")
                .description("Project Health influence of a developer")
                .providerType(ProviderType.CI_BUILD).entityType(EntityType.PERSON).dailyKPI()
                .interval(-100d, 100d).query(ProjectBuildHealthInfluencePerUser.class).build();
        
    }
    
    
    @PostConstruct
    public void initJenkinsKPI() {
    
    
        saveOrUpdate(numberOfConfigurationChanges());
        saveOrUpdate(numberSuccessBuildPerDay());
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
    
    
        return KpiBuilder.createAscending().nameAndKey("SBRJW")
                .description("Success project build rate").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).dailyKPI().query(SuccessfulBuildRate.class)
                .interval(0d, 100d).build();
        
    }
    
    
    /**
     * @return
     */
    public Kpi numberOfBuildBrokenPerUser() {
    
    
        return KpiBuilder.createAscending().nameAndKey("NBBPD")
                .description("Number of broken builds per user ")
                .providerType(ProviderType.CI_BUILD).entityType(EntityType.PROJECT).dailyKPI()
                .interval(0d, 24 * 60 / 5d).query(NumberOfBrokenBuildPerUserPerDay.class).build();
        
    }
    
    
    // KPI : Durée maximale de bon fonctionnement (tps entre deux builds success average)
    // KPI : Durée de non-fonctionnement (moyenne)
    // KPI : Durée de non-fonctionnement (maximale)
    public Kpi numberOfBuildPerDay() {
    
    
        return KpiBuilder.createAscending().nameAndKey("NBPD").description("Number of build ")
                .providerType(ProviderType.CI_BUILD).entityType(EntityType.PROJECT)
                .query(BuildPerDay.class).dailyKPI().build();
        
    }
    
    
    /**
     * @return
     */
    public Kpi numberOfConfigurationChanges() {
    
    
        return KpiBuilder.createAscending().nameAndKey("NJCC")
                .description("Number of job configuration change")
                .providerType(ProviderType.CI_BUILD).entityType(EntityType.PROJECT).dailyKPI()
                .interval(0d, 60d).query(NumberOfJobConfigurationChangesPerWeek.class).build();
    }
    
    
    public Kpi numberOfFailedBuildPerDay() {
    
    
        return KpiBuilder.createAscending().nameAndKey("NFBPD")
                .description("Number of failed build ").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).dailyKPI().query(NumberOfFailedBuildPerDay.class)
                .interval(0d, 24 * 60 / 5d).build();
    }
    
    
    /**
     * @return
     */
    public Kpi numberOfFixedBuildPerUser() {
    
    
        return KpiBuilder.createAscending().nameAndKey("NFBM")
                .description("Number of fixed builds").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PERSON).dailyKPI()
                .query(NumberOfFixedBuildPerUserPerDay.class).build();
        
    }
    
    
    /**
     * @return
     */
    public Kpi numberOfUnstableBuildPerDay() {
    
    
        return KpiBuilder.createAscending().nameAndKey("NUBPD")
                .description("Number of unstable build").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).dailyKPI().query(NumberOfFailedBuildPerDay.class)
                .interval(0d, 24 * 60 / 5d).build();
    }
    
    
    public Kpi numberSuccessBuildPerDay() {
    
    
        return KpiBuilder.createAscending().nameAndKey("NSBPD")
                .description("Number of successful build ").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).dailyKPI().interval(0d, 24 * 60 / 5d)
                .query(NumberOfSuccessfulBuildPerDay.class).build();
    }
    
    
    /**
     * @param _numberSuccessBuildPerDay
     */
    public void saveOrUpdate(final Kpi _kpi) {
    
    
        standardKpiBuilderService.saveOrUpdate(_kpi);
    }
}
