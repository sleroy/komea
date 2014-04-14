/**
 * 
 */

package org.komea.product.plugins.kpi.filters;



import org.komea.eventory.api.filters.IEventFilter;
import org.komea.product.database.alert.IEvent;



/**
 * This class filters metric_value sonar events with given metric name.
 * 
 * @author sleroy
 */
public class SonarMetricEventFilter implements IEventFilter<IEvent>
{
    
    
    private final String metricName;
    
    
    
    /**
     * @param _metricName
     */
    public SonarMetricEventFilter(final String _metricName) {
    
    
        super();
        metricName = _metricName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final IEvent _event) {
    
    
        return "metric_value".equals(_event.getEventType().getEventKey())
                && metricName.equals(_event.getProperties().get("metricName"));
    }
    
}
