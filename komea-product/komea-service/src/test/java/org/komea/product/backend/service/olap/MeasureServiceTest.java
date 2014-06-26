/**
 *
 */

package org.komea.product.backend.service.olap;



import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.PeriodCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/application-context-test.xml")
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class })
public class MeasureServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IKPIService     kpiService;


    @Autowired
    private IMeasureService measureService;



    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.MeasureService#findMultipleHistoricalMeasure(org.komea.product.service.dto.KpiStringKeyList, org.komea.product.service.dto.PeriodCriteria)}
     * .
     */
    @Test
    @Transactional
    @DatabaseSetup("/dbunit/findMultipleHistoricalMeasure.xml")
    public final void testFindMultipleHistoricalMeasureWithAllMeasuresAndKpi() throws Exception {


        final KpiStringKeyList kpiKeyList = new KpiStringKeyList();
        final PeriodCriteria period = new PeriodCriteria();
        period.setStartDate(new DateTime().minusYears(2).toDate());
        period.setEndDate(new Date());
        final List<TimeSerieDTO> findMultipleHistoricalMeasure =
                measureService.findMultipleHistoricalMeasure(kpiKeyList, period);
        final Set<String> kpiKey = new HashSet<String>();
        for (final TimeSerieDTO dto : findMultipleHistoricalMeasure) {
            kpiKey.add(dto.getKpi().getKey());
        }
        assertTrue(kpiKey.contains("BRANCH_COVERAGE(%)"));
        assertTrue(kpiKey.contains("LINE_COVERAGE"));


    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.MeasureService#findMultipleHistoricalMeasure(org.komea.product.service.dto.KpiStringKeyList, org.komea.product.service.dto.PeriodCriteria)}
     * .
     */
    @Test
    @Transactional
    @DatabaseSetup("/dbunit/findMultipleHistoricalMeasure.xml")
    public final void testFindMultipleHistoricalMeasureWIthoutPeriod() throws Exception {


        final KpiStringKeyList kpiKeyList = new KpiStringKeyList();
        final PeriodCriteria period = new PeriodCriteria();
        
        final List<TimeSerieDTO> findMultipleHistoricalMeasure =
                measureService.findMultipleHistoricalMeasure(kpiKeyList, period);
        for (final TimeSerieDTO timeSerieDTO : findMultipleHistoricalMeasure) {
            assertTrue(timeSerieDTO.getCoordinates().isEmpty());
        }


    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.MeasureService#findMultipleHistoricalMeasure(org.komea.product.service.dto.KpiStringKeyList, org.komea.product.service.dto.PeriodCriteria)}
     * .
     */
    @Test
    @Transactional
    @DatabaseSetup("/dbunit/findMultipleHistoricalMeasure.xml")
    public final void testFindMultipleHistoricalMeasureWithSomeKpiAndSomeMeasure() throws Exception {


        final KpiStringKeyList kpiKeyList = new KpiStringKeyList();
        kpiKeyList.addKpiKey("BRANCH_COVERAGE(%)");
        kpiKeyList.addKpiKey("LINE_COVERAGE");
        kpiKeyList.addEntityKey("komea");
        kpiKeyList.addEntityKey("techdebt");

        final PeriodCriteria period = new PeriodCriteria();
        period.setStartDate(new DateTime().minusYears(2).toDate());
        period.setEndDate(new Date());
        final List<TimeSerieDTO> findMultipleHistoricalMeasure =
                measureService.findMultipleHistoricalMeasure(kpiKeyList, period);
        final Set<String> kpiKey = new HashSet<String>();
        for (final TimeSerieDTO dto : findMultipleHistoricalMeasure) {
            kpiKey.add(dto.getKpi().getKey());
        }
        assertEquals(4, findMultipleHistoricalMeasure.size());
        assertTrue(kpiKey.contains("BRANCH_COVERAGE(%)"));
        assertTrue(kpiKey.contains("LINE_COVERAGE"));


    }


    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.MeasureService#findMultipleHistoricalMeasure(org.komea.product.service.dto.KpiStringKeyList, org.komea.product.service.dto.PeriodCriteria)}
     * .
     */
    @Test
    @Transactional
    @DatabaseSetup("/dbunit/findMultipleHistoricalMeasure.xml")
    public final void testFindMultipleHistoricalMeasureWithSomeKpiAndSomeMeasureAndMIssingValues()
            throws Exception {


        System.out.println(FormulaID.of(kpiService.selectByKey("BRANCH_COVERAGE(%)")).getId());
        System.out.println(FormulaID.of(kpiService.selectByKey("LINE_COVERAGE")).getId());
        System.out.println("LINE_MISSING : "
                + FormulaID.of(kpiService.selectByKey("LINE_COVERAGE_MISSING")).getId());

        final KpiStringKeyList kpiKeyList = new KpiStringKeyList();
        kpiKeyList.addKpiKey("BRANCH_COVERAGE(%)");
        kpiKeyList.addKpiKey("LINE_COVERAGE_MISSING");
        kpiKeyList.addEntityKey("komea");
        kpiKeyList.addEntityKey("techdebt");

        final PeriodCriteria period = new PeriodCriteria();
        period.setStartDate(new DateTime().minusYears(2).toDate());
        period.setEndDate(new Date());
        final List<TimeSerieDTO> findMultipleHistoricalMeasure =
                measureService.findMultipleHistoricalMeasure(kpiKeyList, period);
        final Set<String> kpiKey = new HashSet<String>();
        for (final TimeSerieDTO dto : findMultipleHistoricalMeasure) {
            kpiKey.add(dto.getKpi().getKey());


        }
        assertTrue(kpiKey.contains("BRANCH_COVERAGE(%)"));
        assertTrue(kpiKey.contains("LINE_COVERAGE_MISSING"));

        assertEquals(1, findMultipleHistoricalMeasure.get(0).getCoordinates().size());
        assertEquals(0, findMultipleHistoricalMeasure.get(1).getCoordinates().size()); // MISSING VALUE
        assertEquals(1, findMultipleHistoricalMeasure.get(2).getCoordinates().size());
        assertEquals(1, findMultipleHistoricalMeasure.get(3).getCoordinates().size());
        assertEquals(4, findMultipleHistoricalMeasure.size());
        System.out.println(findMultipleHistoricalMeasure);
    }
}
