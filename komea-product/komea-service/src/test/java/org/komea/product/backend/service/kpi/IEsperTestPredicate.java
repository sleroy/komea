package org.komea.product.backend.service.kpi;

import com.espertech.esper.client.EPStatement;

/**
 */
public interface IEsperTestPredicate
{
    
    
    /**
     * Method evaluate.
     * @param _epStatement EPStatement
     */
    void evaluate(final EPStatement _epStatement);
}