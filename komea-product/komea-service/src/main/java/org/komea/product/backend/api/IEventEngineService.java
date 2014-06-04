
package org.komea.product.backend.api;



import java.io.Serializable;

import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.database.alert.IEvent;



/**
 */
public interface IEventEngineService
{
    
    
    /**
     * Registers a esper query.
     * 
     * @param _queryInformations
     *            ICEPQueryImplementation
     */
    void createOrUpdateQuery(IQueryInformations _queryInformations);
    
    
    /**
     * Creates esper Statement.
     * 
     * @param _queryInformations
     *            ICEPQueryImplementation
     * @return the esper compiled statement.
     */
    IQuery createQuery(IQueryInformations _queryInformations);
    
    
    /**
     * Create query from informations
     * 
     * @param _queryName
     *            the query name
     * @param _queryImplementation
     *            the query implementation.
     */
    void createQueryFromInformations(String _queryName, ICEPQueryImplementation _queryImplementation);
    
    
    /**
     * Tests if a statement exist.
     * 
     * @param _statementKey
     *            the statement key.
     * @return boolean
     */
    boolean existQuery(FormulaID _statementKey);
    
    
    /**
     * Returns the given statement or null.
     * 
     * @param _statementName
     * @return EPStatement
     */
    <T extends IQuery> T getQuery(FormulaID _statementName);
    
    
    /**
     * Returns the list of statement names.
     * 
     * @return the statement names.
     */
    FormulaID[] getQueryNames();
    
    
    /**
     * Returns the statement or throws an exception.
     * 
     * @param _statement
     *            the statement name
     * @return the esper statement
     */
    <T extends IQuery> T getQueryOrFail(FormulaID _statement);
    
    
    /**
     * Removes the query
     * 
     * @param _formula
     *            the formula
     */
    void removeQuery(FormulaID _formula);
    
    
    /**
     * @param _customEvent
     */
    void sendCustomEvent(Serializable _customEvent);
    
    
    /**
     * Push alerts
     * 
     * @param _event
     *            the event
     */
    void sendEvent(IEvent _event);
    
}
