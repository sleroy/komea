/**
 * 
 */

package org.komea.product.backend.exceptions;



import org.komea.product.database.model.Kpi;



/**
 * This class defines exception when a kpi does not provides a valid formula.
 * 
 * @author sleroy
 */
public class KpiProvidesInvalidFormulaException extends KomeaRuntimeException
{
    
    
    /**
     * This kpi does not provides a valid formula.
     * 
     * @param _kpi
     *            the kpi
     */
    public KpiProvidesInvalidFormulaException(final Kpi _kpi) {
    
    
        super("KPI " + _kpi + " does not provide a valid formula.");
    }
    
}
