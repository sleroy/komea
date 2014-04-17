
package org.komea.product.cep.tester;



import org.komea.eventory.api.engine.ICEPQuery;



/**
 */
public interface ICEPQueryTestPredicate
{
    
    
    /**
     * Method evaluate.
     * 
     * @param _epStatement
     *            EPStatement
     */
    void evaluate(final ICEPQuery _epStatement);
}
