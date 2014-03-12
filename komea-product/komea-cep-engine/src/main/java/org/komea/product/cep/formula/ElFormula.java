
package org.komea.product.cep.formula;



import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.formula.IElFormula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;



/**
 * Defines a Spring formula
 */
public class ElFormula<T> implements IElFormula<T>
{
    
    
    private static final Logger   LOGGER    = LoggerFactory.getLogger(ElFormula.class);
    
    final static ExpressionParser expParser = new SpelExpressionParser();
    
    private final String          formula;
    
    private Expression            parseExpression;
    
    private final Class<T>        value;
    
    
    
    /**
     * El Formula.
     * 
     * @param _formula
     */
    public ElFormula(final String _formula, final Class<T> _value) {
    
    
        super();
        formula = _formula;
        value = _value;
        parseExpression = null;
        try {
            parseExpression = expParser.parseExpression(_formula);
        } catch (final Exception e) {
            LOGGER.error("Formula cannot be parsed : " + _formula, e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.formula.IElFormula#getFormula()
     */
    @Override
    public String getFormula() {
    
    
        return formula;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.formula.IElFormula#getValue(java.lang.Object)
     */
    @Override
    public T getValue(final Object _context) {
    
    
        return getValue(_context, null);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.formula.IElFormula#getValue(java.lang.Object)
     */
    @Override
    public T getValue(final Object _context, final Map<String, Object> _parameters) {
    
    
        Validate.notNull(_context);
        final StandardEvaluationContext context = new StandardEvaluationContext(_context);
        prepareCustomMethods(context);
        if (_parameters != null) {
            for (final Entry<String, Object> entry : _parameters.entrySet()) {
                context.setVariable(entry.getKey(), entry.getValue());
            }
        }
        try {
            
            
            return parseExpression.getValue(context, value);
        } catch (final Exception e) {
            LOGGER.warn("Formula| Error in the formula : " + formula);
            LOGGER.warn("Formula| Reason : " + e.getMessage());
            LOGGER.warn("Result is {}", parseExpression.getValue(_context));
            
            throw new IllegalArgumentException(e);
        }
    }
    
    
    private void prepareCustomMethods(final StandardEvaluationContext context) {
    
    
        try {
            registerInMethod(context);
        } catch (final NoSuchMethodException e) {
            LOGGER.error("Error with custom method", e);
            
        } catch (final SecurityException e) {
            LOGGER.error("Error with custom method", e);
        }
    }
    
    
    private void registerInMethod(final StandardEvaluationContext context)
            throws NoSuchMethodException {
    
    
        final Method method = ELOperator.class.getMethod("in", new Class[] {
                Object.class, Object[].class });
        Validate.notNull(method);
        context.registerFunction("in",
        
        method);
    }
}
