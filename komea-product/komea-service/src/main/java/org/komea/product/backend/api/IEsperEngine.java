
package org.komea.product.backend.api;



import org.komea.product.database.alert.IAlert;

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
    EPStatement createEPL(String _name, String _query);
    
    
    boolean existEPL(String _metricKey);
    
    
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
    
    
    EPStatement getStatementOrFail(String _measureName);
    
    
    /**
     * Registers a esper query.
     * 
     * @param _query
     *            the query.
     * @param _statementName
     *            the statement name.
     */
    void createOrUpdateEPL(String _query, String _statementName);
    
    
    /**
     * Push alerts
     * 
     * @param _alert
     */
    void sendAlert(IAlert _alert);
    
}
