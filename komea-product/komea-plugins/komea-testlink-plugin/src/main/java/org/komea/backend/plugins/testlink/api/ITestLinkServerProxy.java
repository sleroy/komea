/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink.api;

import java.util.List;
import org.komea.backend.plugins.testlink.MetricTest;
import org.komea.backend.plugins.testlink.TestLinkProject;
import org.komea.backend.plugins.testlink.TestLinkRequirement;
import org.komea.backend.plugins.testlink.TestLinkTestCase;

/**
 *
 * @author rgalerme
 */
public interface ITestLinkServerProxy {

    public List<TestLinkProject> getListProject();

    public List<TestLinkTestCase> getTotalTests(TestLinkProject _project);
    
    public MetricTest getMetricTest(TestLinkProject _project);

    public List<TestLinkRequirement> getRequirements(TestLinkProject _project);


}
