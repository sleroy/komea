
package org.komea.product.backend.service.esper;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.database.model.Kpi;



/**
 */
public class QueryDefinitionTest
{
    
    
    @Test
    public final void testWithKPI() {
    
    
        final Kpi kpi = new Kpi();
        kpi.setEsperRequest("ESPER");
        final QueryDefinition queryDefinition = new QueryDefinition(kpi, "BLA");
        Assert.assertEquals(kpi.getEsperRequest(), queryDefinition.getQuery());
        Assert.assertEquals("BLA", queryDefinition.getName());
    }
    
    
    @Test
    public final void testWithStrings() {
    
    
        final QueryDefinition queryDefinition = new QueryDefinition("ESPER", "BLA");
        Assert.assertEquals("ESPER", queryDefinition.getQuery());
        Assert.assertEquals("BLA", queryDefinition.getName());
    }
}
