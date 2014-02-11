/**
 *
 */
package org.komea.product.backend.service.kpi;

import com.espertech.esper.client.EPStatement;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.service.dto.KpiKey;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author sleroy
 */
public class KPIServiceTest {

    private static final String KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12
            = "KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12";
    private static final String SELECT_COUNT_FROM_ALERT
            = "SELECT COUNT(*) From Event";

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.KPIService#findKPIFacade(org.komea.product.backend.service.business.IEntityWithKPIFacade, java.lang.String)}
     * This test initialises all the requested services to obtain a KPI Facade.
     * Esper Engine is Mocked. The objective is to obtain the requested entity
     * with its KPI through a facade. .
     */
    @Test
    public final void testFindKPIFacade() {

        final KpiDao kpiDAOMock
                = Mockito.mock(KpiDao.class, Mockito.withSettings().verboseLogging());
        final MeasureDao measureDAOMock
                = Mockito.mock(MeasureDao.class, Mockito.withSettings().verboseLogging());

        final IEsperEngine esperEngine
                = Mockito.mock(IEsperEngine.class, Mockito.withSettings().verboseLogging());

        final EPStatement epStatementMock
                = Mockito.mock(EPStatement.class, Mockito.withSettings().verboseLogging());

        final MeasureHistoryService measureService = new MeasureHistoryService();
        measureService.setEsperEngine(esperEngine);
        measureService.setMeasureDAO(measureDAOMock);

        final KPIService kpiService = new KPIService();
        kpiService.setEsperEngine(esperEngine);

        kpiService.setMeasureService(measureService);
        kpiService.setKpiDAO(kpiDAOMock);
        final Person person = new Person();
        person.setId(12);
        person.setFirstName("John");
        person.setLastName("Dalton");

        final List<Kpi> kpiList = new ArrayList<Kpi>();
        final Kpi kpi = new Kpi();
        kpi.setId(1);
        kpi.setEsperRequest(SELECT_COUNT_FROM_ALERT);
        kpi.setEntityType(EntityType.PERSON);
        kpi.setEntityID(person.getId());
        kpi.setDescription("Demo of KPI");
        kpi.setIdProvider(1);
        kpi.setKpiKey("PERSON_PRODUCTIVITY");
        kpi.setValueMin(0d);
        kpi.setValueMax(new Double(Long.MAX_VALUE));
        kpi.setName("Person productivity");
        kpi.setValueDirection(ValueDirection.BETTER);
        kpi.setValueType(ValueType.INT);
        kpiList.add(kpi);

        Mockito.when(kpiDAOMock.selectByExampleWithBLOBs(Matchers.any(KpiCriteria.class)))
                .thenReturn(kpiList);

        Mockito.when(esperEngine.getStatementOrFail(KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12))
                .thenReturn(epStatementMock);

        final Kpi findKPIFacade
                = kpiService.findKPI(KpiKey.withEntity("PERSON_PRODUCTIVITY", person));
        Assert.assertNotNull(findKPIFacade);
        Assert.assertEquals(kpi, findKPIFacade);
        Assert.assertEquals(person.getId(), kpi.getEntityID());

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.KPIService#synchronizeEntityWithKomea(org.komea.product.backend.service.business.IEntityWithKPIFacade)}
     * .
     */
    @Test
    public final void testSynchronizeEntityWithKomea() {

        final KpiDao kpiDAOMock
                = Mockito.mock(KpiDao.class, Mockito.withSettings().verboseLogging());
        final MeasureDao measureDAOMock
                = Mockito.mock(MeasureDao.class, Mockito.withSettings().verboseLogging());

        final IEsperEngine esperEngine
                = Mockito.mock(IEsperEngine.class, Mockito.withSettings().verboseLogging());

        final EPStatement epStatementMock
                = Mockito.mock(EPStatement.class, Mockito.withSettings().verboseLogging());

        final MeasureHistoryService measureService = new MeasureHistoryService();
        measureService.setEsperEngine(esperEngine);
        measureService.setMeasureDAO(measureDAOMock);

        final KPIService kpiService = new KPIService();
        kpiService.setEsperEngine(esperEngine);

        kpiService.setMeasureService(measureService);
        kpiService.setKpiDAO(kpiDAOMock);

        final Person person = new Person();
        person.setId(12);
        person.setFirstName("John");
        person.setLastName("Dalton");

        final List<Kpi> kpiList = new ArrayList<Kpi>();
        final Kpi kpi = new Kpi();
        kpi.setId(1);
        kpi.setEsperRequest(SELECT_COUNT_FROM_ALERT);
        kpi.setEntityType(EntityType.PERSON);
        kpi.setDescription("Demo of KPI");
        kpi.setIdProvider(1);
        kpi.setKpiKey("PERSON_PRODUCTIVITY");
        kpi.setValueMin(0d);
        kpi.setValueMax(new Double(Long.MAX_VALUE));
        kpi.setName("Person productivity");
        kpi.setValueDirection(ValueDirection.BETTER);
        kpi.setValueType(ValueType.INT);
        kpiList.add(kpi);
        kpiService.updateKPIOfEntity(person, kpiList);

        Mockito.when(kpiDAOMock.selectByExampleWithBLOBs(Matchers.any(KpiCriteria.class)))
                .thenReturn(kpiList);

        Mockito.when(esperEngine.getStatementOrFail(KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12))
                .thenReturn(epStatementMock);

        kpiService.synchronizeEntityWithKomea(person);

        final ArgumentCaptor<QueryDefinition> forClass
                = ArgumentCaptor.forClass(QueryDefinition.class);
        Mockito.verify(esperEngine, Mockito.times(2)).createOrUpdateEPLQuery(forClass.capture());
        Assert.assertEquals(KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12, forClass.getValue().getName());
        Assert.assertEquals(SELECT_COUNT_FROM_ALERT, forClass.getValue().getQuery());

    }
}
