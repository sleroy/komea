/**
 *
 */
package org.komea.product.backend.service.standardkpi;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.eventory.query.CEPQuery;
import org.komea.product.backend.groovy.GroovyEngineService;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.kpi.standard.sonar.SonarMetricKpi;

/**
 * @author sleroy
 */
public class SonarKpiServiceTest {

    private final SonarKPIService standardKpiBuilderService = new SonarKPIService();

    private final GroovyEngineService groovyEngineService = new GroovyEngineService();

    @Before
    public void before() {
        groovyEngineService.init();

    }

    @After
    public void destroy() {
        groovyEngineService.destroy();

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.standardkpi.StandardKpiService#actualLineCoverage()}
     * .
     */
    @Test
    public void testActualLineCoverage() throws Exception {

        testKpi(standardKpiBuilderService.actualLineCoverage());

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.standardkpi.StandardKpiService#actualLineCoverage()}
     * .
     */
    @Test
    public void testActualLineCoverageInvokcation() throws Exception {

        new CEPQuery(new SonarMetricKpi("ncloc"));

    }

    @Test
    public void testBuildSonarMetricKpi() throws Exception {

        testKpi(standardKpiBuilderService.buildSonarMetricKpi("Lines of code", "ncloc", 0, 100, ValueType.INT, ValueDirection.NONE));
    }

    private void testKpi(final Kpi kpiToTest) {

        final Set<ConstraintViolation<Kpi>> validate = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(kpiToTest);
        Assert.assertTrue(groovyEngineService.isValidFormula(kpiToTest.getEsperRequest()));
        Assert.assertTrue(validate.isEmpty());
    }
}
