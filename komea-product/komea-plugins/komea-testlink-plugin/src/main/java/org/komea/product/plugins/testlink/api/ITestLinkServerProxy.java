/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.api;

import java.util.List;
import org.komea.product.plugins.testlink.core.MetricTest;
import org.komea.product.plugins.testlink.core.TestLinkProject;
import org.komea.product.plugins.testlink.core.TestLinkRequirement;
import org.komea.product.plugins.testlink.core.TestLinkTestCase;

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
