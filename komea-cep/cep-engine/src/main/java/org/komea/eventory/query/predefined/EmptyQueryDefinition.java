/**
 * 
 */

package org.komea.eventory.query.predefined;



import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.eventory.formula.CountFormula;
import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.api.IFilterDefinition;



/**
 * Provides a stub for query definition.
 * 
 * @author sleroy
 */
public class EmptyQueryDefinition implements ICEPQueryImplementation
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        return Collections.emptyList();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula getFormula() {
    
    
        return new CountFormula<Serializable>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.ICEPQueryImplementation#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return Collections.emptyMap();
    }
    
}
