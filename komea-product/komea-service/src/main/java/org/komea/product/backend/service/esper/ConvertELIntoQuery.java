/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.formula.ElFormula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Utility class to convert a spring el into a cep query.
 * 
 * @author sleroy
 */
public class ConvertELIntoQuery
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertELIntoQuery.class);
    
    
    
    /**
     * Parses the formula into a query.
     * 
     * @param _formula
     *            the formula
     * @return true if the formula is valid.
     */
    public static boolean isValidFormula(final String _formula) {
    
    
        try {
            return parseEL(_formula) != null;
        } catch (final Exception e) {
            LOGGER.warn("Invalid formula {}", _formula);
        }
        return false;
        
    }
    
    
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
