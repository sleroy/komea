
package org.komea.product.backend.service.esper;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import jodd.bean.BeanUtil;

import org.springframework.beans.BeanUtils;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;



public class EPStatementResult
{
    
    
    public static EPStatementResult build(@NotNull
    final EPStatement _epStatement) {
    
    
        return new EPStatementResult(_epStatement);
    }
    
    
    
    private final EPStatement statement;
    
    
    
    private EPStatementResult(final EPStatement _statement) {
    
    
        super();
        statement = _statement;
    }
    
    
    /**
     * Returns a list of result from the EP Statement and a property expression.
     * 
     * @param _propertyName
     *            the property expression
     * @return the list of values or an empty list..
     */
    public <T> List<Map<String, T>> listMapResult() {
    
    
        final List<Map<String, T>> res = new ArrayList<Map<String, T>>(100);
        SafeIterator<EventBean> safeIterator = null;
        try {
            safeIterator = statement.safeIterator();
            if (safeIterator.hasNext()) {
                final EventBean next = safeIterator.next();
                res.add((Map<String, T>) next.getUnderlying());
            }
            
        } finally {
            safeIterator.close();
        }
        return res;
    }
    
    
    /**
     * Returns a list of result from the EP Statement and a property expression.
     * 
     * @param _propertyName
     *            the property expression
     * @return the list of values or an empty list..
     */
    public <T> List<T> listMapResult(final Class<T> _implementationClass) {
    
    
        final List<T> res = new ArrayList<T>(100);
        SafeIterator<EventBean> safeIterator = null;
        try {
            safeIterator = statement.safeIterator();
            if (safeIterator.hasNext()) {
                final EventBean next = safeIterator.next();
                final T beanPojo = BeanUtils.instantiate(_implementationClass);
                final Map<String, Object> propertyMap = (Map<String, Object>) next.getUnderlying();
                BeanUtil.populateBean(beanPojo, propertyMap);
                res.add(beanPojo);
            }
        } finally {
            safeIterator.close();
        }
        return res;
    }
    
    
    /**
     * Returns a list of result from the EP Statement and a property expression.
     * 
     * @param _propertyName
     *            the property expression
     * @return the list of values or an empty list..
     */
    public <T> List<T> listResult(final String _propertyName) {
    
    
        final List<T> res = new ArrayList<T>(100);
        SafeIterator<EventBean> safeIterator = null;
        try {
            safeIterator = statement.safeIterator();
            if (safeIterator.hasNext()) {
                final EventBean next = safeIterator.next();
                res.add((T) next.get(_propertyName));
            }
            
        } finally {
            safeIterator.close();
        }
        return res;
    }
    
    
    /**
     * This method should be use with great care, the statement must always returns a value.
     * 
     * @return
     */
    public <T> T singleResult() {
    
    
        T res = null;
        SafeIterator<EventBean> safeIterator = null;
        try {
            safeIterator = statement.safeIterator();
            if (safeIterator.hasNext()) {
                final EventBean next = safeIterator.next();
                res = (T) ((Map) next.getUnderlying()).values().iterator().next();
            }
            
        } finally {
            safeIterator.close();
        }
        return res;
    }
    
    
    /**
     * Returns a single result from the EP Statement
     * 
     * @param _propertyName
     *            the property expression
     * @return the value or null.
     */
    public <T> T singleResult(final String _propertyName) {
    
    
        T res = null;
        SafeIterator<EventBean> safeIterator = null;
        try {
            safeIterator = statement.safeIterator();
            if (safeIterator.hasNext()) {
                final EventBean next = safeIterator.next();
                res = (T) next.get(_propertyName);
            }
            
        } finally {
            safeIterator.close();
        }
        return res;
    }
}
