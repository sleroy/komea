/**
 *
 */

package org.komea.cep.dynamicdata;



import org.komea.product.database.dto.KpiResult;



/**
 * This interface defines the method provided by formulas working on dynamic
 * data (i.e database)
 * 
 * @author sleroy
 */
public interface IDynamicDataQuery
{
    
    
    /**
     * Returns the formula
     */
    String getFormula();
    
    
    /**
     * Returns the result of the query
     * 
     * @return the result of the query
     */
    KpiResult getResult();
}
