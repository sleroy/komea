
package org.komea.product.backend.service.alert;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.backend.utils.exemples.kpi.BranchCoverageKPI;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.Measure;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class AlertFinderTest {
    
    @Mock
    private IAlertService            alertSerice;
    
    @Mock
    private IMeasureService          measureService;
    
    @InjectMocks
    private final AlertFinderService finderSerice = new AlertFinderService();
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertService#findAlert(org.komea.product.database.enums.EntityType, org.komea.product.database.dto.BaseEntityDto, org.komea.product.database.model.KpiAlertType, java.util.List, java.util.Map)}
     * .
     */
    @Test
    public final void testFindAlert() throws Exception {
    
        // GIVEN the kpi COVERAGE_BRANCH
        Kpi kpi = KpiBuilder.create().key("COVERAGE_BRANCH").name("Branch coverage").description("Give the branch coverage")
                .groupFormula(GroupFormula.AVG_VALUE).dynamicQuery(BranchCoverageKPI.class).entityType(EntityType.PROJECT)
                .providerType(ProviderType.QUALITY).forProject().produceValue(ValueType.FLOAT, ValueDirection.BETTER).build();
        
        // AND the alert COVERAGE_BRANCH_TOO_LOW
        final KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);
        
        // AND the alert is activated with a value of 35
        Mockito.when(alertSerice.isAlertActivated(alertType, 35)).thenReturn(true);
        
        // AND the project Komea
        BaseEntityDto entity = new BaseEntityDto();
        entity.setId(1);
        entity.setEntityType(EntityType.PROJECT);
        entity.setDescription("KPI manager");
        entity.setName("Komea");
        
        // AND AlertCriteria to find on the project komea alerts of type COVERAGE_BRANCH_TOO_LOW
        List<Measure> measures = Lists.newArrayList();
        Measure measure = new Measure();
        measure.setIdKpi("1");
        measure.setEntityID(1);
        measure.setValue(36D);
        measures.add(measure);
        
        // AND the current branch coverage for the komea project is 36
        Mockito.when(measureService.currentMeasure(kpi, entity)).thenReturn(36D);
        
        // WKEN I want to knwo if the komea project as a branch coverage to low
        KpiAlertDto alert = finderSerice.findAlert(alertType, entity, kpi);
        
        // THEN an alert is launched because the current branch cooverage is 36 < 60
        org.junit.Assert.assertEquals("the result must be 36D", 36D, alert.getValue(), 0.001);
    }
    
    @Test
    public final void testFindAlert_no_alert() throws Exception {
    
        // GIVEN the kpi COVERAGE_BRANCH
        Kpi kpi = KpiBuilder.create().key("COVERAGE_BRANCH").name("Branch coverage").description("Give the branch coverage")
                .groupFormula(GroupFormula.AVG_VALUE).dynamicQuery(BranchCoverageKPI.class).entityType(EntityType.PROJECT)
                .providerType(ProviderType.QUALITY).forProject().produceValue(ValueType.FLOAT, ValueDirection.BETTER).build();
        
        // AND the alert COVERAGE_BRANCH_TOO_LOW
        final KpiAlertType alertType = new KpiAlertType();
        alertType.setKpiAlertKey("COVERAGE_BRANCH_TOO_LOW");
        alertType.setIdKpi(1);
        alertType.setId(1);
        alertType.setOperator(Operator.LESSER);
        alertType.setValue(60D);
        alertType.setName("branch coverage too small");
        alertType.setDescription("An alert is launched when the Branche coverage is < 60");
        alertType.setSeverity(Severity.CRITICAL);
        
        // AND the alert is activated with a value of 70
        Mockito.when(alertSerice.isAlertActivated(alertType, 70)).thenReturn(false);
        
        // AND the project Komea
        BaseEntityDto entity = new BaseEntityDto();
        entity.setId(1);
        entity.setEntityType(EntityType.PROJECT);
        entity.setDescription("KPI manager");
        entity.setName("Komea");
        
        // AND AlertCriteria to find on the project komea alerts of type COVERAGE_BRANCH_TOO_LOW
        List<Measure> measures = Lists.newArrayList();
        Measure measure = new Measure();
        measure.setIdKpi("1");
        measure.setEntityID(1);
        measure.setValue(36D);
        measures.add(measure);
        
        // AND the current branch coverage for the komea project is 36
        Mockito.when(measureService.currentMeasure(kpi, entity)).thenReturn(70D);
        
        // WKEN I want to knwo if the komea project as a branch coverage to low
        KpiAlertDto alert = finderSerice.findAlert(alertType, entity, kpi);
        
        // THEN an alert is not launched because the current branch cooverage is 70 > 60
        org.junit.Assert.assertEquals("the result must be 70D", 70D, alert.getValue(), 0.001);
        org.junit.Assert.assertFalse("the alert must not be activated", alert.isActivated());
        
    }
    
}
