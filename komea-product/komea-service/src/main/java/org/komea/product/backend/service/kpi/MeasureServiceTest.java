
package org.komea.product.backend.service.kpi;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

public class MeasureServiceTest extends AbstractSpringDBunitIntegrationTest {
    
    //
    @Autowired
    private IMeasureService measureService;
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_getMeasure() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        
        // WHEN the user looking for the coverage-branch for the project komea
        // between 1/1/01 and now
        // with max number result = 5
        HistoryStringKey measureKey = new HistoryStringKey("BRANCH_COVERAGE(%)", "KOMEA", ExtendedEntityType.PROJECT);
        LimitCriteria limit = LimitCriteria.CreateLimitCriteriaForNValues(5);
        
        MeasureResult measure = measureService.getMeasure(measureKey, limit);
        
        // THEN the measure must have two values
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(2, historicalValues.size());
        // the first value must be 35%
        Assert.assertEquals(35, historicalValues.get(0).getValue(), 0.001);
        // the first value must be 60%
        Assert.assertEquals(60, historicalValues.get(1).getValue(), 0.001);
    }
}
