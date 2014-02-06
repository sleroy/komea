
package org.komea.product.backend.service.esper;



import org.komea.product.backend.service.business.IQueryDefinition;
import org.komea.product.database.model.Kpi;



public final class QueryDefinition implements IQueryDefinition
{
    
    
    private final String query;
    private final String statementName;
    
    
    
    public QueryDefinition(final Kpi _kpi, final String _statementName) {
    
    
        query = _kpi.getEsperRequest();
        statementName = _statementName;
    }
    
    
    public QueryDefinition(final String _query, final String _statementName) {
    
    
        query = _query;
        statementName = _statementName;
    }
    
    
    @Override
    public String getName() {
    
    
        return statementName;
    }
    
    
    @Override
    public String getQuery() {
    
    
        return query;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "QueryDefinition [query=" + query + ", statementName=" + statementName + "]";
    }
}
