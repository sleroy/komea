/**
 * 
 */

package org.komea.product.plugins.kpi.tuplecreator;



import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.plugins.kpi.formula.UserFormulaTest;
import org.komea.product.service.dto.EntityKey;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class UserTupleCreatorTest
{
    
    
    /**
     * Test method for {@link org.komea.product.plugins.kpi.tuplecreator.UserTupleCreator#create(org.komea.product.database.alert.IEvent)}.
     */
    @Test
    public final void testCreate() throws Exception {
    
    
        final UserTupleCreator userTupleCreator = new UserTupleCreator();
        final EntityKey create = userTupleCreator.create(UserFormulaTest.fakeEvent());
        assertEquals(Integer.valueOf(1), create.getId());
        assertEquals(EntityType.PERSON, create.getEntityType());
    }
    
}
