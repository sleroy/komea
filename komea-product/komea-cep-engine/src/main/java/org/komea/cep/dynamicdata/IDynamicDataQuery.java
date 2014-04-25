/**
 *
 */
package org.komea.cep.dynamicdata;

import org.komea.eventory.api.formula.ICEPResult;

/**
 * This interface defines the method provided by formulas working on dynamic
 * data (i.e database)
 *
 * @author sleroy
 */
public interface IDynamicDataQuery {

    /**
     * Returns the result of the query
     *
     * @return the result of the query
     */
    ICEPResult getResult();

    String getFormula();
}
