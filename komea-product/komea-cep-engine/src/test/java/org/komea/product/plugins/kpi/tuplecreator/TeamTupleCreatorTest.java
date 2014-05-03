/**
 * 
 */

package org.komea.product.plugins.kpi.tuplecreator;



import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.plugins.kpi.formula.TeamFormulaTest;
import org.komea.product.service.dto.EntityKey;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class TeamTupleCreatorTest
{
    
    
    /**
     * Test method for {@link org.komea.product.plugins.kpi.tuplecreator.TeamTupleCreator#create(org.komea.product.database.alert.IEvent)}.
     */
    @Test
    public void testCreate() throws Exception {
    
    
        final TeamTupleCreator tupleCreator = new TeamTupleCreator();
        final EntityKey create = tupleCreator.create(TeamFormulaTest.fakeEvent());
        assertEquals(Integer.valueOf(1), create.getId());
        assertEquals(EntityType.TEAM, create.getEntityType());
        
        
    }
    
}
