/**
 * 
 */

package org.komea.eventory.api.engine;



import java.io.Serializable;
import java.util.Map;

import org.komea.eventory.api.formula.ICEPFormula;



/**
 * This class defines a CEP query. A query is composed of :
 * <ul>
 * <li>A CEP Statement that filters and stores the required events to compute the query.</li>
 * <li>A CEP Formula that computes from a CEP Statement a table of results or an numerical value.</li>
 * </ul>
 * filters the events received by the CEP, stores them into an appropriate cache storage.
 * 
 * @author sleroy
 */
public interface ICEPQuery<TEvent extends Serializable, TRes>
{
    
    
    /**
     * Returns the formula employed to compute the query.
     * 
     * @return the formula.
     */
    ICEPFormula<TEvent, TRes> getFormula();
    
    
    /**
     * Returns the parameters of the query. The map may be changed by the user.
     * 
     * @return the parameters.
     */
    Map<String, Object> getParameters();
    
    
    /**
     * Returns the result.
     */
    TRes getResult();
    
    
    /**
     * Returns the CEP Statement that contains the events of the query.
     * 
     * @return the statement.
     */
    ICEPStatement<TEvent> getStatement();
    
    
    /**
     * Notify to the query a new event has been added.
     * 
     * @param _event
     *            the event.
     */
    void notifyEvent(Serializable _event);
}
