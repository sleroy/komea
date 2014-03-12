
package org.komea.product.backend.service.esper;



import java.util.List;

import javax.validation.constraints.NotNull;

import org.komea.product.cep.api.ICEPQuery;



/**
 * This class is a builder to read/ convert CEPQuery results.
 */
public final class CEPQueryResultConvertor
{
    
    
    /**
     * Method build.
     * 
     * @param _epStatement
     *            ICEPQuery
     * @return ICEPQueryResult
     */
    public static CEPQueryResultConvertor build(@NotNull
    final ICEPQuery _epStatement) {
    
    
        return new CEPQueryResultConvertor(_epStatement);
    }
    
    
    
    private final ICEPQuery statement;
    
    
    
    /**
     * Constructor for ICEPQueryResult.
     * 
     * @param _statement
     *            ICEPQuery
     */
    private CEPQueryResultConvertor(final ICEPQuery _statement) {
    
    
        super();
        statement = _statement;
    }
    
    
    /**
     * Method listUnderlyingObjects.
     * 
     * @return List<T>
     */
    public <T> List<T> listUnderlyingObjects() {
    
    
        return (List<T>) statement.getStatement().getAggregateView();
    }
    
    
    /**
     * This method should be use with great care, the statement must always returns a value.
     * 
     * @return T
     */
    public <T> T singleResult() {
    
    
        return statement.getResult().asType();
    }
}
