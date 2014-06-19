/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.userinterface;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.utils.DisplayTraceDialog;
import org.komea.product.wicket.utils.ManageMessageConnexion;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 * @author rgalerme
 */
public class TestLinkForm extends Form<TestLinkServer> {

    private final Component feedBack;
    private final String oldAdress;
    private final StatelessLayoutPage         page;
    private final ITestLinkServerDAO testlinkService;
    private final TestLinkServer testServer;
    private final ManageMessageConnexion messageCon;
    private final WebMarkupContainer contSuccess;
    private final WebMarkupContainer contWaiting;
    private final WebMarkupContainer contError;
    private final WebMarkupContainer globalContenaire;
    private final IModel<String> conModel;
    private final IModel<String> stackTraceDialog;

    public TestLinkForm(
            final StatelessLayoutPage _page,
            final TestLinkServer _testServer,
            final ITestLinkServerDAO _testlinkService,
            final Component _feedBack,
            final String id,
            final IModel<TestLinkServer> model) {

        super(id, model);
        page = _page;
        testServer = _testServer;
        testlinkService = _testlinkService;
        feedBack = _feedBack;
        oldAdress = testServer.getAddress();

        feedBack.setVisible(false);

        add(TextFieldBuilder.<String>createRequired("name", testServer, "name")
                .withTooltip(getString("testlinkpage.save.add.title")).simpleValidator(3, 255).build());
        add(TextFieldBuilder.<String>createRequired("address", testServer, "address")
                .simpleValidator(4, 255).withTooltip(getString("global.save.form.field.tooltip.serverloc")).build());
        add(TextFieldBuilder
                .<String>createRequired("key", testServer, "key")
                .simpleValidator(1, 255)
                .withTooltip(
                        getString("testlinkpage.save.tooltip.key"))
                .build());

        // button
        stackTraceDialog = Model.of("Test d'affichage");
        IModel<String> titleTraceDialog = Model.of(getString("testlink.connexion.dialog.title"));

        final DisplayTraceDialog dialog = new DisplayTraceDialog("dialogStackTrace", titleTraceDialog, stackTraceDialog);
        this.add(dialog);
        // autre
        conModel = Model.of(" ");
        final Label conMessage = new Label("labelerror", conModel);
        conMessage.setOutputMarkupId(true);

        messageCon = new ManageMessageConnexion();

        globalContenaire = new WebMarkupContainer("global");
        globalContenaire.setOutputMarkupId(true);
        globalContenaire.setOutputMarkupPlaceholderTag(true);
        add(globalContenaire);

        contSuccess = new WebMarkupContainer("success");
        contSuccess.setVisible(false);
        contSuccess.setOutputMarkupId(true);
        contSuccess.setOutputMarkupPlaceholderTag(true);
        globalContenaire.add(contSuccess);

        contError = new WebMarkupContainer("errors");
        contError.setVisible(false);
        contError.setOutputMarkupId(true);
        contError.setOutputMarkupPlaceholderTag(true);
        contError.add(conMessage);
        globalContenaire.add(contError);

        contWaiting = new WebMarkupContainer("waiting");
        contWaiting.setVisible(false);
        contWaiting.setOutputMarkupId(true);
        contWaiting.setOutputMarkupPlaceholderTag(true);
        globalContenaire.add(contWaiting);

        // button
        contError.add(new AjaxLink("openDialogTrace") {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialog.open(art);
            }
        });
        final String erroraddress = getString("global.connexion.error.address");
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
                messageCon.setEtat(ManageMessageConnexion.Etat.WAITING);
                updateStatusServerTest();
                target.add(TestLinkForm.this);
                TestLinkForm.this.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)) {

                    @Override
                    protected void onPostProcessTarget(AjaxRequestTarget target) {
                        super.onPostProcessTarget(target); //To change body of generated methods, choose Tools | Templates.
                        updateStatusServerTest();
                        System.out.print("time fonctionne");
                        if (!messageCon.visibleWaiting()) {
                            stop(target);
                        }
                    }
                }
                );

                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            if (testlinkService.testConnexion(testServer)) {
                                messageCon.setEtat(ManageMessageConnexion.Etat.SUCCESS);
                            } else {
                                messageCon.setEtat(ManageMessageConnexion.Etat.ERROR);
                                conModel.setObject(erroraddress);
                                stackTraceDialog.setObject("");

                            }
                        } catch (Exception e) {
                            registerExeption(e);
                        }
                    }
                });
                executorService.shutdown();

            }
        };
//        testButton.setDefaultFormProcessing(false);
        add(testButton);

        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {
        add(new AjaxLinkLayout<StatelessLayoutPage>("cancel", page)

            @Override
            public void onClick(final AjaxRequestTarget art) {

                final StatelessLayoutPage page = getCustom();
                page.setResponsePage(new TestLinkPage(page.getPageParameters()));
            }
        });

        add(new AjaxButton("submit", this) {

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
                testlinkService.saveOrUpdate(testServer);
                page.setResponsePage(new TestLinkPage(page.getPageParameters()));

            }
        });
    }

    private void updateStatusServerTest() {
        contError.setVisible(messageCon.visibleError());
        contSuccess.setVisible(messageCon.visibleSuccess());
        contWaiting.setVisible(messageCon.visibleWaiting());
    }

    void registerExeption(Exception ex) {
        messageCon.setEtat(ManageMessageConnexion.Etat.ERROR);
        conModel.setObject(ex.getMessage());
        String recursiveDisplayTrace = ManageMessageConnexion.recursiveDisplayTrace(ex);
        stackTraceDialog.setObject(recursiveDisplayTrace);
    }

}
