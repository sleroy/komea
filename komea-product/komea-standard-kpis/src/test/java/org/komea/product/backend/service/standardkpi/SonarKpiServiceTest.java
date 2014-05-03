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
public class SonarKpiServiceTest
{
    
    
    private final SonarKPIService standardKpiBuilderService = new SonarKPIService();
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.standardkpi.JenkinsKpiService#actualLineCoverage()}.
     */
    @Test
    public void testActualLineCoverage() throws Exception {
    
    
        testKpi(standardKpiBuilderService.actualLineCoverage());
        
    }
    
    
    @Test
    public void testBuildSonarMetricKpi() throws Exception {
    
    
        testKpi(standardKpiBuilderService.buildSonarMetricKpi("Number of lines of code", "ncloc"));
    }
    
    
    private void testKpi(final Kpi kpiToTest) {
    
    
        final Set<ConstraintViolation<Kpi>> validate =
                Validation.buildDefaultValidatorFactory().getValidator().validate(kpiToTest);
        Assert.assertNotNull(new ElFormula<ICEPQueryImplementation>(kpiToTest.getEsperRequest(),
                ICEPQueryImplementation.class));
        Assert.assertTrue(validate.isEmpty());
    }
    
}
