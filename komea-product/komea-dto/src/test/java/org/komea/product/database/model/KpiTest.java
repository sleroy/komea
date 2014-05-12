/**
 * 
 */

package org.komea.product.database.model;



import org.junit.Test;
import org.komea.product.database.enums.EntityType;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class KpiTest
{
    
    
    /**
     * Test method for {@link org.komea.product.database.model.Kpi#computeKPIEsperKey()}.
     */
    @Test
    public void testComputeKPIEsperKey() throws Exception {
    
    
        final Kpi kpi = new Kpi();
        kpi.setId(12);
        kpi.setName("name");
        kpi.setKpiKey("key");
        System.out.println(kpi.computeKPIEsperKey());
        assertEquals("KPI_key", kpi.computeKPIEsperKey());
        kpi.setEntityType(EntityType.DEPARTMENT);
        System.out.println(kpi.computeKPIEsperKey());
        assertEquals("KPI_key_T_DEPARTMENT" + "", kpi.computeKPIEsperKey());
        
    }
    
}
