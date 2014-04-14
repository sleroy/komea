
package org.komea.eventory.query.predefined;



import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.formula.CountFormula;
import org.komea.eventory.query.predefined.EmptyQueryDefinition;



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
