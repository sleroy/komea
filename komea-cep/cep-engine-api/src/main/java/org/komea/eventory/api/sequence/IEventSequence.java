/**
 * 
 */

package org.komea.eventory.api.sequence;



/**
 * This class returns an event sequence.
 * 
 * @author sleroy
 */
public interface IEventSequence<T>
{
    
    
    /**
     * Returns the nth event.
     */
    T get(int _number);
    
    
    /**
     * Returns the first event.
     * 
     * @return the first event.
     */
    T getFirst();
    
    
    /**
     * Returns the second event.
     * 
     * @return the second event;
     */
    T getSecond();
    
    
    /**
     * Returns the sequence size.
     * 
     * @return the sequence siae.
     */
    int size();
}
