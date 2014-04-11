
package org.komea.product.cep.query.predefined;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.formula.CountFormula;



public class EmptyQueryDefinitionTest
{
    
    
    @Test 
    public void testGetFilterDefinitions() throws Exception {
    
    
        Assert.assertTrue(new EmptyQueryDefinition().getFilterDefinitions().isEmpty());
    }
    
    
    @Test 
    public void testGetFormula() throws Exception {
    
    
        Assert.assertTrue(new EmptyQueryDefinition().getFormula() instanceof CountFormula);
    }
    
    
    @Test 
    public void testGetParameters() throws Exception {
    
    
        Assert.assertTrue(new EmptyQueryDefinition().getParameters().isEmpty());
    }
    
}
