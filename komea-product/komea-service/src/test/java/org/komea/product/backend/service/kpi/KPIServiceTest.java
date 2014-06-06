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
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.eventory.filter.NoEventFilter;
import org.komea.eventory.formula.CountFormula;
import org.komea.eventory.query.CEPQueryBuilder;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.service.dto.KpiKey;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@RunWith(MockitoJUnitRunner.class)
public class KPIServiceTest
{
    
    
    private static final String  KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12 =
                                                                               "KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12";
    private static final String  SELECT_COUNT_FROM_ALERT               =
                                                                               "SELECT COUNT(*) From Event";
    @Mock
    private IEventEngineService  cepEngine;
    @Mock
    private ICronRegistryService cronRegistryService;
    @Mock
    private KpiDao               kpiDAOMock;
    
    @InjectMocks
    private final KPIService     kpiService                            = new KPIService();
    
    @Mock
    private MeasureDao           measureDAOMock;
    
    
    
    /**
     * Test method for {@link org.komea.product.cep.tester.KPIService#deleteKpi(org.komea.product.database.model.Kpi)}.
     */
    @Test
    @Ignore
    public void testDeleteKpi() throws Exception {
    
    
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KPIService#findKPIFacade(org.komea.product.backend.business.IEntityWithKPIFacade, java.lang.String)}
     * This test initialises all the requested services to obtain a KPI Facade.
     * Esper Engine is Mocked. The objective is to obtain the requested entity
     * with its KPI through a facade. .
     */
    @Test
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
        
        kpi.setDescription("Demo of KPI");
        kpi.setProviderType(ProviderType.OTHER);
        kpi.setKpiKey("PERSON_PRODUCTIVITY");
        kpi.setValueMin(0d);
        kpi.setValueMax(new Double(Long.MAX_VALUE));
        kpi.setName("Person productivity");
        kpi.setValueDirection(ValueDirection.BETTER);
        kpi.setValueType(ValueType.INT);
        kpi.setGroupFormula(GroupFormula.AVG_VALUE);
        kpi.setProviderType(ProviderType.OTHER);
        kpiList.add(kpi);
        
        Mockito.when(kpiDAOMock.selectByCriteriaWithBLOBs(Matchers.any(KpiCriteria.class)))
                .thenReturn(kpiList);
        
        final ICacheStorageFactory cacheStorageFactory = new ICacheStorageFactory()
        {
            
            
            @Override
            public ICacheStorage newCacheStorage(final ICacheConfiguration _arg0) {
            
            
                return new GoogleCacheStorage(_arg0);
            }
        };
        Mockito.when(cepEngine.getQueryOrFail(FormulaID.of(KPI_PERSON_PRODUCTIVITY_T_1_ENTITY_12)))
                .thenReturn(
                        CEPQueryBuilder.create(new CountFormula(), cacheStorageFactory)
                                .defineFilter(new NoEventFilter()).build());
        
        final Kpi findKPIFacade =
                kpiService.findKPI(KpiKey.ofKpiNameAndEntity("PERSON_PRODUCTIVITY", person));
        Assert.assertNotNull(findKPIFacade);
        Assert.assertEquals(kpi, findKPIFacade);
        
    }
}
