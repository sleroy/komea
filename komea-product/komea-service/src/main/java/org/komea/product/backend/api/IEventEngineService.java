
package org.komea.product.backend.api;



import org.komea.product.backend.service.esper.IQueryDefinition;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.database.alert.IEvent;



/**
 */
public interface IEventEngineService
{
    
    
    /**
     * Creates esper Statement.
     * 
     * @param _queryDefinition
     *            IQueryDefinition
     * @return the esper compiled statement.
     */
    ICEPQuery createEPL(IQueryDefinition _queryDefinition);
    
    
    /**
     * Registers a esper query.
     * 
     * @param _queryDefinition
     *            IQueryDefinition
     */
    void createOrUpdateEPLQuery(IQueryDefinition _queryDefinition);
    
    
    /**
     * Tests if a statement exist.
     * 
     * @param _statementKey
     *            the statement key.
     * @return boolean
     */
    boolean existEPL(String _statementKey);
    
    
    /**
     * Returns the given statement or null.
     * 
     * @param _statementName
     * @return EPStatement
     */
    ICEPQuery getStatement(String _statementName);
    
    
    /**
     * Returns the list of statement names.
     * 
     * @return the statement names.
     */
    String[] getStatementNames();
    
    
    /**
     * Returns the statement or throws an exception.
     * 
     * @param _statement
     *            the statement name
     * @return the esper statement
     */
    ICEPQuery getStatementOrFail(String _statement);
    
    
    /**
     * Push alerts
     * 
     * @param _event
     */
    void sendEvent(IEvent _event);
    
}
