
package org.komea.product.backend.service.esper;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;



/**
 */
public class ElFormula
{
    
    
    final static ExpressionParser expParser = new SpelExpressionParser();
    
    private Expression            parseExpression;
    
    private final String          formula;
    
    private static final Logger   LOGGER    = LoggerFactory.getLogger(ElFormula.class);
    
    
    
    /**
     * El Formula.
     * 
     * @param _formula
     */
    public ElFormula(final String _formula) {
    
    
        super();
        formula = _formula;
        parseExpression = null;
        try {
            parseExpression = expParser.parseExpression(_formula);
        } catch (final Exception e) {
            LOGGER.error("Formula cannot be parsed : " + _formula, e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
        
    }
    
    
    /**
     * Method getFormula.
     * @return String
     */
    public String getFormula() {
    
    
        return formula;
    }
    
    
    /**
     * Method getValue.
     * @param _context Object
     * @param _value Class<T>
     * @return T
     */
    public <T> T getValue(final Object _context, final Class<T> _value) {
    
    
        try {
            return parseExpression.getValue(_context, _value);
        } catch (final Exception e) {
            LOGGER.debug("Formula| Error in the formula : " + formula);
            LOGGER.debug("Formula| Reason : " + e.getMessage());
            
            throw new IllegalArgumentException(e);
        }
    }
}
