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
        return buildSonarMetricKpi("Line coverage", "line_coverage", 0, 100);
    }
    
    
    public Kpi buildSonarMetricKpi(
            final String _title,
            final String _metricName,
            final double _min,
            final double _max) {
    
    
        return KpiBuilder.createAscending().nameAndKeyDescription(_title).dailyKPI()
                .entityType(EntityType.PROJECT).providerType(ProviderType.QUALITY)
                .interval(_min, _max)
                .query("new " + SonarMetricKpi.class.getName() + "('" + _metricName + "')").build();
    }
    
    
    @PostConstruct
    public void initSonarKPI() {
    
    
        saveOrUpdate(actualLineCoverage());
        saveOrUpdate(buildSonarMetricKpi("Lines of Code", "ncloc", 0, 2000000));
        saveOrUpdate(buildSonarMetricKpi("Classes", "classes", 0, 20000));
        saveOrUpdate(buildSonarMetricKpi("Files", "files", 0, 50000));
        saveOrUpdate(buildSonarMetricKpi("Packages", "packages", 0, 5000));
        saveOrUpdate(buildSonarMetricKpi("Methods", "functions", 0, 50000));
        saveOrUpdate(buildSonarMetricKpi("Comments (%)", "comment_lines_density", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Public documented API (%)",
                "public_documented_api_density", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Complexity /file", "file_complexity", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Complexity /class", "class_complexity", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Unit tests", "tests", 0, 50000));
        saveOrUpdate(buildSonarMetricKpi("Unit tests errors", "test_errors", 0, 50000));
        saveOrUpdate(buildSonarMetricKpi("Skipped unit tests", "skipped_tests", 0, 50000));
        saveOrUpdate(buildSonarMetricKpi("Unit tests failures", "tests_failures", 0, 50000));
        saveOrUpdate(buildSonarMetricKpi("Unit tests success (%)", "test_success_density", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Branch coverage", "branch_coverage", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("IT branch coverage", "it_branch_coverage", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Branch coverage on new code", "new_branch_coverage", 0,
                100));
        saveOrUpdate(buildSonarMetricKpi("Coverage on new code", "new_coverage", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Duplicated lines (%)", "duplicated_lines_density", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Rules compliance", "violations_density", 0, 100));
        saveOrUpdate(buildSonarMetricKpi("Blocker issues", "blocker_violations", 0, 100000));
        saveOrUpdate(buildSonarMetricKpi("New Blocker issues", "new_blocker_violations", 0, 100000));
        saveOrUpdate(buildSonarMetricKpi("New issues", "new_violations", 0, 100000));
        
        
    }
    
    
    /**
     * @param _numberSuccessBuildPerDay
     */
    private void saveOrUpdate(final Kpi _kpi) {
    
    
        standardKpiBuilderService.saveOrUpdate(_kpi);
    }
    
}
