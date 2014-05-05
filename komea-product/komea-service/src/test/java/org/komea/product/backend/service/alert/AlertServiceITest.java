/**
 * 
 */

package org.komea.product.backend.service.alert;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
public class AlertServiceITest extends AbstractSpringDBunitIntegrationTest {
    
    @Autowired
    private IAlertService ialertService;
    
    private AlertService  alertService;
    
    @Before
    public void setUp() {
    
        alertService = (AlertService) ialertService;
    }
    
    // @Mock
    // private IAlertTypeService alertTypeService;
    //
    // @Mock
    // private IEntityService entityService;
    //
    // @Mock
    // private IKPIService kpiService;
    // @Mock
    // private IHistoryService measureService;
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#findAlert(org.komea.product.database.enums.EntityType, org.komea.product.database.dto.BaseEntityDto, org.komea.product.database.model.KpiAlertType, java.util.List, java.util.Map)}
     * .
     */
    @Test
    public final void testFindAlert() throws Exception {
    
        // TODO
        
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#findAlerts(org.komea.product.database.dto.SearchKpiAlertsDto)}.
     */
    @Test
    public final void testFindAlerts() throws Exception {
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#findMeasure(org.komea.product.database.enums.EntityType, org.komea.product.database.dto.BaseEntityDto, org.komea.product.database.model.KpiAlertType, java.util.List)}
     * .
     */
    @Test
    public final void testFindMeasure() throws Exception {
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#isAlertActivated(org.komea.product.database.model.KpiAlertType, java.lang.Number)}
     * .
     */
    @Test
    public final void testIsAlertActivated() throws Exception {
    
        // GIVEN an alert is launch when branch coverage is under 60%
        KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);
        // AND the alert definition is activated
        alertType.setEnabled(true);
        // AND The current coverage is 5
        int coverage = 5;
        
        // WHEN we test if the alert is activated
        boolean activated = alertService.isAlertActivated(alertType, coverage);
        
        // The alert must be activate
        Assert.assertTrue(activated);
    }
    
    @Test
    public final void testIsAlert_not_activated() throws Exception {
    
        // GIVEN an alert is launch when branch coverage is under 60%
        KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);
        // AND the alert definition is activated
        alertType.setEnabled(true);
        // AND The current coverage is 65
        int coverage = 65;
        
        // WHEN we test if the alert is activated
        boolean activated = alertService.isAlertActivated(alertType, coverage);
        
        // The alert must be activate
        Assert.assertFalse(activated);
    }
    
    @Test
    public final void testIsAlert_definition_not_activated() throws Exception {
    
        // GIVEN an alert is launch when branch coverage is under 60%
        KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);
        // AND the alert definition is not activated
        alertType.setEnabled(false);
        // AND The current coverage is 5
        int coverage = 5;
        
        // WHEN we test if the alert is activated
        boolean activated = alertService.isAlertActivated(alertType, coverage);
        
        // The alert must be activate
        Assert.assertFalse(activated);
    }
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#isAlertAssociatedToMeasureEntity(org.komea.product.backend.service.alert.AlertCriteria, org.komea.product.database.model.Measure)}
     * .
     */
    @Test
    public void testIsAlertAssociatedToMeasureEntity() throws Exception {
    
        // alertService.
    }
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#isAlertFiltered(org.komea.product.database.dto.SearchKpiAlertsDto, org.komea.product.database.dto.KpiAlertDto)}
     * .
     */
    @Test
    public void testIsAlertFiltered() throws Exception {
    
        org.junit.Assert.assertTrue("not yet implemented", false);
        
    }
}
