/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import java.util.List;

import org.komea.product.service.dto.EventTypeStatistic;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
public class EventTypeStatistics
{
    
    
    private List<EventTypeStatistic> eventTypeStatistics = Lists.newArrayList();
    
    
    
    public void add(final EventTypeStatistic _eventTypeStatistic) {
    
    
        eventTypeStatistics.add(_eventTypeStatistic);
    }
    
    
    public List<EventTypeStatistic> getEventTypeStatistics() {
    
    
        return eventTypeStatistics;
    }
    
    
    public void setEventTypeStatistics(final List<EventTypeStatistic> _eventTypeStatistics) {
    
    
        eventTypeStatistics = _eventTypeStatistics;
    }
}
