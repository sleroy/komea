/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.komea.product.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.product.plugins.bugzilla.data.BugZillaServer;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class BugZillaForm extends Form<BugZillaServer> {

    private final IBugZillaConfigurationService bService;
    private final BugZillaServer bugServer;
    private final Component feedBack;
    private final LayoutPage page;
    private final String oldAdress;

    public BugZillaForm(IBugZillaConfigurationService _bService, BugZillaServer _bugServer, Component _feedBack, LayoutPage _page, String id, IModel<BugZillaServer> model) {
        super(id, model);
        this.bService = _bService;
        this.bugServer = _bugServer;
        this.feedBack = _feedBack;
        this.page = _page;
        this.oldAdress = bugServer.getAddress();
        feedBack.setVisible(false);

        add(TextFieldBuilder.<String>createRequired("address", this.bugServer, "address").highlightOnErrors()
                .withTooltip("Server need a address").build());
        add(TextFieldBuilder.<String>createRequired("login", this.bugServer, "login").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Server need a login").build());
        add(TextFieldBuilder.<String>createRequired("password", this.bugServer, "mdp").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip("Server need a password").build());
        add(TextFieldBuilder.<String>createRequired("reminderAlert", this.bugServer, "reminderAlert").highlightOnErrors()
                .withTooltip("").build());

        //button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));
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
                bService.saveOrUpdate(bugServer, oldAdress);
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));

            }
        });
    }

}
