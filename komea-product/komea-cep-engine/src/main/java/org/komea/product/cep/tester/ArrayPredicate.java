/**
 * 
 */

package org.komea.product.cep.tester;



import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.product.cep.api.tester.ICEPQueryTestPredicate;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.service.dto.EntityKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;



/**
 */
class ArrayPredicate implements ICEPQueryTestPredicate<Serializable>
{
    
    
    private final Object[][] array;
    
    
    
    /**
     * @param _columnName
     * @param _expectedValue
     */
    public ArrayPredicate(final Object[][] _array) {
    
    
        super();
        array = _array;
        
    }
    
    
    /**
     * Method evaluate.
     * 
     * @param _epStatement
     *            EPStatement
     * @see org.komea.product.cep.api.tester.ICEPQueryTestPredicate#evaluate(EPStatement)
     */
    @Override
    public void evaluate(final ICEPQuery<Serializable, KpiResult> _epStatement) {
    
    
        final KpiResult listMapResult = _epStatement.getResult();
        CEPQueryTester.LOGGER.debug("Sorted table : {}", listMapResult.toString());
        final Map<EntityKey, Number> resultMap = listMapResult.getMap();
        assertThat("Expected same number of rows", resultMap.size(), equalTo(array.length));
        
        
        for (int i = 0; i < array.length; ++i) {
            assertThat("Array should have 2 columns [entityKey, number]", array[i].length,
                    equalTo(2));
            CEPQueryTester.LOGGER.debug("Evaluating line {} of esper request", i);
            final EntityKey entityKey = (EntityKey) array[i][0];
            final Number tuple = resultMap.get(entityKey);
            System.out.println(Arrays.toString(array[i]));
            assertThat("Expect value for the iteration " + i + " and entity " + entityKey, tuple,
                    equalTo(array[i][1]));
            
        }
        
    }
}
