/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.userinterface;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.utils.DisplayTraceDialog;
import org.komea.product.wicket.utils.ManageMessageConnexion;
import org.komea.product.wicket.utils.ManageMessageConnexion.Etat;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;



/**
 * @author rgalerme
 */
public class BugZillaForm extends Form<BZServerConfiguration>
{


    private final IBZConfigurationDAO    bService;
    private final BZServerConfiguration  bugServer;
    private final IModel<String>         conModel;
    private final WebMarkupContainer     contError;
    private final WebMarkupContainer     contSuccess;
    private final WebMarkupContainer     contWaiting;
    private final Component              feedBack;
    private final WebMarkupContainer     globalContenaire;
    private final ManageMessageConnexion messageCon;
    private final StatelessLayoutPage    page;
    private final String                 savPassword;
    private final IModel<String>         stackTraceDialog;



    public BugZillaForm(
            final IBZConfigurationDAO _bService,
            final BZServerConfiguration _bugServer,
            final Component _feedBack,
            final StatelessLayoutPage _page,
            final String id,
            final IModel<BZServerConfiguration> model) {


        super(id, model);
        bService = _bService;
        bugServer = _bugServer;
        feedBack = _feedBack;
        page = _page;
        feedBack.setVisible(false);

        add(TextFieldBuilder.createURL("address", bugServer, "address")
                .withTooltip(getString("global.save.form.field.tooltip.serverloc"))
                .simpleValidator(3, 255).build());

        add(TextFieldBuilder.<String> createRequired("login", bugServer, "login")
                .simpleValidator(0, 255)
                .withTooltip(getString("global.save.form.field.tooltip.login")).build());
        savPassword = bugServer.getPassword();
        add(TextFieldBuilder.<String> createPasswordNoRequire("password", bugServer, "password")
                .simpleValidator(0, 255)
                .withTooltip(getString("global.save.form.field.tooltip.password")).build());

        // add(TextFieldBuilder.<String> createRequired("reminderAlert", bugServer, "reminderAlert")
        // .withTooltip(getString("bugzillapage.save.form.field.tooltip.reminder")).build());
        // panel de test de connexion
        // dialog
        stackTraceDialog = Model.of("Test d'affichage");
        final IModel<String> titleTraceDialog =
                Model.of(getString("bugzillapage.connexion.dialog.title"));

        final DisplayTraceDialog dialog =
                new DisplayTraceDialog("dialogStackTrace", titleTraceDialog, stackTraceDialog);
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
        contError.add(new AjaxLink("openDialogTrace")
        {


            @Override
            public void onClick(final AjaxRequestTarget art) {


                dialog.open(art);
            }
        });

        add(new AjaxLinkLayout<StatelessLayoutPage>("cancel", page)
                {


            @Override
            public void onClick(final AjaxRequestTarget art) {


                final StatelessLayoutPage page = getCustom();
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));
            }
                });
        // partie de

        final String erroraddress = getString("global.connexion.error.address");

        AjaxButton testButton;
        testButton = new AjaxButton("testButton", this)
        {


            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {


                feedBack.setVisible(true);
                target.add(feedBack);

            }


            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {


                feedBack.setVisible(false);
                target.add(feedBack);
                messageCon.setEtat(Etat.WAITING);
                updateStatusServerTest();
                target.add(BugZillaForm.this);
                BugZillaForm.this.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1))
                {


                    @Override
                    protected void onPostProcessTarget(final AjaxRequestTarget target) {


                        super.onPostProcessTarget(target); // To change body of generated methods, choose Tools | Templates.
                        updateStatusServerTest();
                        System.out.print("time fonctionne");
                        if (!messageCon.visibleWaiting()) {
                            stop(target);
                        }
                    }
                });

                final ExecutorService executorService = Executors.newFixedThreadPool(1);
                try {
                    executorService.execute(new Runnable()
                    {


                        @Override
                        public void run() {


                            if (bugServer.getPassword() == null
                                    || "".equals(bugServer.getPassword())) {
                                bugServer.setPassword(savPassword);
                            }
                            try {
                                if (bService.testConnexion(bugServer)) {
                                    messageCon.setEtat(Etat.SUCCESS);
                                } else {
                                    messageCon.setEtat(Etat.ERROR);
                                    conModel.setObject(erroraddress);
                                    stackTraceDialog.setObject("");
                                }
                            } catch (final Exception ex) {
                                registerExeption(ex);
                            }
                        }
                    });
                } finally {
                    try {
                        executorService.awaitTermination(15, TimeUnit.HOURS);
                    } catch (final InterruptedException e) {
                        registerExeption(e);
                    }
                    executorService.shutdownNow();
                }

            }
        };
        add(testButton);

        // fin test connexion
        add(new AjaxButton("saveValidation", this)
        {


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
                if (bugServer.getPassword() == null || "".equals(bugServer.getPassword())) {
                    bugServer.setPassword(savPassword);
                }
                bService.saveOrUpdate(bugServer);
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));

            }
        });
    }


    private void updateStatusServerTest() {


        contError.setVisible(messageCon.visibleError());
        contSuccess.setVisible(messageCon.visibleSuccess());
        contWaiting.setVisible(messageCon.visibleWaiting());
    }


    void registerExeption(final Exception ex) {


        messageCon.setEtat(Etat.ERROR);
        conModel.setObject(ex.getMessage());
        final String recursiveDisplayTrace = ManageMessageConnexion.recursiveDisplayTrace(ex);
        stackTraceDialog.setObject(recursiveDisplayTrace);
    }


}
