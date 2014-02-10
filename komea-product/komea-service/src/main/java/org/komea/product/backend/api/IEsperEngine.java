
package org.komea.product.backend.api;



import org.komea.product.backend.service.business.IQueryDefinition;
import org.komea.product.database.alert.IEvent;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;



public interface IEsperEngine
{
    
    
    /**
     * Creates esper Statement.
     * 
     * @param _name
     *            the statement name
     * @param _query
     *            the statement query.
     * @return the esper compiled statement.
     */
    EPStatement createEPL(IQueryDefinition _queryDefinition);
    
    
    /**
     * Registers a esper query.
     * 
     * @param _query
     *            the query.
     * @param _statementName
     *            the statement name.
     */
    void createOrUpdateEPLQuery(IQueryDefinition _queryDefinition);
    
    
    /**
     * Tests if a statement exist.
     * 
     * @param _statementKey
     *            the statement key.
     */
    boolean existEPL(String _statementKey);
    
    
    /**
     * Returns the espere engine service provider.
     * 
     * @return the esper engine service provider.
     */
    EPServiceProvider getEsper();
    
    
    /**
     * Returns the given statement or null.
     * 
     * @param _statementName
     */
    EPStatement getStatement(String _statementName);
    
    
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
    EPStatement getStatementOrFail(String _statement);
    
    
    /**
     * Push alerts
     * 
     * @param _event
     */
    void sendAlert(IEvent _event);
    
}
