/**
 * 
 */

package org.komea.product.backend.service.olap;



import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.komea.product.backend.api.IKpiMathService;
import org.komea.product.database.model.Measure;
import org.springframework.stereotype.Service;



/**
 * This interface defines the math functions employed by KPI and measures
 * 
 * @author sleroy
 */
@Service
public class KpiMathService implements IKpiMathService
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiMathService#computeAverageFromMeasures(java.util.List)
     */
    @Override
    public double computeAverageFromMeasures(final List<Measure> _measures) {
    
    
        Validate.notNull(_measures);
        final Mean mean = new Mean();
        final double[] values = convertMeasuresInDoubleArray(_measures);
        mean.setData(values);
        return mean.evaluate();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiMathService#computeSumFromMeasures(java.util.List)
     */
    @Override
    public double computeSumFromMeasures(final List<Measure> _measures) {
    
    
        Validate.notNull(_measures);
        final Sum sum = new Sum();
        return sum.evaluate(convertMeasuresInDoubleArray(_measures));
    }
    
    
    /**
     * Convert the list of measures into a double array.
     * 
     * @param _measures
     *            the measures
     * @return the double array.
     */
    private double[] convertMeasuresInDoubleArray(final List<Measure> _measures) {
    
    
        Validate.notNull(_measures);
        final double[] array = new double[_measures.size()];
        for (int i = 0; i < _measures.size(); ++i) {
            array[i] = _measures.get(i).getValue().doubleValue();
        }
        return array;
    }
    
}
