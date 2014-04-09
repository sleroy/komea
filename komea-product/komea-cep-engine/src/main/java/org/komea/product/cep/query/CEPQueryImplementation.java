/**
 * 
 */

package org.komea.product.cep.query;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.api.IFilterDefinition;



/**
 * This class defines the implementation necessary to instantiate a cep query
 * 
 * @author sleroy
 */
public class CEPQueryImplementation implements ICEPQueryImplementation
{
    
    
    private final List<IFilterDefinition> filterDefinitions = new ArrayList<IFilterDefinition>();
    
    
    private ICEPFormula                   formula;
    
    
    private Map<String, Object>           parameters        = Collections.EMPTY_MAP;
    
    
    
    /**
     * @param _queryName
     *            the query name.
     */
    public CEPQueryImplementation() {
    
    
        super();
    }
    
    
    /**
     * @param _filterDefinition
     */
    public void addFilterDefinition(final FilterDefinition _filterDefinition) {
    
    
        filterDefinitions.add(_filterDefinition);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
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
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "CEPQueryImplementation [filterDefinitions="
                + filterDefinitions + ", formula=" + formula + ", parameters=" + parameters + "]";
    }
    
    
}
