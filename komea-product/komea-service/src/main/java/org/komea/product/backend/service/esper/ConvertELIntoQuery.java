/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.komea.product.cep.api.IQueryDefinition;
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
    public static IQueryDefinition parseEL(final String _formula) {
    
    
        final ElFormula<IQueryDefinition> elFormula =
                new ElFormula<IQueryDefinition>(_formula, IQueryDefinition.class);
        return elFormula.getValue(null);
    }
    
}
