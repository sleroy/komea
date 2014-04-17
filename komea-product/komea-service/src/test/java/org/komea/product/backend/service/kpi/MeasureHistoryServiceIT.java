/**
 *
 */
package org.komea.product.backend.service.kpi;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.genericservice.DAOEventRegistry;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
public class MeasureHistoryServiceIT extends AbstractSpringIntegrationTestCase {

    @Autowired
    private KpiDao kpiDao;

    @Autowired
    private IMeasureHistoryService measureHistoryService;

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#getFilteredHistory(org.komea.product.backend.service.history.HistoryKey, int, org.komea.product.database.model.MeasureCriteria)}
     * .
     */
    @Test 
    public final void testGetFilteredHistoryHistoryKeyIntMeasureCriteria() {

        final Kpi exampleKPI = buildFakeKpi("DEMO_FILTER1");
        final HistoryKey historyKey
                = HistoryKey.of(exampleKPI, EntityType.PROJECT, exampleKPI.getId());
        final List<Measure> filteredHistory
                = measureHistoryService.getFilteredHistory(historyKey, 2, new MeasureCriteria());
        Assert.assertTrue(filteredHistory.isEmpty());
        final Measure measure = buildFakeMeasure(exampleKPI);
        measureHistoryService.storeMeasure(measure);
        final List<Measure> secondTiMeasures
                = measureHistoryService.getFilteredHistory(historyKey, 2, new MeasureCriteria());
        Assert.assertEquals(1, secondTiMeasures.size());
        Assert.assertEquals(measure.getId(), secondTiMeasures.get(0).getId());
        final Measure measure2 = buildFakeMeasure(exampleKPI);
        measureHistoryService.storeMeasure(measure2);
        Assert.assertEquals(2,
                measureHistoryService.getFilteredHistory(historyKey, 2, new MeasureCriteria())
                .size());
        final List<Measure> filterValidation
                = measureHistoryService.getFilteredHistory(historyKey, 1, new MeasureCriteria());
        Assert.assertEquals(1, filterValidation.size());
        System.out.println(filterValidation);

        Assert.assertEquals(measure2.getId(), filterValidation.get(0).getId()); // LINKED TO BUG
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#getFilteredHistory(org.komea.product.backend.service.history.HistoryKey, org.komea.product.database.model.MeasureCriteria)}
     * .
     */
    @Test 
    public final void testGetFilteredHistoryHistoryKeyMeasureCriteria() {

        final Kpi exampleKPI = buildFakeKpi("DEMO_FILTER2");
        final HistoryKey historyKey
                = HistoryKey.of(exampleKPI, EntityType.PROJECT, exampleKPI.getId());
        Assert.assertTrue("Expected no measures ",
                measureHistoryService.getFilteredHistory(historyKey, 2, new MeasureCriteria())
                .isEmpty());
        final Measure measure = buildFakeMeasure(exampleKPI);
        measureHistoryService.storeMeasure(measure);
        final List<Measure> secondTiMeasures
                = measureHistoryService.getFilteredHistory(historyKey, 2, new MeasureCriteria());
        Assert.assertEquals("Expected one measure", 1, secondTiMeasures.size());

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#getHistory(org.komea.product.backend.service.history.HistoryKey)}.
     */
    @Test 
    public final void testGetHistory() {

        final Kpi exampleKPI = buildFakeKpi("DEMO_FILTER3");
        final HistoryKey historyKey
                = HistoryKey.of(exampleKPI, EntityType.PROJECT, exampleKPI.getId());
        final Measure measure = buildFakeMeasure(exampleKPI);
        Assert.assertTrue(measureHistoryService.getHistory(historyKey).isEmpty());
        measureHistoryService.storeMeasure(measure);
        Assert.assertEquals(1, measureHistoryService.getHistory(historyKey).size());
        measureHistoryService.storeMeasure(measure);
        measureHistoryService.storeMeasure(measure);
        Assert.assertEquals("Expected 3 messages", 3, measureHistoryService.getHistory(historyKey)
                .size());

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#storeMeasure(org.komea.product.database.model.Measure)}.
     */
    @Test 
    public final void testStoreMeasure() {

        final Kpi exampleKPI = buildFakeKpi("DEMO_FILTER4");

        HistoryKey.of(exampleKPI, EntityType.PROJECT, exampleKPI.getId());
        final Measure measure = buildFakeMeasure(exampleKPI);
        measureHistoryService.saveOrUpdate(measure);
        Assert.assertNotNull(measure.getId());

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#buildHistoryPurgeAction(org.komea.product.database.model.Kpi)}.
     */
    @Test 
    public final void testUnitBuildHistoryPurgeAction() {

        final MeasureHistoryService buildMeasureHistoryComponent = buildMeasureHistoryComponent();
        // Test day
        final Kpi kpiDay = new Kpi();
        kpiDay.setEvictionType(EvictionType.DAYS);
        Assert.assertTrue(buildMeasureHistoryComponent.buildHistoryPurgeAction(kpiDay) instanceof HistoryPurgePerDaysAction);
        // Test day
        final Kpi kpiMonth = new Kpi();
        kpiMonth.setEvictionType(EvictionType.MONTHS);
        Assert.assertTrue(buildMeasureHistoryComponent.buildHistoryPurgeAction(kpiMonth) instanceof HistoryPurgePerMonthsAction);
        // Test day
        final Kpi kpiValues = new Kpi();
        kpiValues.setEvictionType(EvictionType.VALUES);
        Assert.assertTrue(buildMeasureHistoryComponent.buildHistoryPurgeAction(kpiValues) instanceof HistoryPurgePerValuesAction);

    }

    private Kpi buildFakeKpi(final String _kpiNameString) {

        final Kpi exampleKPI = new Kpi();
        exampleKPI.setKpiKey(_kpiNameString);
        exampleKPI.setProviderType(ProviderType.OTHER);
        exampleKPI.setEntityType(EntityType.PROJECT);
        exampleKPI.setEntityID(_kpiNameString.hashCode());
        kpiDao.insert(exampleKPI);
        // exampleKPI.setId(_kpiNameString.hashCode());
        return exampleKPI;
    }

    private Measure buildFakeMeasure(final Kpi _exampleKPI) {

        final Measure measure = new Measure();
        measure.setIdKpi(_exampleKPI.getId());
        measure.setEntity(EntityType.PROJECT, _exampleKPI.getId());
        measure.setValue(14d);
        measure.setDate(new Date());
        return measure;
    }

    private MeasureHistoryService buildMeasureHistoryComponent() {

        final MeasureHistoryService measureHistoryService = new MeasureHistoryService();
        measureHistoryService.setDaoEventRegistry(Mockito.mock(DAOEventRegistry.class, Mockito
                .withSettings().verboseLogging()));
        measureHistoryService.setEsperEngine(Mockito.mock(IEventEngineService.class, Mockito
                .withSettings().verboseLogging()));
        measureHistoryService.setMeasureDAO(Mockito.mock(MeasureDao.class, Mockito.withSettings()
                .verboseLogging()));
        return measureHistoryService;
    }

}
