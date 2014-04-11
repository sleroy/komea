/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.cep.filter.NoEventFilter;
import org.komea.product.cep.formula.CountFormula;
import org.komea.product.cep.query.CEPQueryBuilder;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.service.dto.KpiKey;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobDataMap;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@RunWith(MockitoJUnitRunner.class)
public class KPIServiceTest
{
    
    
    private static final String         KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12 =
                                                                                      "KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12";
    private static final String         SELECT_COUNT_FROM_ALERT               =
                                                                                      "SELECT COUNT(*) From Event";
    @Mock
    private IEventEngineService         cepEngine;
    @Mock
    private ICronRegistryService        cronRegistryService;
    @Mock
    private KpiDao                      kpiDAOMock;
    
    @InjectMocks
    private final KPIService            kpiService                            = new KPIService();
    
    @Mock
    private MeasureDao                  measureDAOMock;
    
    @InjectMocks
    private final MeasureHistoryService measureService                        =
                                                                                      new MeasureHistoryService();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.KPIService#createOrUpdateHistoryCronJob(org.komea.product.database.model.Kpi, org.komea.product.database.api.IEntity)}
     * .
     */
    @Test @Ignore
    public void testCreateOrUpdateHistoryCronJob() throws Exception {
    
    
        final Kpi kpi = new Kpi();
        kpi.setCronExpression("0 0 0 0 0");
        
        kpiService.createOrUpdateHistoryCronJob(kpi, new Project());
        final ArgumentCaptor<Class> argumentCaptor = ArgumentCaptor.forClass(Class.class);
        verify(kpiService.getCronRegistry(), atLeastOnce()).registerCronTask(Matchers.anyString(),
                Matchers.anyString(), argumentCaptor.capture(), Matchers.any(JobDataMap.class));
        kpiService.createOrUpdateHistoryCronJob(kpi, new Project());
        verify(kpiService.getCronRegistry(), times(2)).existCron(Matchers.anyString());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.KPIService#deleteKpi(org.komea.product.database.model.Kpi)}.
     */
    @Test @Ignore
    @Ignore
    public void testDeleteKpi() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.KPIService#findKPIFacade(org.komea.product.backend.business.IEntityWithKPIFacade, java.lang.String)}
     * This test initialises all the requested services to obtain a KPI Facade.
     * Esper Engine is Mocked. The objective is to obtain the requested entity
     * with its KPI through a facade. .
     */
    @Test @Ignore
    public final void testFindKpi() {
    
    
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
        kpi.setProviderType(ProviderType.OTHER);
        kpi.setKpiKey("PERSON_PRODUCTIVITY");
        kpi.setValueMin(0d);
        kpi.setValueMax(new Double(Long.MAX_VALUE));
        kpi.setName("Person productivity");
        kpi.setValueDirection(ValueDirection.BETTER);
        kpi.setValueType(ValueType.INT);
        kpi.setProviderType(ProviderType.OTHER);
        kpiList.add(kpi);
        
        Mockito.when(kpiDAOMock.selectByCriteriaWithBLOBs(Matchers.any(KpiCriteria.class)))
                .thenReturn(kpiList);
        
        Mockito.when(cepEngine.getQueryOrFail(KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12)).thenReturn(
                CEPQueryBuilder.create(new CountFormula()).defineFilter(new NoEventFilter())
                        .build());
        
        final Kpi findKPIFacade =
                kpiService.findKPI(KpiKey.ofKpiNameAndEntity("PERSON_PRODUCTIVITY", person));
        Assert.assertNotNull(findKPIFacade);
        Assert.assertEquals(kpi, findKPIFacade);
        Assert.assertEquals(person.getId(), kpi.getEntityID());
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.kpi.KPIService#prepareKpiHistoryJob(org.komea.product.database.model.Kpi, org.komea.product.database.api.IEntity, java.lang.String)}
     * .
     */
    @Test @Ignore
    public void testPrepareKpiHistoryJob() throws Exception {
    
    
        kpiService.prepareKpiHistoryJob(new Kpi(), null, "KPI_CRON_NAME");
        final ArgumentCaptor<JobDataMap> argumentCaptor = ArgumentCaptor.forClass(JobDataMap.class);
        final ArgumentCaptor<Class> classCaptor = ArgumentCaptor.forClass(Class.class);
        verify(kpiService.getCronRegistry(), times(1)).registerCronTask(Matchers.anyString(),
                Matchers.anyString(), classCaptor.capture(), argumentCaptor.capture());
        
        
        CronInstantiatorFactory.testCronInstantiation(classCaptor.getValue(),
                argumentCaptor.getValue());
    }
    
    
}
