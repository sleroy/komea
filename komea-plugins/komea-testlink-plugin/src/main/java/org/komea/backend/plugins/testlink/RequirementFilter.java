/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.testlink;



import java.util.ArrayList;
import java.util.List;



/**
 * @author rgalerme
 */
public class RequirementFilter
{
    
    
    static RequirementFilter filter(final List<TestLinkRequirement> requirements) {
    
    
        final RequirementFilter requirement = new RequirementFilter(requirements);
        requirement.updateData();
        return requirement;
    }
    
    
    
    private final List<TestLinkRequirement> testedRequirements;
    private final List<TestLinkRequirement> totalRequirements;
    
    private final List<TestLinkRequirement> untestedRequirements;
    
    
    
    private RequirementFilter(final List<TestLinkRequirement> requirements) {
    
    
        totalRequirements = requirements;
        testedRequirements = new ArrayList<TestLinkRequirement>();
        untestedRequirements = new ArrayList<TestLinkRequirement>();
    }
    
    
    public List<TestLinkRequirement> getTestedRequirements(final String _project) {
    
    
        return testedRequirements;
    }
    
    
    public List<TestLinkRequirement> getUntestedRequirements(final String _project) {
    
    
        return untestedRequirements;
    }
    
    
    private void updateData() {
    
    
        //
    }
}
