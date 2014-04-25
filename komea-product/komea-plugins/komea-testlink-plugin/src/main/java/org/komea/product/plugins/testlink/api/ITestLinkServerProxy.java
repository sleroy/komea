/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.api;

import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import java.util.List;
import org.komea.product.plugins.testlink.model.TestLinkMetrics;
import org.komea.product.plugins.testlink.model.TestLinkProject;
import org.komea.product.plugins.testlink.model.TestLinkRequirement;

/**
 *
 * @author rgalerme
 */
public interface ITestLinkServerProxy {

    public List<TestLinkProject> getListProject();

    public List<TestCase> getTotalTests(TestLinkProject _project);

    public TestLinkMetrics getMetricTest(TestLinkProject _project);

    public List<TestLinkRequirement> getRequirements(TestLinkProject _project);

}
