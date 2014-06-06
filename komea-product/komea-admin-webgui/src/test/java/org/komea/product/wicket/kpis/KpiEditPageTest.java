/**
 * 
 */

package org.komea.product.wicket.kpis;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;

/**
 * @author sleroy
 */
public class KpiEditPageTest {

	@Rule
	public final WicketTesterMethodRule	wicketRule	= new WicketTesterMethodRule();

	@Before
	public void before() {

		wicketRule.getApplicationContextMock().putBean(Mockito.mock(IEntityService.class));
		wicketRule.getApplicationContextMock().putBean(Mockito.mock(IProviderService.class));
		wicketRule.getApplicationContextMock().putBean(Mockito.mock(IKPIService.class));
		wicketRule.getApplicationContextMock().putBean(Mockito.mock(IGroovyEngineService.class));

	}

	/**
	 * Test method for
	 * {@link org.komea.product.wicket.kpis.KpiEditPage#KpiEditPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
	 * .
	 */
	@Test
	public final void testKpiEditPagePageParameters() throws Exception {

		wicketRule.testStart(KpiEditPage.class);
	}

	/**
	 * Test method for
	 * {@link org.komea.product.wicket.kpis.KpiEditPage#KpiEditPage(org.apache.wicket.request.mapper.parameter.PageParameters, org.komea.product.database.model.Kpi)}
	 * .
	 */
	@Test
	public void testKpiEditPagePageParametersKpi() throws Exception {

		final WicketTester newWicketTester = wicketRule.newWicketTester();
		try {
			newWicketTester.startPage(new KpiEditPage(new PageParameters(), new Kpi()));
		} finally {
			newWicketTester.destroy();
		}
	}
}
