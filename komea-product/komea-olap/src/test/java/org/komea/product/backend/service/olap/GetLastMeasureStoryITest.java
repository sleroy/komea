
package org.komea.product.backend.service.olap;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.KpiLoadingService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.google.common.collect.Sets;

@DatabaseTearDown(value = "measures2.xml", type = DatabaseOperation.DELETE_ALL)
public class GetLastMeasureStoryITest extends AbstractSpringDBunitIntegrationTest {
    
    @Autowired
    private IMeasureService   measureService;
    
    @Autowired
    private KpiLoadingService kpiLoading;
    
    @Before
    public void setpUp() {
    
        kpiLoading.initLoadingService();
    }
    
    @Test
    @DatabaseSetup("measures2.xml")
    public void test_last_measure_for_kpi_branch_koverage_on_komea_project() {
    
        // Given thetwo last stored measurs in komea the same days the komea project value are 60 and 70
        // WHEN I ask for the current branch coverage on the komea project
        KpiStringKey kpiKey = KpiStringKey.ofKpiNameAndEntityDetails("BRANCH_COVERAGE(%)", EntityType.PROJECT, "KOMEA");
        double currentMeasure = measureService.lastMeasure(kpiKey);
        
        // Then the current branch coverage must be 65 (average between 60 ans 70
        Assert.assertEquals(65, currentMeasure, 0.001);
    }
    
    @Test
    @DatabaseSetup("measures2.xml")
    public void test_current_measure_for_kpi_branch_koverage_on_komea_project_many() {
    
        // Given thetwo last stored measurs in komea the same days the komea project value are 60 and 70 // WHEN I ash for the current
        // branch coverage on the komea project
        // WHEN I ask for the current branch coverage on the komea project
        KpiStringKeyList kpiKeys = new KpiStringKeyList(Sets.newHashSet("BRANCH_COVERAGE(%)"), Sets.newHashSet("KOMEA"), EntityType.PROJECT);
        double currentMeasure = measureService.lastMeasures(kpiKeys).get(0).getValue();
        
        // Then the current branch coverage must be 35
        Assert.assertEquals(62, currentMeasure, 0.001);
    }
}
