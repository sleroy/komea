
package org.komea.product.backend.service.esper.stats;



import java.util.Map.Entry;

import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.product.cep.api.formula.IEventGroup;
import org.komea.product.cep.api.formula.IEventGroupFormula;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.formula.EventTable;
import org.komea.product.database.alert.IEvent;
import org.komea.product.service.dto.EventTypeStatistic;



/**
 * @author sleroy
 */
public class ProviderFormula implements ICEPFormula<IEvent, EventTypeStatistics>
{
    
    
    public static class ProviderEventTypeTuple
    {
        
        
        private final String eventType;
        
        private final String providerName;
        
        
        
        /**
         * 
         */
        public ProviderEventTypeTuple(final String _providerName, final String _eventType) {
        
        
            providerName = _providerName;
            eventType = _eventType;
            
        }
        
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object obj) {
        
        
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ProviderEventTypeTuple other = (ProviderEventTypeTuple) obj;
            if (eventType == null) {
                if (other.eventType != null) {
                    return false;
                }
            } else if (!eventType.equals(other.eventType)) {
                return false;
            }
            if (providerName == null) {
                if (other.providerName != null) {
                    return false;
                }
            } else if (!providerName.equals(other.providerName)) {
                return false;
            }
            return true;
        }
        
        
        /**
         * @return the eventType
         */
        public String getEventType() {
        
        
            return eventType;
        }
        
        
        /**
         * @return the providerName
         */
        public String getProviderName() {
        
        
            return providerName;
        }
        
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
        
        
            final int prime = 31;
            int result = 1;
            result = prime * result + (eventType == null ? 0 : eventType.hashCode());
            result = prime * result + (providerName == null ? 0 : providerName.hashCode());
            return result;
        }
        
        
        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
        
        
            return "ProviderEventTypeTuple [eventType="
                    + eventType + ", providerName=" + providerName + "]";
        }
    }
    
    
    
    public static class ProviderEventTypeTupleCreator implements
            ITupleCreator<IEvent, ProviderEventTypeTuple>
    {
        
        
        /*
         * (non-Javadoc)
         * @see
         * org.komea.product.cep.api.formula.tuple.ITupleCreator#create(java
         * .io.Serializable)
         */
        @Override
        public ProviderEventTypeTuple create(final IEvent _event) {
        
        
            return new ProviderEventTypeTuple(_event.getProvider().getKey(), _event.getEventType()
                    .getKey());
            
        }
        
    }
    
    
    
    private final IEventGroupFormula<IEvent> eventValueFormula;
    
    
    
    /**
     * @param _eventValueFormula
     */
    public ProviderFormula(final IEventGroupFormula<IEvent> _eventValueFormula) {
    
    
        eventValueFormula = _eventValueFormula;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.api.formula.ICEPFormula#compute(org.komea.eventory
     * .api.engine.ICEPStatement, java.util.Map)
     */
    @Override
    public EventTypeStatistics compute(final ICEPStatement<IEvent> _arg0) {
    
    
        final EventTable<ProviderEventTypeTuple, IEvent> eventTable =
                new EventTable<ProviderEventTypeTuple, IEvent>(new ProviderEventTypeTupleCreator());
        eventTable.fill(_arg0.getAggregateView());
        
        final EventTypeStatistics eventTypeStatistics = new EventTypeStatistics();
        for (final Entry<ProviderEventTypeTuple, IEventGroup<IEvent>> eventGroupEntry : eventTable
                .iterator()) {
            final EventTypeStatistic eventTypeStatistic = new EventTypeStatistic();
            eventTypeStatistic.setProvider(eventGroupEntry.getKey().getProviderName());
            eventTypeStatistic.setType(eventGroupEntry.getKey().getEventType());
            eventTypeStatistic.setValue(eventValueFormula.evaluate(eventGroupEntry.getValue())
                    .intValue());
            eventTypeStatistics.add(eventTypeStatistic);
        }
        return eventTypeStatistics;
    }
}
