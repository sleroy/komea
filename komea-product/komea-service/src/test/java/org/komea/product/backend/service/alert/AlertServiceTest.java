/**
 *
 */

package org.komea.product.backend.service.alert;



import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.AlertCriteria;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class AlertServiceTest
{
    
    
    @InjectMocks
    private final IAlertService alertService = new AlertService();
    
    @Mock
    private IAlertTypeService   alertTypeService;
    
    @Mock
    private IEntityService      entityService;
    
    @Mock
    private IKPIService         kpiService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#isAlertFiltered(org.komea.product.database.dto.SearchKpiAlertsDto, org.komea.product.database.dto.KpiAlertDto)}
     * .
     */
    @Test
    public void testIs_activeted_Alert_COVERAGE_BRANCH_TOO_LOW_filtered() throws Exception {
    
    
        final SearchKpiAlertsDto filter = new SearchKpiAlertsDto();
        filter.setActivatedOnly(true);
        filter.addEntityKey("KOMEA");
        filter.setExtendedEntityType(ExtendedEntityType.PROJECT);
        filter.addAlertKey("COVERAGE_BRANCH_TOO_LOW");
        
        final KpiAlertDto kpiAlert = new KpiAlertDto();
        kpiAlert.setActivated(true);
        
        final boolean isFiltered = alertService.isAlertFiltered(filter, kpiAlert);
        
        org.junit.Assert.assertTrue("The alert should be filtered ", isFiltered);
        
    }
    
    
    @Test
    public void testIs_not_activeted_Alert_COVERAGE_BRANCH_TOO_LOW_filtered() throws Exception {
    
    
        final SearchKpiAlertsDto filter = new SearchKpiAlertsDto();
        filter.setActivatedOnly(true);
        filter.addEntityKey("KOMEA");
        filter.setExtendedEntityType(ExtendedEntityType.PROJECT);
        filter.addAlertKey("COVERAGE_BRANCH_TOO_LOW");
        
        final KpiAlertDto kpiAlert = new KpiAlertDto();
        kpiAlert.setActivated(false);
        
        final boolean isFiltered = alertService.isAlertFiltered(filter, kpiAlert);
        
        org.junit.Assert.assertFalse("The alert should not be filtered ", isFiltered);
        
    }
    
    
    @Test
    public void testIs_not_activeted_Alert_COVERAGE_BRANCH_TOO_LOW_with_no_activated_filtered()
            throws Exception {
    
    
        final SearchKpiAlertsDto filter = new SearchKpiAlertsDto();
        filter.setActivatedOnly(false);
        filter.addEntityKey("KOMEA");
        filter.setExtendedEntityType(ExtendedEntityType.PROJECT);
        filter.addAlertKey("COVERAGE_BRANCH_TOO_LOW");
        
        final KpiAlertDto kpiAlert = new KpiAlertDto();
        kpiAlert.setActivated(false);
        
        final boolean isFiltered = alertService.isAlertFiltered(filter, kpiAlert);
        
        org.junit.Assert.assertTrue("The alert should be filtered ", isFiltered);
        
    }
    
    
    @Test
    public final void testIsAlert_definition_not_activated() throws Exception {
    
    
        // GIVEN an alert is launch when branch coverage is under 60%
        final KpiAlertType alertType = new KpiAlertType();
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
        final int coverage = 5;
        
        // WHEN we test if the alert is activated
        final boolean activated = alertService.isAlertActivated(alertType, coverage);
        
        // The alert must be activate
        Assert.assertFalse(activated);
    }
    
    
    @Test
    public final void testIsAlert_not_activated() throws Exception {
    
    
        // GIVEN an alert is launch when branch coverage is under 60%
        final KpiAlertType alertType = new KpiAlertType();
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
        final int coverage = 65;
        
        // WHEN we test if the alert is activated
        final boolean activated = alertService.isAlertActivated(alertType, coverage);
        
        // The alert must be activate
        Assert.assertFalse(activated);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#isAlertActivated(org.komea.product.database.model.KpiAlertType, java.lang.Number)}
     * .
     */
    @Test
    public final void testIsAlertActivated() throws Exception {
    
    
        // GIVEN an alert is launch when branch coverage is under 60%
        final KpiAlertType alertType = new KpiAlertType();
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
        final int coverage = 5;
        
        // WHEN we test if the alert is activated
        final boolean activated = alertService.isAlertActivated(alertType, coverage);
        
        // The alert must be activate
        Assert.assertTrue(activated);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#isAlertAssociatedToMeasureEntity(org.komea.product.backend.service.alert.AlertCriteria, org.komea.product.database.model.Measure)}
     * .
     */
    @Test
    @Ignore
    public void testIsAlertAssociatedToMeasureEntity() throws Exception {
    
    
        // GIVEN the alert COVERAGE_BRANCH_TOO_LOW
        final KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);
        
        // AND the project Komea
        final BaseEntityDto entity = new BaseEntityDto();
        entity.setId(1);
        entity.setEntityType(EntityType.PROJECT);
        entity.setDescription("KPI manager");
        entity.setName("Komea");
        
        // AND AlertCriteria to find on the project komea alerts of type COVERAGE_BRANCH_TOO_LOW
        final List<Measure> measures = Lists.newArrayList();
        final AlertCriteria criteria =
                new AlertCriteria(alertType, entity, EntityType.PROJECT, measures);
        
        // AND a measure on theKomea project
        final Measure measure = new Measure();
        measure.setEntityID(1);
        measure.setValue(25D);
        
        // WHEN I check if the mieasure corrspond to the alertCriterai
        final boolean isAssociate = alertService.isMeasureAssociatedToAlert(criteria, measure);
        
        // THEN the result must be true
        org.junit.Assert.assertTrue(
                "The alert COVERAGE_BRANCH_TOO_LOW should be associate to the project Komea ",
                isAssociate);
    }
}
