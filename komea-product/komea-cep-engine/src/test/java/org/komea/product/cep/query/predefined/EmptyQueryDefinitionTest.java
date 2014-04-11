
package org.komea.product.cep.query.predefined;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.formula.CountFormula;



public class EmptyQueryDefinitionTest
{
    
    
    @Test @Ignore
    public void testGetFilterDefinitions() throws Exception {
    
    
        Assert.assertTrue(new EmptyQueryDefinition().getFilterDefinitions().isEmpty());
    }
    
    
    @Test @Ignore
    public void testGetFormula() throws Exception {
    
    
        Assert.assertTrue(new EmptyQueryDefinition().getFormula() instanceof CountFormula);
    }
    
    
    @Test @Ignore
    public void testGetParameters() throws Exception {
    
    
        Assert.assertTrue(new EmptyQueryDefinition().getParameters().isEmpty());
    }
    
}
