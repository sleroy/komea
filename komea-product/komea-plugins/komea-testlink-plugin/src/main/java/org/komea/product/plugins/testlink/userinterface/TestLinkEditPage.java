/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.userinterface;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.komea.product.wicket.StatelessLayoutPage;



/**
 * @author rgalerme
 */
public final class TestLinkEditPage extends StatelessLayoutPage
{
    
    
    @SpringBean
    private ITestLinkServerDAO testlinkService;
    
    
    
    public TestLinkEditPage(final PageParameters params) {
    
    
        this(params, new TestLinkServer(),true);
    }

    public TestLinkEditPage(PageParameters _parameters,final TestLinkServer _testLinkServer) {
       this(_parameters, _testLinkServer,false);
    }
    
    
    private TestLinkEditPage(
            final PageParameters pageParameters,
            final TestLinkServer _testLinkServer,boolean _isNew) {
    
    
        super(pageParameters);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        final TestLinkForm tform =
                new TestLinkForm(this, _testLinkServer, testlinkService, feedbackPanel, "form",
                        new CompoundPropertyModel<TestLinkServer>(_testLinkServer));
        add(tform);
                String message;
        if (_isNew) {
            message = getString("testlinkpage.save.add.title");
        } else {
            message = getString("testlinkpage.save.edit.title");
        }
        tform.add(new Label("legend", message));
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("testlinkpage.main.title");
    }
}
