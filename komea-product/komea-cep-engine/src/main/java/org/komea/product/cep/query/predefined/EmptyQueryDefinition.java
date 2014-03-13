/**
 * 
 */

package org.komea.product.cep.query.predefined;



import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.cep.api.IQueryDefinition;
import org.komea.product.cep.formula.CountFormula;



/**
 * Provides a stub for query definition.
 * 
 * @author sleroy
 */
public class EmptyQueryDefinition implements IQueryDefinition
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryDefinition#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        return Collections.emptyList();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryDefinition#getFormula()
     */
    @Override
    public ICEPFormula getFormula() {
    
    
        return new CountFormula<Serializable>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryDefinition#getName()
     */
    @Override
    public String getName() {
    
    
        return "emptyQuery";
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.IQueryDefinition#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return Collections.emptyMap();
    }
    
}
