
package org.komea.product.web.rest.api;


import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.ManyHistoricalMeasureRequest;
import org.komea.product.service.dto.PeriodCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath:/spring/application-context-test.xml", "classpath*:/spring/dispatcher-servlet-test.xml" })
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class MeasureControllerBugTest extends AbstractSpringIntegrationTestCase {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private MeasuresController    measureController;
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.MeasureService#findMultipleHistoricalMeasure(org.komea.product.service.dto.KpiStringKeyList, org.komea.product.service.dto.PeriodCriteria)}
     * .
     */
    @Test
    @Transactional
    @DatabaseSetup("findMultipleHistoricalMeasure.xml")
    public final void testFindMultipleHistoricalMeasureWithAllMeasuresAndKpi() throws Exception {
    
        final ManyHistoricalMeasureRequest request = new ManyHistoricalMeasureRequest();
        final KpiStringKeyList kpiKeyList = new KpiStringKeyList();
        kpiKeyList.addKpiKey("BRANCH_COVERAGE(%)");
        kpiKeyList.addKpiKey("LINE_COVERAGE_MISSING");
        kpiKeyList.addEntityKey("komea");
        kpiKeyList.addEntityKey("techdebt");
        
        final PeriodCriteria period = new PeriodCriteria();
        period.setStartDate(new DateTime().minusYears(2).toDate());
        period.setEndDate(new Date());
        
        request.setKpiKeyList(kpiKeyList);
        request.setPeriod(period);
        
        measureController.averageHistoricalWithEvolution(request);
        
    }
    
}
