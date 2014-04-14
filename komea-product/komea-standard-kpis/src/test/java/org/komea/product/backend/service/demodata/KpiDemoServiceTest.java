/**
 * 
 */

package org.komea.product.backend.service.demodata;



import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.formula.ElFormula;
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
    @Test 
    public void testActualLineCoverage() throws Exception {
    
    
        testKpi(kpiDemoService.actualLineCoverage());
        
    }
    
    
    @Test 
    public void testBuildSonarMetricKpi() throws Exception {
    
    
        testKpi(kpiDemoService.buildSonarMetricKpi("Number of lines of code", "ncloc"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberBuildPerDay()}.
     */
    @Test 
    public void testNumberBuildPerDay() throws Exception {
    
    
        testKpi(kpiDemoService.numberBuildPerDay());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberBuildPerMonth()}.
     */
    @Test 
    public void testNumberBuildPerMonth() throws Exception {
    
    
        testKpi(kpiDemoService.numberBuildPerMonth());
    }
    
    
    @Test 
    public void testNumberOfBuildBrokenPerUser() throws Exception {
    
    
        testKpi(kpiDemoService.numberOfBuildBrokenPerUser());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerDay()}.
     */
    @Test 
    public void testNumberSuccessBuildPerDay() throws Exception {
    
    
        testKpi(kpiDemoService.numberSuccessBuildPerDay());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerWeek()}.
     */
    @Test 
    public void testNumberSuccessBuildPerWeek() throws Exception {
    
    
        testKpi(kpiDemoService.numberSuccessBuildPerWeek());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#successRateJenkinsPerMonthKpi()}.
     */
    @Test 
    public void testSuccessRateJenkinsPerMonthKpi() throws Exception {
    
    
        testKpi(kpiDemoService.successRateJenkinsPerMonthKpi());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#successRateJenkinsPerWeekKpi()}.
     */
    @Test 
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
