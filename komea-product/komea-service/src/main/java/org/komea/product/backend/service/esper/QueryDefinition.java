
package org.komea.product.backend.service.esper;



import org.komea.product.backend.service.business.IQueryDefinition;
import org.komea.product.database.model.Kpi;



/**
 */
public final class QueryDefinition implements IQueryDefinition
{
    
    
    private final String query;
    private final String statementName;
    
    
    
    /**
     * Constructor for QueryDefinition.
     * @param _kpi Kpi
     */
    public QueryDefinition(final Kpi _kpi) {
    
    
        this(_kpi, _kpi.computeKPIEsperKey());
    }
    
    
    /**
     * Constructor for QueryDefinition.
     * @param _kpi Kpi
     * @param _statementName String
     */
    public QueryDefinition(final Kpi _kpi, final String _statementName) {
    
    
        query = _kpi.getEsperRequest();
        statementName = _statementName;
    }
    
    
    /**
     * Constructor for QueryDefinition.
     * @param _esperQuery String
     * @param _statementName String
     */
    public QueryDefinition(final String _esperQuery, final String _statementName) {
    
    
        query = _esperQuery;
        statementName = _statementName;
    }
    
    
    /**
     * Method getName.
     * @return String
     * @see org.komea.product.backend.service.business.IQueryDefinition#getName()
     */
    @Override
    public String getName() {
    
    
        return statementName;
    }
    
    
    /**
     * Method getQuery.
     * @return String
     * @see org.komea.product.backend.service.business.IQueryDefinition#getQuery()
     */
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
