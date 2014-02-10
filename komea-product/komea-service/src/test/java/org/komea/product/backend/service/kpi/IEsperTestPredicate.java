package org.komea.product.backend.service.kpi;

import com.espertech.esper.client.EPStatement;

public interface IEsperTestPredicate
{
    
    
    void evaluate(final EPStatement _epStatement);
}