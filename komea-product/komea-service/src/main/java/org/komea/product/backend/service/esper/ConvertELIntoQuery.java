/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.formula.ElFormula;



/**
 * Utility class to convert a spring el into a cep query.
 * 
 * @author sleroy
 */
public class ConvertELIntoQuery
{
    
    
    /**
     * Parses the formula into a query.
     * 
     * @param _formula
     *            the formula
     * @return the query.
     */
    public static ICEPQueryImplementation parseEL(final String _formula) {
    
    
        final ElFormula<ICEPQueryImplementation> elFormula =
                new ElFormula<ICEPQueryImplementation>(_formula, ICEPQueryImplementation.class);
        return elFormula.getValue(null);
    }
    
}
