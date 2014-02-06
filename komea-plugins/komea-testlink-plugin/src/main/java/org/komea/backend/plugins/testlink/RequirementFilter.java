/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rgalerme
 */
public class RequirementFilter {

    private final List<TestLinkRequirement> totalRequirements;
    private final List<TestLinkRequirement> testedRequirements;
    private final List<TestLinkRequirement> untestedRequirements;

    static RequirementFilter filter(List<TestLinkRequirement> requirements) {
        RequirementFilter requirement = new RequirementFilter(requirements);
        requirement.updateData();
        return requirement;
    }

    private RequirementFilter(List<TestLinkRequirement> requirements) {
        this.totalRequirements = requirements;
        this.testedRequirements = new ArrayList<TestLinkRequirement>();
        this.untestedRequirements = new ArrayList<TestLinkRequirement>();
    }

    private void updateData() {

    }

    public List<TestLinkRequirement> getTestedRequirements(String _project) {
        return this.testedRequirements;
    }

    public List<TestLinkRequirement> getUntestedRequirements(String _project) {
        return this.untestedRequirements;
    }
}
