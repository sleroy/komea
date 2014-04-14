/**
 *
 */
package org.komea.product.wicket.kpis;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 * @author sleroy
 */
public class KpiPageTest {

    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();

    @Before
    public void before() {

        final IKPIService service = Mockito.mock(IKPIService.class);
        final List<Kpi> values = new ArrayList<Kpi>();
        final Kpi kpi = new Kpi();
        kpi.setName("KPI_VALUE");
        values.add(kpi);
        when(service.selectAll()).thenReturn(values);

        wicketRule.getApplicationContextMock().putBean(service);

    }

    /**
     * Test method for
     * {@link org.komea.product.wicket.kpis.KpiPage#KpiPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test 
    public final void testKpiPage() throws Exception {

        wicketRule.testStart(KpiPage.class);
    }

}
