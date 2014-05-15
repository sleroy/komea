/**
 *
 */
package org.komea.product.wicket.kpicharts;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.komea.product.wicket.kpichart.KpiChartPage;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
public class KpiPageTest extends AbstractSpringIntegrationTestCase {

	@Rule
	public final WicketTesterMethodRule	wicketRule	= new WicketTesterMethodRule();

	@Autowired
	private IStatisticsAPI	            statisticsAPI;
	@Autowired
	private IEntityService	            entityService;

	@Autowired
	private IKPIService	                kpiService;

	@Before
	public void before() {

		wicketRule.getApplicationContextMock().putBean(statisticsAPI);
		wicketRule.getApplicationContextMock().putBean(entityService);
		wicketRule.getApplicationContextMock().putBean(kpiService);

	}

	/**
	 * Test method for
	 * {@link org.komea.product.wicket.kpis.KpiPage#KpiPage(org.apache.wicket.request.mapper.parameter.PageParameters)}
	 * .
	 */
	@Test
	public final void testKpiPage() throws Exception {

		wicketRule.testStart(KpiChartPage.class);
	}

}
