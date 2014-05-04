/**
 * 
 */

package org.komea.product.backend.service.alert;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IHistoryService;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.entities.IEntityService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class AlertServiceTest
{
    
    
    @InjectMocks
    private AlertService      alertService;
    
    
    @Mock
    private IAlertTypeService alertTypeService;
    
    
    @Mock
    private IEntityService    entityService;
    
    
    @Mock
    private IKPIService       kpiService;
    @Mock
    private IHistoryService   measureService;
    
    
    
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
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
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
    
    
        // alertService.isAlertFiltered(new SearchKpiAlertsDto(), new KpiAlertDto());
        
    }
}
