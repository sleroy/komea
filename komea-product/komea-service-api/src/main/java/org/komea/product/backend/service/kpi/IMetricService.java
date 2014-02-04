
package org.komea.product.backend.service.kpi;



/**
 * This interface defines the service to get a wrapper over an EPStatement.
 * 
 * @author sleroy
 */
public interface IMetricService
{
    
    
    /**
     * Returns a metric from a EPStatement.
     * 
     * @param _measureName
     *            the measure name.
     * @return the epstatement metric.
     */
    IEPMetric findMeasure(String _measureName);
}
