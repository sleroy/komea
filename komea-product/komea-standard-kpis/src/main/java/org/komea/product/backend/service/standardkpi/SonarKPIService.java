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
import org.komea.product.plugins.kpi.standard.sonar.SonarMetricKpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
@Transactional
public class SonarKPIService
{
    
    
    @Autowired
    private IStandardKpiService standardKpiBuilderService;
    
    
    
    public Kpi actualLineCoverage() {
    
    
        // metric_value
        // sonar
        // metricName
        return buildSonarMetricKpi("Line coverage", "line_coverage");
    }
    
    
    public Kpi buildSonarMetricKpi(final String _title, final String _metricName) {
    
    
        return KpiBuilder.createAscending().nameAndKeyDescription(_title).dailyKPI()
                .entityType(EntityType.PROJECT).providerType(ProviderType.QUALITY)
                .query("new " + SonarMetricKpi.class.getName() + "('" + _metricName + "')").build();
    }
    
    
    @PostConstruct
    public void initSonarKPI() {
    
    
        saveOrUpdate(actualLineCoverage());
        saveOrUpdate(buildSonarMetricKpi("Lines of Code", "ncloc"));
        saveOrUpdate(buildSonarMetricKpi("Classes", "classes"));
        saveOrUpdate(buildSonarMetricKpi("Files", "files"));
        saveOrUpdate(buildSonarMetricKpi("Packages", "packages"));
        saveOrUpdate(buildSonarMetricKpi("Methods", "functions"));
        saveOrUpdate(buildSonarMetricKpi("Comments (%)", "comment_lines_density"));
        saveOrUpdate(buildSonarMetricKpi("Public documented API (%)",
                "public_documented_api_density"));
        saveOrUpdate(buildSonarMetricKpi("Complexity /file", "file_complexity"));
        saveOrUpdate(buildSonarMetricKpi("Complexity /class", "class_complexity"));
        saveOrUpdate(buildSonarMetricKpi("Unit tests", "tests"));
        saveOrUpdate(buildSonarMetricKpi("Unit tests errors", "test_errors"));
        saveOrUpdate(buildSonarMetricKpi("Skipped unit tests", "skipped_tests"));
        saveOrUpdate(buildSonarMetricKpi("Unit tests failures", "tests_failures"));
        saveOrUpdate(buildSonarMetricKpi("Unit tests success (%)", "test_success_density"));
        saveOrUpdate(buildSonarMetricKpi("Branch coverage", "branch_coverage"));
        saveOrUpdate(buildSonarMetricKpi("IT branch coverage", "it_branch_coverage"));
        saveOrUpdate(buildSonarMetricKpi("Branch coverage on new code", "new_branch_coverage"));
        saveOrUpdate(buildSonarMetricKpi("Coverage on new code", "new_coverage"));
        saveOrUpdate(buildSonarMetricKpi("Duplicated lines (%)", "duplicated_lines_density"));
        saveOrUpdate(buildSonarMetricKpi("Rules compliance", "violations_density"));
        saveOrUpdate(buildSonarMetricKpi("Blocker issues", "blocker_violations"));
        saveOrUpdate(buildSonarMetricKpi("New Blocker issues", "new_blocker_violations"));
        saveOrUpdate(buildSonarMetricKpi("New issues", "new_violations"));
        
        
    }
    
    
    /**
     * @param _numberSuccessBuildPerDay
     */
    private void saveOrUpdate(final Kpi _kpi) {
    
    
        standardKpiBuilderService.saveOrUpdate(_kpi);
    }
    
}
