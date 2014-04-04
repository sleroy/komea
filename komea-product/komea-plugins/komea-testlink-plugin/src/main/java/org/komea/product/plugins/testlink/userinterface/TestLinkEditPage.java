/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.userinterface;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.plugins.testlink.api.ITestLinkServerService;
import org.komea.product.plugins.testlink.core.TestLinkServer;
import org.komea.product.wicket.LayoutPage;

/**
 *
 * @author rgalerme
 */
public final class TestLinkEditPage extends LayoutPage {

    @SpringBean
    private ITestLinkServerService bService;

    public TestLinkEditPage(PageParameters params) {
        this(params, new TestLinkServer());
    }

    TestLinkEditPage(PageParameters pageParameters, TestLinkServer _testLinkServer) {
        super(pageParameters);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        TestLinkForm tform = new TestLinkForm(this, _testLinkServer, bService, feedbackPanel, "form", new CompoundPropertyModel<TestLinkServer>(_testLinkServer));
        add(tform);
    }

    @Override
    public String getTitle() {

        return getString("TestLinkEditPage.title");
    }
}
