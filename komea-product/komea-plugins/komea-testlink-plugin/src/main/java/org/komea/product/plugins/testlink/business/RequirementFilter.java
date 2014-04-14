/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.business;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.plugins.testlink.model.TestLinkRequirement;



/**
 * This class is a filter that obtains the list of all requirements inside a Testlink server.
 * 
 * @author rgalerme
 */
public class RequirementFilter
{
    
    
    private final List<TestLinkRequirement> testedRequirements;
    private final List<TestLinkRequirement> totalRequirements;
    
    private final List<TestLinkRequirement> untestedRequirements;
    
    
    
    public RequirementFilter(final List<TestLinkRequirement> requirements) {
    
    
        totalRequirements = requirements;
        testedRequirements = new ArrayList<TestLinkRequirement>();
        untestedRequirements = new ArrayList<TestLinkRequirement>();
    }
    
    
    public List<TestLinkRequirement> getTestedRequirements(final String _project) {
    
    
        return testedRequirements;
    }
    
    
    /**
     * Returns the list of total requirements
     * 
     * @return
     */
    public List<TestLinkRequirement> getTotalRequirements() {
    
    
        return totalRequirements;
    }
    
    
    public List<TestLinkRequirement> getUntestedRequirements(final String _project) {
    
    
        return untestedRequirements;
    }
}
