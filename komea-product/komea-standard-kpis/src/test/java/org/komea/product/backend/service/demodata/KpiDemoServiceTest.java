/**
 * 
 */

package org.komea.product.backend.service.demodata;



import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.formula.ElFormula;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public class KpiDemoServiceTest
{
    
    
    private final KpiDemoService kpiDemoService = new KpiDemoService();
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#actualLineCoverage()}.
     */
    @Test @Ignore
    public void testActualLineCoverage() throws Exception {
    
    
        testKpi(kpiDemoService.actualLineCoverage());
        
    }
    
    
    @Test @Ignore
    public void testBuildSonarMetricKpi() throws Exception {
    
    
        testKpi(kpiDemoService.buildSonarMetricKpi("Number of lines of code", "ncloc"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberBuildPerDay()}.
     */
    @Test @Ignore
    public void testNumberBuildPerDay() throws Exception {
    
    
        testKpi(kpiDemoService.numberBuildPerDay());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberBuildPerMonth()}.
     */
    @Test @Ignore
    public void testNumberBuildPerMonth() throws Exception {
    
    
        testKpi(kpiDemoService.numberBuildPerMonth());
    }
    
    
    @Test @Ignore
    public void testNumberOfBuildBrokenPerUser() throws Exception {
    
    
        testKpi(kpiDemoService.numberOfBuildBrokenPerUser());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerDay()}.
     */
    @Test @Ignore
    public void testNumberSuccessBuildPerDay() throws Exception {
    
    
        testKpi(kpiDemoService.numberSuccessBuildPerDay());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerWeek()}.
     */
    @Test @Ignore
    public void testNumberSuccessBuildPerWeek() throws Exception {
    
    
        testKpi(kpiDemoService.numberSuccessBuildPerWeek());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#successRateJenkinsPerMonthKpi()}.
     */
    @Test @Ignore
    public void testSuccessRateJenkinsPerMonthKpi() throws Exception {
    
    
        testKpi(kpiDemoService.successRateJenkinsPerMonthKpi());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#successRateJenkinsPerWeekKpi()}.
     */
    @Test @Ignore
    public void testSuccessRateJenkinsPerWeekKpi() throws Exception {
    
    
        testKpi(kpiDemoService.successRateJenkinsPerWeekKpi());
    }
    
    
    private void testKpi(final Kpi kpiToTest) {
    
    
        final Set<ConstraintViolation<Kpi>> validate =
                Validation.buildDefaultValidatorFactory().getValidator().validate(kpiToTest);
        Assert.assertNotNull(new ElFormula<ICEPQueryImplementation>(kpiToTest.getEsperRequest(),
                ICEPQueryImplementation.class));
        Assert.assertTrue(validate.isEmpty());
    }
    
}
