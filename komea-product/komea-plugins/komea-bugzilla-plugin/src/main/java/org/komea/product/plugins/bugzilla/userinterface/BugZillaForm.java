/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 * @author rgalerme
 */
public class BugZillaForm extends Form<BZServerConfiguration> {

    private final IBZConfigurationDAO bService;
    private final BZServerConfiguration bugServer;
    private final Component feedBack;
    private final LayoutPage page;

    public BugZillaForm(
            final IBZConfigurationDAO _bService,
            final BZServerConfiguration _bugServer,
            final Component _feedBack,
            final LayoutPage _page,
            final String id,
            final IModel<BZServerConfiguration> model) {

        super(id, model);
        bService = _bService;
        bugServer = _bugServer;
        feedBack = _feedBack;
        page = _page;
        feedBack.setVisible(false);

        add(TextFieldBuilder.createURL("address", bugServer, "address")
                .withTooltip(getString("global.save.form.field.tooltip.serverloc")).simpleValidator(3, 255).build());

        add(TextFieldBuilder.<String>createRequired("login", bugServer, "login")
                .simpleValidator(0, 255).withTooltip(getString("global.save.form.field.tooltip.login")).build());

        add(TextFieldBuilder.<String>createRequired("password", bugServer, "password")
                .simpleValidator(0, 255).withTooltip(getString("global.save.form.field.tooltip.password")).build());

//        add(TextFieldBuilder.<String> createRequired("reminderAlert", bugServer, "reminderAlert")
//                .withTooltip(getString("bugzillapage.save.form.field.tooltip.reminder")).build());
        // button
        final Model<String> conModel = Model.of("");
        final Label conMessage = new Label("testMessage", conModel);
        conMessage.setOutputMarkupId(true);
        conMessage.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)));
        add(conMessage);

        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                final LayoutPage page = getCustom();
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));
            }
        });
        // partie de 

        final String success = getString("global.connexion.success");
        final String error = getString("global.connexion.error");
        
        AjaxButton testButton = new AjaxButton("testButton", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
                conModel.setObject(getString("global.connexion.error"));
                target.add(feedBack);
                target.add(conMessage);

            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                target.add(feedBack);
                conModel.setObject(getString("global.connexion.loading"));
                conMessage.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)));
                target.add(conMessage);
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (bService.testConnexion(bugServer)) {
                            conModel.setObject(success);
                        } else {
                            conModel.setObject(error);
                        }
                    }
                });
                executorService.shutdown();

            }
        };
//        testButton.setDefaultFormProcessing(false);
        add(testButton);

        // fin test connexion
        add(new AjaxButton("saveValidation", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                bService.saveOrUpdate(bugServer);
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));

            }
        });
    }

}
