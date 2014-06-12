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
import org.komea.product.plugins.kpi.standard.jenkins.NumberOfSuccessfulBuildPerDay;
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
    
    
    
    @PostConstruct
    public void initJenkinsKPI() {
    
    
        saveOrUpdate(numberSuccessBuildPerDay());

    }


    // KPI : Durée maximale de bon fonctionnement (tps entre deux builds success average)
    // KPI : Durée de non-fonctionnement (moyenne)
    // KPI : Durée de non-fonctionnement (maximale)


    public Kpi numberSuccessBuildPerDay() {
    
    
        return KpiBuilder.createAscending().nameAndKey("Successful builds")
                .produceValue(ValueType.INT, ValueDirection.BETTER)
                .description("Number of successful builds").providerType(ProviderType.CI_BUILD)
                .entityType(EntityType.PROJECT).interval(0d, 100d)
                .query(NumberOfSuccessfulBuildPerDay.class).build();
    }
    
    
    /**
     * @param _numberSuccessBuildPerDay
     */
    public void saveOrUpdate(final Kpi _kpi) {
    
    
        standardKpiBuilderService.saveOrUpdate(_kpi);
    }
}
