/**
 * 
 */

package org.komea.product.plugins.kpi.tuplecreator;



import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.plugins.kpi.formula.DepartmentFormulaTest;
import org.komea.product.service.dto.EntityKey;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class DepartmentTupleCreatorTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.kpi.tuplecreator.DepartmentTupleCreator#create(org.komea.product.database.alert.IEvent)}.
     */
    @Test
    public final void testCreate() throws Exception {
    
    
        final DepartmentTupleCreator tupleCreator = new DepartmentTupleCreator();
        final EntityKey create = tupleCreator.create(DepartmentFormulaTest.fakeEvent());
        assertEquals(Integer.valueOf(1), create.getId());
        assertEquals(EntityType.DEPARTMENT, create.getEntityType());
    }
    
}
