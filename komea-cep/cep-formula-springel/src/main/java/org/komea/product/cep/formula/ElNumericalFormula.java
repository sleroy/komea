
package org.komea.product.cep.formula;



import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.query.CEPResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;



/**
 * This class defines a CEP formular using spring expression language. The expression is evaluated for all elements
 * 
 * @author sleroy
 */
public class ElNumericalFormula<T extends Serializable> implements ICEPFormula<T>
{
    
    
    public static class Context
    {
        
        
        private Object              event;
        private Map<String, Object> params;
        private Number              previous = 0d;
        
        
        
        /**
         * Returns the event
         * 
         * @return the event.
         */
        public Object getEvent() {
        
        
            return event;
            
        }
        
        
        public Map<String, Object> getParams() {
        
        
            return params;
        }
        
        
        public Number getPrevious() {
        
        
            return previous;
        }
        
        
        public void setEvent(final Object _event) {
        
        
            event = _event;
        }
        
        
        public void setParams(final Map<String, Object> _params) {
        
        
            params = _params;
        }
        
        
        public void setPrevious(final Number _previous) {
        
        
            previous = _previous;
        }
    }
    
    
    
    private static final Logger       LOGGER    = LoggerFactory.getLogger(ElNumericalFormula.class);
    
    
    final static ExpressionParser     expParser = new SpelExpressionParser();
    
    private ElFormula<Number>         elFormula;
    
    
    private final Map<String, Object> parameters;
    
    
    
    /**
     * Build a numerical formula
     * 
     * @param _formula
     *            the formula
     */
    public ElNumericalFormula(final String _formula) {
    
    
        this(_formula, Collections.EMPTY_MAP);
    }
    
    
    /**
     * El Formula.
     * 
     * @param _formula
     *            the spring formula
     */
    public ElNumericalFormula(final String _formula, final Map<String, Object> _parameters) {
    
    
        super();
        Validate.notNull(_parameters);
        Validate.notNull(_formula);
        parameters = Collections.unmodifiableMap(_parameters);
        elFormula = new ElFormula<Number>(_formula, Number.class);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPFormula#compute(org.komea.product.cep.api.ICEPStatement, java.util.Map)
     */
    @Override
    public ICEPResult compute(
            final ICEPStatement<T> _statement,
            final Map<String, Object> _parameters) {
    
    
        Validate.notNull(_statement);
        Validate.notNull(_parameters, "Parameters must be provided");
        final List<T> eventView = _statement.getAggregateView();
        Validate.notNull(eventView);
        LOGGER.debug("Formula will be computed with {}", eventView.size());
        
        final Context context = new Context();
        context.setParams(new HashMap<String, Object>(parameters));
        context.getParams().putAll(_parameters);
        // Iteration on elements.
        for (final Object eventObject : eventView) {
            context.setEvent(eventObject);
            context.setPrevious(computeValue(context));
        }
        LOGGER.debug("Formula returns value {}", context.getPrevious());
        
        return CEPResult.buildFromNumber(context.getPrevious());
    }
    
    
    /**
     * Computes a value from a previous number and an event.
     * 
     * @param _event
     *            the event
     * @return the new value.
     */
    public Number computeValue(final Context _event) {
    
    
        try {
            return elFormula.getValue(_event);
        } catch (final Exception e) {
            LOGGER.debug("Formula| Error in the formula : " + elFormula.getFormula());
            LOGGER.debug("Formula| Reason : " + e.getMessage());
            
            throw new IllegalArgumentException(e);
        }
    }
    
    
    /**
     * Returns the spring formula.
     * 
     * @return the spring formula.
     */
    public String getFormula() {
    
    
        return elFormula.getFormula();
    }
    
}
