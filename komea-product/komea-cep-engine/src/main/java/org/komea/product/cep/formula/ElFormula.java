
package org.komea.product.cep.formula;



import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.product.cep.api.formula.IElFormula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;



/**
 * Defines a Spring formula
 */
public class ElFormula<T> implements IElFormula<T>
{
    
    
    private static final Logger       LOGGER    = LoggerFactory.getLogger(ElFormula.class);
    
    final static ExpressionParser     expParser = new SpelExpressionParser();
    
    private final String              formula;
    
    private final Map<String, Method> methods   = new HashMap<String, Method>();
    
    private Expression                parseExpression;
    
    
    private final Class<T>            value;
    
    
    
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
    public T getValue(final Object _context) throws SpelEvaluationException {
    
    
        return getValue(_context, null);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.formula.IElFormula#getValue(java.lang.Object)
     */
    @Override
    public T getValue(final Object _context, final Map<String, Object> _parameters)
            throws SpelEvaluationException {
    
    
        Object valuecontext = _context;
        if (_context == null) {
            valuecontext = new Object();
        }
        final StandardEvaluationContext context = new StandardEvaluationContext(valuecontext);
        for (final Map.Entry<String, Method> method : methods.entrySet()) {
            context.registerFunction(method.getKey(), method.getValue());
        }
        
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
    
    
    /**
     * Register a method
     * 
     * @param _name
     *            the name
     * @param _method
     *            the method.
     */
    public void registerMethod(final String _name, final Method _method) {
    
    
        methods.put(_name, _method);
    }
    
    
}
