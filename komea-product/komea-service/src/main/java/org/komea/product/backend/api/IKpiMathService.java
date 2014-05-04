/**
 * 
 */

package org.komea.product.backend.api;



import java.util.List;

import org.komea.product.database.model.Measure;



/**
 * @author sleroy
 */
public interface IKpiMathService
{
    
    
    /**
     * Compute the average from measures.
     * 
     * @param _measures
     *            the measures
     * @return the measure returned.
     */
    public double computeAverageFromMeasures(List<Measure> _measures);
    
    
    /**
     * Compute the sum from a list of measures
     * 
     * @param _measures
     *            the measures
     * @return the sum
     */
    public double computeSumFromMeasures(List<Measure> _measures);
    
}
