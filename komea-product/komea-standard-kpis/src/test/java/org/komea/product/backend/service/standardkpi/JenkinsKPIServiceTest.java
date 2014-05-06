/**
 * 
 */

package org.komea.product.backend.service.standardkpi;



import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.cep.formula.ElFormula;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public class JenkinsKPIServiceTest
{
    
    
    private final JenkinsKPIService standardKpiBuilderService = new JenkinsKPIService();
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.JenkinsKPIService#healthRateOfUserActions()}.
     */
    @Test
    public void testHealthRateOfUserActions() throws Exception {
    
    
        testKpi(standardKpiBuilderService.healthRateOfUserActions());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.StandardKpiService#jenkinsSuccessBuildRate()}.
     */
    @Test
    public void testJenkinsSuccessBuildRate() throws Exception {
    
    
        testKpi(standardKpiBuilderService.jenkinsSuccessBuildRate());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.StandardKpiService#numberOfBuildPerDay()}.
     */
    @Test
    public void testNumberBuildPerDay() throws Exception {
    
    
        testKpi(standardKpiBuilderService.numberOfBuildPerDay());
    }
    
    
    @Test
    public void testNumberOfBuildBrokenPerUser() throws Exception {
    
    
        testKpi(standardKpiBuilderService.numberOfBuildBrokenPerUser());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.JenkinsKPIService#numberOfBuildPerDay()}.
     */
    @Test
    public void testNumberOfBuildPerDay() throws Exception {
    
    
        testKpi(standardKpiBuilderService.numberOfBuildPerDay());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.JenkinsKPIService#numberOfConfigurationChanges()}.
     */
    @Test
    public void testNumberOfConfigurationChanges() throws Exception {
    
    
        // throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.JenkinsKPIService#numberOfFailedBuildPerDay()}.
     */
    @Test
    public void testNumberOfFailedBuildPerDay() throws Exception {
    
    
        testKpi(standardKpiBuilderService.numberOfFailedBuildPerDay());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.JenkinsKPIService#numberOfUnstableBuildPerDay()}.
     */
    @Test
    public void testNumberOfUnstableBuildPerDay() throws Exception {
    
    
        // throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.StandardKpiService#numberSuccessBuildPerDay()}.
     */
    @Test
    public void testNumberSuccessBuildPerDay() throws Exception {
    
    
        testKpi(standardKpiBuilderService.numberSuccessBuildPerDay());
    }
    
    
    private void testKpi(final Kpi kpiToTest) {
    
    
        final Set<ConstraintViolation<Kpi>> validate =
                Validation.buildDefaultValidatorFactory().getValidator().validate(kpiToTest);
        Assert.assertNotNull(new ElFormula<ICEPQueryImplementation>(kpiToTest.getEsperRequest(),
                ICEPQueryImplementation.class));
        Assert.assertTrue(validate.isEmpty());
    }
    
}
