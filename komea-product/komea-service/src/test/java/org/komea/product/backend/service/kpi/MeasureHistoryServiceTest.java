/**
 *
 */
package org.komea.product.backend.service.kpi;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.genericservice.DAOEventRegistry;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class MeasureHistoryServiceTest {

    @Mock
    private DAOEventRegistry daoEventRegistry;

    @Mock
    private IEventEngineService esperEngine;

    @Mock
    private MeasureDao measureDAO;

    @InjectMocks
    private MeasureHistoryService measureHistoryService;
    @Mock
    private MeasureDao requiredDAO;

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#buildHistoryPurgeAction(org.komea.product.database.model.Kpi)}.
     */
    @Test @Ignore
    public final void testBuildHistoryPurgeAction() throws Exception {

        // TODO
        // throw new RuntimeException("not yet implemented");
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#buildMeasureCriteriaFromSearchFilter(org.komea.product.database.dto.SearchMeasuresDto, org.komea.product.backend.service.history.HistoryKey)}
     * .
     */
    @Test @Ignore
    public final void testBuildMeasureCriteriaFromSearchFilter() throws Exception {

        // TODO
        // throw new RuntimeException("not yet implemented");
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#getFilteredHistory(org.komea.product.backend.service.history.HistoryKey, int, org.komea.product.database.model.MeasureCriteria)}
     * .
     */
    @Test @Ignore
    public final void testGetFilteredHistoryHistoryKeyIntMeasureCriteria() throws Exception {

        // TODO
        // throw new RuntimeException("not yet implemented");
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#getFilteredHistory(org.komea.product.backend.service.history.HistoryKey, org.komea.product.database.model.MeasureCriteria)}
     * .
     */
    @Test @Ignore
    public final void testGetFilteredHistoryHistoryKeyMeasureCriteria() throws Exception {

        // TODO
        // throw new RuntimeException("not yet implemented");
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#getHistory(org.komea.product.backend.service.history.HistoryKey)}.
     */
    @Test @Ignore
    public final void testGetHistory() throws Exception {

        final Kpi kpi
                = KpiBuilder
                .createAscending()
                .providerType(ProviderType.OTHER)
                .nameAndKeyDescription("Number of successful build per day")
                .entityType(EntityType.PROJECT)
                .expirationMonth()
                .query("SELECT project as entity, COUNT(*) as value FROM Event.win:time(1 day) WHERE eventType.eventKey='build_complete' GROUP BY project")
                .cronSixHours().build();

        kpi.setId(1);
        final HistoryKey kpiKey = HistoryKey.of(kpi);
        Assert.assertFalse(kpiKey.hasEntityReference());
        final List<Measure> history = measureHistoryService.getHistory(kpiKey);
        Mockito.verify(requiredDAO, Mockito.times(1)).selectByCriteria(
                Matchers.any(MeasureCriteria.class));
        Assert.assertTrue(history.isEmpty());
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.MeasureHistoryService#getMeasures(java.util.List, java.util.List, org.komea.product.database.dto.SearchMeasuresDto)}
     * .
     */
    @Test @Ignore
    public final void testGetMeasures() throws Exception {

        // TODO
        // throw new RuntimeException("not yet implemented");
    }

}
