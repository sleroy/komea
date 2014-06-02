/**
 *
 */
package org.komea.product.backend.service.standardkpi;

import javax.annotation.PostConstruct;
import org.komea.product.backend.api.standardkpi.IStandardKpiService;
import org.komea.product.backend.service.kpi.GroovyScriptLoader;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
@Service
@Transactional
public class SonarKPIService {

    @Autowired
    private IStandardKpiService standardKpiBuilderService;

    public Kpi actualLineCoverage() {

        // metric_value
        // sonar
        // metricName
        return buildSonarMetricKpi("Line coverage", "line_coverage", 0, 100, ValueType.PERCENT, ValueDirection.BETTER);
    }

    public Kpi buildSonarMetricKpi(
            final String _name,
            final String _key,
            final double _min,
            final double _max,
            final ValueType _valueType, final ValueDirection _valueDirection) {

        final GroovyScriptLoader groovyScriptLoader
                = new GroovyScriptLoader(Thread.currentThread().getContextClassLoader(),
                        "org/komea/product/backend/service/standardkpi/SonarMetricScript.groovy");
        groovyScriptLoader.addParameter("##metric##", _key);

        return KpiBuilder.createAscending().nameAndKeyDescription(_name).dailyKPI()
                .entityType(EntityType.PROJECT).providerType(ProviderType.QUALITY)
                .interval(_min, _max).produceValue(_valueType, _valueDirection)
                .key(_key).queryScript(groovyScriptLoader.load()).build();
    }

    @PostConstruct
    public void initSonarKPI() {

        saveOrUpdate(actualLineCoverage());
        saveOrUpdate(buildSonarMetricKpi("Lines of Code", "ncloc", 0, 1000000, ValueType.INT, ValueDirection.NONE));
        saveOrUpdate(buildSonarMetricKpi("Classes", "classes", 0, 10000, ValueType.INT, ValueDirection.NONE));
        saveOrUpdate(buildSonarMetricKpi("Files", "files", 0, 10000, ValueType.INT, ValueDirection.NONE));
        saveOrUpdate(buildSonarMetricKpi("Packages", "packages", 0, 1000, ValueType.INT, ValueDirection.NONE));
        saveOrUpdate(buildSonarMetricKpi("Methods", "functions", 0, 100000, ValueType.INT, ValueDirection.NONE));
        saveOrUpdate(buildSonarMetricKpi("Comments (%)", "comment_lines_density", 0, 100, ValueType.PERCENT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("Public documented API (%)",
                "public_documented_api_density", 0, 100, ValueType.PERCENT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("Complexity /file", "file_complexity", 0, 100, ValueType.FLOAT, ValueDirection.WORST));
        saveOrUpdate(buildSonarMetricKpi("Complexity /class", "class_complexity", 0, 100, ValueType.FLOAT, ValueDirection.WORST));
        saveOrUpdate(buildSonarMetricKpi("Unit tests", "tests", 0, 1000, ValueType.INT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("Unit tests errors", "test_errors", 0, 1000, ValueType.INT, ValueDirection.WORST));
        saveOrUpdate(buildSonarMetricKpi("Skipped unit tests", "skipped_tests", 0, 1000, ValueType.INT, ValueDirection.WORST));
        saveOrUpdate(buildSonarMetricKpi("Unit tests success (%)", "test_success_density", 0, 100, ValueType.PERCENT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("Branch coverage", "branch_coverage", 0, 100, ValueType.PERCENT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("IT branch coverage", "it_branch_coverage", 0, 100, ValueType.PERCENT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("Branch coverage on new code", "new_branch_coverage", 0,
                100, ValueType.PERCENT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("Coverage on new code", "new_coverage", 0, 100, ValueType.PERCENT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("Duplicated lines (%)", "duplicated_lines_density", 0, 100, ValueType.PERCENT, ValueDirection.WORST));
        saveOrUpdate(buildSonarMetricKpi("Rules compliance", "violations_density", 0, 100, ValueType.PERCENT, ValueDirection.BETTER));
        saveOrUpdate(buildSonarMetricKpi("Blocker issues", "blocker_violations", 0, 100000, ValueType.INT, ValueDirection.WORST));
        saveOrUpdate(buildSonarMetricKpi("New Blocker issues", "new_blocker_violations", 0, 100000, ValueType.INT, ValueDirection.WORST));
        saveOrUpdate(buildSonarMetricKpi("New issues", "new_violations", 0, 100000, ValueType.INT, ValueDirection.WORST));

    }

    /**
     * @param _numberSuccessBuildPerDay
     */
    private void saveOrUpdate(final Kpi _kpi) {

        standardKpiBuilderService.saveOrUpdate(_kpi);
    }

}
