
package org.komea.product.backend.service.olap;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.KpiLoadingService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.MeasureResult;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.google.common.collect.Sets;

@DatabaseTearDown(value = "measures.xml", type = DatabaseOperation.DELETE_ALL)
public class GetCurrentMeasureStoryITest extends AbstractSpringDBunitIntegrationTest {
    
    //
    @Autowired
    private IMeasureService   measureService;
    
    @Autowired
    private KpiLoadingService kpiLoading;
    
    @Before
    public void setpUp() {
    
        kpiLoading.initLoadingService();
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_current_measure_for_kpi_branch_koverage_on_komea_project() {
    
        // Given the current branch coverage on the komea project value is 62
        // WHEN I ash for the current branch coverage on the komea project
        KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT, "KOMEA");
        double currentMeasure = measureService.currentMeasure(kpiKey);
        
        // Then the current branch coverage must be 35
        Assert.assertEquals(62, currentMeasure, 0.001);
    }
    
    @Test(expected = KPINotFoundRuntimeException.class)
    @DatabaseSetup("measures.xml")
    public void test_current_measure_for_not_exising_kpi_on_komea_project() {
    
        // WHEN I ask for a non existing kpi on the komea project
        KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("NOT_EXIST", EntityType.PROJECT, "KOMEA");
        double currentMeasure = measureService.currentMeasure(kpiKey);
        
        // Then the KPINotFoundRuntimeException exception must be launched
    }
    
    @Test(expected = EntityNotFoundException.class)
    @DatabaseSetup("measures.xml")
    public void test_current_measure_for_kpi_branch_koverage_on_not_exinsting_project() {
    
        // WHEN I ask for the current branch coverage on a non existing project project
        KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT, "NOT_EXIST");
        double currentMeasure = measureService.currentMeasure(kpiKey);
        
        // Then the EntityNotFoundException exception must be launched
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_current_measure_for_kpi_branch_koverage_on_komea_project_many() {
    
        // Given the current branch coverage on the komea project value is 62
        // WHEN I ash for the current branch coverage on the komea project
        
        KpiStringKeyList kpiKeys = new KpiStringKeyList(Sets.newHashSet("BRANCH_COVERAGE(%)"), Sets.newHashSet("KOMEA"), EntityType.PROJECT);
        double currentMeasure = measureService.currentMeasures(kpiKeys).get(0).getValue();
        
        // Then the current branch coverage must be 35
        Assert.assertEquals(62, currentMeasure, 0.001);
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_current_measure_no_existing_kpi_on_komea_project_many() {
    
        // WHEN I ask for a non existing kpi on the komea project
        KpiStringKeyList kpiKeys = new KpiStringKeyList(Sets.newHashSet("NOT_EXIST"), Sets.newHashSet("KOMEA"), EntityType.PROJECT);
        List<MeasureResult> measures = measureService.currentMeasures(kpiKeys);
        
        // Then the measures list must be empty
        Assert.assertTrue("Then the measures list must be empty", measures.isEmpty());
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_current_measure_for_kpi_branch_koverage_on_not_exinsting_project_many() {
    
        // WHEN I ask for a the branch coverage kpi on not exintuing project
        KpiStringKeyList kpiKeys = new KpiStringKeyList(Sets.newHashSet("NOT_EXIST"), Sets.newHashSet("NOT_EXIST"), EntityType.PROJECT);
        List<MeasureResult> measures = measureService.currentMeasures(kpiKeys);
        
        // Then the measures list must be empty
        Assert.assertTrue("Then the measures list must be empty", measures.isEmpty());
    }
}
