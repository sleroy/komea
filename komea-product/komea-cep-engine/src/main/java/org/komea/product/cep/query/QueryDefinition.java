/**
 * 
 */

package org.komea.product.cep.query;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.cep.api.IQueryDefinition;



/**
 * @author sleroy
 */
public class QueryDefinition implements IQueryDefinition
{
    
    
    private final List<IFilterDefinition> filterDefinitions = new ArrayList<IFilterDefinition>();
    
    
    private ICEPFormula                   formula;
    
    
    private Map<String, Object>           parameters        = Collections.EMPTY_MAP;
    
    private String                        queryName;
    
    
    
    /**
     * @param _queryName
     *            the query name.
     */
    public QueryDefinition(final String _queryName) {
    
    
        queryName = _queryName;
    }
    
    
    /**
     * @param _filterDefinition
     */
    public void addFilterDefinition(final FilterDefinition _filterDefinition) {
    
    
        filterDefinitions.add(_filterDefinition);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryDefinition#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        return filterDefinitions;
    }
    
    
    /**
     * @return the formula
     */
    @Override
    public ICEPFormula getFormula() {
    
    
        return formula;
    }
    
    
    @Override
    public String getName() {
    
    
        return queryName;
    }
    
    
    /**
     * @return the parameters
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return parameters;
    }
    
    
    /**
     * @param _formula
     *            the formula to set
     */
    public void setFormula(final ICEPFormula _formula) {
    
    
        formula = _formula;
    }
    
    
    /**
     * @param _parameters
     */
    public void setParameters(final Map<String, Object> _parameters) {
    
    
        parameters = _parameters;
        
        
    }
    
    
    public void setQueryName(final String _queryName) {
    
    
        queryName = _queryName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "QueryDefinition [filterDefinitions="
                + filterDefinitions + ", formula=" + formula + ", parameters=" + parameters
                + ", queryName=" + queryName + "]";
    }
    
    
}
