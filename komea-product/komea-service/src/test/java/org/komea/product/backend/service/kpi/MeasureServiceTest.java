
package org.komea.product.backend.service.kpi;


import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.HistoricalValue;
import org.komea.product.service.dto.HistoryStringKey;
import org.komea.product.service.dto.HistoryStringKeyList;
import org.komea.product.service.dto.LimitCriteria;
import org.komea.product.service.dto.MeasureResult;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.common.collect.Lists;

public class MeasureServiceTest extends AbstractSpringDBunitIntegrationTest {
    
    //
    @Autowired
    private IMeasureHistoryService measureService;
    
    public static class DemoQuery implements IDynamicDataQuery {
        
        @Override
        public String getFormula() {
        
            // TODO Auto-generated getFormula
            return null;
        }
        
        @Override
        public KpiResult getResult() {
        
            KpiResult kpiResult = new KpiResult();
            kpiResult.put(EntityKey.of(EntityType.PROJECT, 4), 62);
            return kpiResult;
        }
        
    }
    
    @Autowired
    private IKpiQueryRegisterService kpiQueryRegisterService;
    
    @Autowired
    private KpiDao                   kpiDAO;
    
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
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure must have two values
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(3, historicalValues.size());
        // the first value must be 35%
        Assert.assertEquals(35, historicalValues.get(1).getValue(), 0.001);
        // the seconf value must be 60%
        Assert.assertEquals(60, historicalValues.get(0).getValue(), 0.001);
        
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test__only_one_getMeasure() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        // AND the current coverage value is 62%
        
        // WHEN the user looking for the coverage-branch for the project komea
        // between 1/1/01 and now
        // with max number result = 1
        HistoryStringKey measureKey = new HistoryStringKey("BRANCH_COVERAGE(%)", "KOMEA", ExtendedEntityType.PROJECT);
        LimitCriteria limit = LimitCriteria.CreateLimitCriteriaForNValues(1);
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure must have only one values
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(1, historicalValues.size());
        // the first value must be 35%
        Assert.assertEquals(60, historicalValues.get(0).getValue(), 0.001);
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test__only_one_getMeasure_with__start_date_after_first_value() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        
        // WHEN the user looking for the coverage-branch for the project komea
        // between 1/04/2014 and now
        // with max number result = 5
        HistoryStringKey measureKey = new HistoryStringKey("BRANCH_COVERAGE(%)", "KOMEA", ExtendedEntityType.PROJECT);
        Date startDate = new DateTime(2014, 4, 1, 0, 0, 0).toDate();
        ;
        LimitCriteria limit = LimitCriteria.CreateLimitCriteriaFromStartingDate(startDate, 5);
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure must have only one values
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(1, historicalValues.size());
        // the first value must be 60%
        Assert.assertEquals(60, historicalValues.get(0).getValue(), 0.001);
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test__only_one_getMeasure_with_end_date_before_first_value() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        
        // WHEN the user looking for the coverage-branch for the project komea
        // between 1/1/2013 and 1/4/2014
        // with max number result = 5
        HistoryStringKey measureKey = new HistoryStringKey("BRANCH_COVERAGE(%)", "KOMEA", ExtendedEntityType.PROJECT);
        Date startDate = new DateTime(2013, 1, 1, 0, 0, 0).toDate();
        Date endDate = new DateTime(2014, 4, 1, 0, 0, 0).toDate();
        ;
        LimitCriteria limit = LimitCriteria.CreateLimitCriteria(startDate, endDate, 5);
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure must have only one values
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(1, historicalValues.size());
        // the first value must be 35%
        Assert.assertEquals(35, historicalValues.get(0).getValue(), 0.001);
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test__only_one_getMeasure_with_start_date_sup_end_date() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        
        // WHEN the user looking for the coverage-branch for the project komea
        // between 5/5/2014 and 1/4/2014
        // with max number result = 5
        HistoryStringKey measureKey = new HistoryStringKey("BRANCH_COVERAGE(%)", "KOMEA", ExtendedEntityType.PROJECT);
        Date startDate = new DateTime(2014, 5, 5, 0, 0, 0).toDate();
        Date endDate = new DateTime(2014, 4, 1, 0, 0, 0).toDate();
        ;
        LimitCriteria limit = LimitCriteria.CreateLimitCriteria(startDate, endDate, 5);
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure list must be empty
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(0, historicalValues.size());
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_getMeasure_with_negative_limit_number_value() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        
        // WHEN the user looking for the coverage-branch for the project komea
        // between 1/1/01 and now
        // with max number result = -1
        HistoryStringKey measureKey = new HistoryStringKey("BRANCH_COVERAGE(%)", "KOMEA", ExtendedEntityType.PROJECT);
        LimitCriteria limit = LimitCriteria.CreateLimitCriteriaForNValues(-1);
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure list must be empty
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(0, historicalValues.size());
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_getMeasure_not_existing_kpi() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        
        // WHEN the user looking for the coverage-branch for the project komea
        // between 1/1/01 and now
        // with max number result = -1
        HistoryStringKey measureKey = new HistoryStringKey("NOT_EXIST", "KOMEA", ExtendedEntityType.PROJECT);
        LimitCriteria limit = LimitCriteria.CreateLimitCriteriaForNValues(-1);
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure list must be empty
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(0, historicalValues.size());
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_getMeasure_not_existing_Project() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        
        // WHEN the user looking for the coverage-branch for the project komea
        // between 1/1/01 and now
        // with max number result = -1
        HistoryStringKey measureKey = new HistoryStringKey("BRANCH_COVERAGE(%)", "NOT_EXIST", ExtendedEntityType.PROJECT);
        LimitCriteria limit = LimitCriteria.CreateLimitCriteriaForNValues(-1);
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure list must be empty
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(0, historicalValues.size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    @DatabaseSetup("measures.xml")
    public void test_getMeasure_with_null_kpiKey() {
    
        // GIVEN the database contain the KPI branch_coverage
        // AND the project Komea has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        
        // WHEN the user looking for the null KPI for the project komea
        // between 1/1/01 and now
        // with max number result = -1
        HistoryStringKey measureKey = new HistoryStringKey(null, "KOMEA", ExtendedEntityType.PROJECT);
        LimitCriteria limit = LimitCriteria.CreateLimitCriteriaForNValues(-1);
        
        MeasureResult measure = measureService.getHistoricalMeasure(measureKey, limit);
        
        // THEN the measure list must be empty
        List<HistoricalValue> historicalValues = measure.getHistoricalValues();
        Assert.assertEquals(0, historicalValues.size());
    }
    
    @Test
    @DatabaseSetup("measures.xml")
    public void test_getMeasures() {
    
        // GIVEN the database contain the KPI branch_coverage and line_coverage
        // AND the database contain the project KOMEA and SCERTIFY
        // AND the project Komea for the branch_coverage has two value for this KPI : 35% (5/01/2014) and 60% ((1/05/2014)
        // AND the project Komea for the line_coverage has two value for this KPI : 42% (5/01/2014) and 57% ((1/05/2014)
        // AND the project Scertify for the branch_coverage has two value for this KPI : 22% (5/01/2014) and 44% ((1/05/2014)
        // AND the project Scertify for the line_coverage has two value for this KPI : 55% (5/01/2014) and 66% ((1/05/2014)
        
        // WHEN the user looking for the coverage-branch and line coverage for the project komea, and scertify
        // between 1/1/01 and now
        // with max number result = 5
        
        List<String> kpiKeys = Lists.newArrayList("BRANCH_COVERAGE(%)", "LINE_COVERAGE");
        List<String> entityKeys = Lists.newArrayList("KOMEA", "SCERTIFY");
        LimitCriteria limit = LimitCriteria.CreateLimitCriteriaForNValues(5);
        HistoryStringKeyList historyKeyList = new HistoryStringKeyList(kpiKeys, entityKeys, ExtendedEntityType.PROJECT);
        List<MeasureResult> measureResults = measureService.getHistoricalMeasures(historyKeyList, limit);
        
        // THEN the measure list must be 4 ( 2 KPI on Ti projects)
        Assert.assertEquals(4, measureResults.size());
        // List<HistoricalValue> historicalValues = measure.getHistoricalValues();
    }
}
