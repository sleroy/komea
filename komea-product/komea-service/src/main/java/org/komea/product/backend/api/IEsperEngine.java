
package org.komea.product.backend.api;



import org.komea.product.backend.service.esper.IQueryDefinition;
import org.komea.product.database.alert.IEvent;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;



/**
 */
public interface IEsperEngine
{
    
    
    /**
     * Creates esper Statement.
     * 
    
    
    
     * @param _queryDefinition IQueryDefinition
     * @return the esper compiled statement. */
    EPStatement createEPL(IQueryDefinition _queryDefinition);
    
    
    /**
     * Registers a esper query.
     * 
    
    
     * @param _queryDefinition IQueryDefinition
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
     * Returns the espere engine service provider.
     * 
    
     * @return the esper engine service provider. */
    EPServiceProvider getEsper();
    
    
    /**
     * Returns the given statement or null.
     * 
     * @param _statementName
     * @return EPStatement
     */
    EPStatement getStatement(String _statementName);
    
    
    /**
     * Returns the list of statement names.
     * 
    
     * @return the statement names. */
    String[] getStatementNames();
    
    
    /**
     * Returns the statement or throws an exception.
     * 
     * @param _statement
     *            the statement name
    
     * @return the esper statement */
    EPStatement getStatementOrFail(String _statement);
    
    
    /**
     * Push alerts
     * 
     * @param _event
     */
    void sendEvent(IEvent _event);
    
}
