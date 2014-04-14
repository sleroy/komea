/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.userinterface;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class TestLinkForm extends Form<TestLinkServer> {

    private final LayoutPage page;
    private final String oldAdress;
    private final TestLinkServer testServer;
    private final ITestLinkServerDAO testlinkService;
    private final Component feedBack;

    public TestLinkForm(LayoutPage _page, TestLinkServer _testServer, ITestLinkServerDAO _testlinkService, Component _feedBack, String id, IModel<TestLinkServer> model) {
        super(id, model);
        this.page = _page;
        this.testServer = _testServer;
        this.testlinkService = _testlinkService;
        this.feedBack = _feedBack;
        this.oldAdress = testServer.getAddress();

        feedBack.setVisible(false);
       
        add(TextFieldBuilder.<String>createRequired("name", this.testServer, "name").highlightOnErrors()
                .withTooltip("Serva need a name").build());
        add(TextFieldBuilder.<String>createRequired("address", this.testServer, "address").highlightOnErrors()
                .withTooltip("Server need a address").build());
        add(TextFieldBuilder.<String>createRequired("key", this.testServer, "key").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Server need key for connexion").build());

        //button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new TestLinkPage(page.getPageParameters()));
            }
        });

        add(new AjaxButton("submit", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
                error("error found");
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                testlinkService.saveOrUpdate(testServer, oldAdress);
                page.setResponsePage(new TestLinkPage(page.getPageParameters()));

            }
        });
    }

}
