/**
 * 
 */

package org.komea.product.backend.criterias;



import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.service.dto.KpiKey;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class FindKpiOrFailTest
{
    
    
    @Mock
    private KpiDao kpiDao;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.criterias.FindKpiOrFail#find()}.
     */
    @Test
    public final void testFind() throws Exception {
    
    
        final Kpi kpi = new Kpi();
        when(kpiDao.selectByCriteriaWithBLOBs(Matchers.any(KpiCriteria.class))).thenReturn(
                Collections.singletonList(kpi));
        final FindKpi findKpi = new FindKpiOrFail(KpiKey.ofKpiName("KPI"), kpiDao);
        findKpi.find();
        verify(kpiDao, times(1)).selectByCriteriaWithBLOBs(Matchers.any(KpiCriteria.class));
    }
}
