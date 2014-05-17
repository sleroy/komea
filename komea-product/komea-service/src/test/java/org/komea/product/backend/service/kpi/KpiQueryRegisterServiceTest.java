/**
 * 
 */

package org.komea.product.backend.service.kpi;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.komea.product.backend.api.IDynamicQueryCacheService;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IQueryInformations;
import org.komea.product.backend.service.ISpringService;
import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.model.Kpi;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class KpiQueryRegisterServiceTest {

	@Mock
	private IDynamicDataQueryRegisterService	dynamicDataQueryRegisterService;

	@Mock
	private IDynamicQueryCacheService	     dynamicQueryCacheService;

	@Mock
	private IEventEngineService	             esperEngine;

	@InjectMocks
	private KpiQueryRegisterService	         kpiQueryRegisterService;
	@Mock
	private ISpringService	                 springService;

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.kpi.KpiQueryRegisterService#registerQuery(org.komea.product.database.model.Kpi, java.lang.Object)}
	 * .
	 */
	@Test
	public void testRegisterCEPQuery() throws Exception {

		final Kpi kpi = new Kpi();
		kpi.setEsperRequest("Formula");
		final ICEPQueryImplementation mock = mock(ICEPQueryImplementation.class);
		kpiQueryRegisterService.registerQuery(kpi, mock);
		final ArgumentCaptor<IQueryInformations> argumentCaptor = ArgumentCaptor.forClass(IQueryInformations.class);
		verify(esperEngine, times(1)).createOrUpdateQuery(argumentCaptor.capture());
		assertEquals(kpi.getEsperRequest(), argumentCaptor.getValue().getQueryName());

	}

	/**
	 * Test method for
	 * {@link org.komea.product.backend.service.kpi.KpiQueryRegisterService#registerQuery(org.komea.product.database.model.Kpi, java.lang.Object)}
	 * .
	 */
	@Test
	public void testRegisterDynamicQuery() throws Exception {

		final Kpi kpi = new Kpi();
		kpi.setEsperRequest("Formula");
		final IDynamicDataQuery mock = mock(IDynamicDataQuery.class);
		when(dynamicQueryCacheService.addCacheOnDynamicQuery(kpi.getEsperRequest(), mock)).thenReturn(mock);
		kpiQueryRegisterService.registerQuery(kpi, mock);
		verify(dynamicDataQueryRegisterService, times(1)).registerQuery(kpi.getEsperRequest(), mock);
		verify(dynamicQueryCacheService, times(1)).addCacheOnDynamicQuery(kpi.getEsperRequest(), mock);

	}
}
