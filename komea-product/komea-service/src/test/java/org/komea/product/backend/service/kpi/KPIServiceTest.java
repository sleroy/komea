/**
 * 
 */

package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.EntityWithKPIAdapter;
import org.komea.product.backend.service.business.IEntityWithKPI;
import org.komea.product.backend.service.business.IKPIFacade;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Person;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.espertech.esper.client.EPStatement;



/**
 * @author sleroy
 */
public class KPIServiceTest
{
    
    
    private static final String KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12 = "KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12";
    private static final String SELECT_COUNT_FROM_ALERT = "SELECT COUNT(*) From Alert";


    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.KPIService#findKPIFacade(org.komea.product.backend.service.business.IEntityWithKPI, java.lang.String)}
     * This test initialises all the requested services to obtain a KPI Facade. Esper Engine is Mocked. The objective is to obtain the
     * requested entity with its KPI through a facade.
     * .
     */
    @Test
    public final void testFindKPIFacade() {
    
    
        final KpiDao kpiDAOMock =
                Mockito.mock(KpiDao.class, Mockito.withSettings().verboseLogging());
        final MeasureDao measureDAOMock =
                Mockito.mock(MeasureDao.class, Mockito.withSettings().verboseLogging());
        
        final IEsperEngine esperEngine =
                Mockito.mock(IEsperEngine.class, Mockito.withSettings().verboseLogging());
        
        final EPStatement epStatementMock =
                Mockito.mock(EPStatement.class, Mockito.withSettings().verboseLogging());
        
        final MetricService metricService = new MetricService();
        metricService.setEsperEngine(esperEngine);
        
        final MeasureService measureService = new MeasureService();
        
        measureService.setMeasureDAO(measureDAOMock);
        
        final EntityWithKPIAdapter kpiAdapter = new EntityWithKPIAdapter();
        
        kpiAdapter.setKpiDAO(kpiDAOMock);
        
        final KPIService kpiService = new KPIService();
        kpiService.setEsperEngine(esperEngine);
        kpiService.setMetricService(metricService);
        kpiService.setMeasureService(measureService);
        
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
        kpi.setMinValue(0d);
        kpi.setMaxValue(new Double(Long.MAX_VALUE));
        kpi.setName("Person productivity");
        kpi.setValueDirection(ValueDirection.BETTER);
        kpi.setValueType(ValueType.INT);
        kpiList.add(kpi);
        
        Mockito.when(kpiDAOMock.selectByCriteria(Matchers.any(KpiCriteria.class))).thenReturn(
                kpiList);
        
        Mockito.when(esperEngine.getStatementOrFail(KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12))
                .thenReturn(epStatementMock);
        
        
        final IEntityWithKPI<Person> personAdapter = kpiAdapter.adapt(person);
        
        try {
            
            
            final IKPIFacade<Person> findKPIFacade =
                    kpiService.findKPIFacade(personAdapter, "PERSON_PRODUCTIVITY");
            Assert.assertNotNull(findKPIFacade);
            Assert.assertEquals(kpi, findKPIFacade.getKPI());
            Assert.assertEquals(personAdapter, findKPIFacade.getEntity());
            Assert.assertEquals(kpiList, findKPIFacade.getEntity().getListofKpis());
            
            
        } catch (final KPINotFoundException e) {
            Assert.fail(e.getMessage());
        }
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.KPIService#synchronizeInEsper(org.komea.product.backend.service.business.IEntityWithKPI)}
     * .
     */
    @Test
    public final void testSynchronizeInEsper() {
    
    
        final KpiDao kpiDAOMock =
                Mockito.mock(KpiDao.class, Mockito.withSettings().verboseLogging());
        final MeasureDao measureDAOMock =
                Mockito.mock(MeasureDao.class, Mockito.withSettings().verboseLogging());
        
        final IEsperEngine esperEngine =
                Mockito.mock(IEsperEngine.class, Mockito.withSettings().verboseLogging());
        
        final EPStatement epStatementMock =
                Mockito.mock(EPStatement.class, Mockito.withSettings().verboseLogging());
        
        final MetricService metricService = new MetricService();
        metricService.setEsperEngine(esperEngine);
        
        final MeasureService measureService = new MeasureService();
        
        measureService.setMeasureDAO(measureDAOMock);
        
        final EntityWithKPIAdapter kpiAdapter = new EntityWithKPIAdapter();
        
        kpiAdapter.setKpiDAO(kpiDAOMock);
        
        final KPIService kpiService = new KPIService();
        kpiService.setEsperEngine(esperEngine);
        kpiService.setMetricService(metricService);
        kpiService.setMeasureService(measureService);
        
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
        kpi.setMinValue(0d);
        kpi.setMaxValue(new Double(Long.MAX_VALUE));
        kpi.setName("Person productivity");
        kpi.setValueDirection(ValueDirection.BETTER);
        kpi.setValueType(ValueType.INT);
        kpiList.add(kpi);
        
        Mockito.when(kpiDAOMock.selectByCriteria(Matchers.any(KpiCriteria.class))).thenReturn(
                kpiList);
        
        Mockito.when(esperEngine.getStatementOrFail(KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12))
                .thenReturn(epStatementMock);
        
        
        final IEntityWithKPI<Person> personAdapter = kpiAdapter.adapt(person);
        kpiService.synchronizeInEsper(personAdapter);
        
        Mockito.verify(esperEngine, Mockito.times(1)).createOrUpdateEPL(SELECT_COUNT_FROM_ALERT,
                KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12);
        
        
    }
}
