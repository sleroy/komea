/**
 * 
 */

package org.komea.product.plugins.kpi.tuplecreator;



import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.plugins.kpi.formula.ProjectFormulaTest;
import org.komea.product.service.dto.EntityKey;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class ProjectTupleCreatorTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.kpi.tuplecreator.ProjectTupleCreator#create(org.komea.product.database.alert.IEvent)}.
     */
    @Test
    public final void testCreate() throws Exception {
    
    
        final ProjectTupleCreator tupleCreator = new ProjectTupleCreator();
        final EntityKey create = tupleCreator.create(ProjectFormulaTest.fakeEvent());
        assertEquals(Integer.valueOf(1), create.getId());
        assertEquals(EntityType.PROJECT, create.getEntityType());
        
    }
    
}
