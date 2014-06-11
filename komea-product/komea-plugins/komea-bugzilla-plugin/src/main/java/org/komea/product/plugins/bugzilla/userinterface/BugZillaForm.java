/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.core.RegisterLog;
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
    private final ManageMessageConnexion messageCon;
    private String savPassword;
    private final WebMarkupContainer contSuccess;
    private final WebMarkupContainer contWaiting;
    private final WebMarkupContainer contError;

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
        savPassword = bugServer.getPassword();
        add(TextFieldBuilder.<String>createPasswordNoRequire("password", bugServer, "password")
                .simpleValidator(0, 255).withTooltip(getString("global.save.form.field.tooltip.password")).build());

//        add(TextFieldBuilder.<String> createRequired("reminderAlert", bugServer, "reminderAlert")
//                .withTooltip(getString("bugzillapage.save.form.field.tooltip.reminder")).build());
        // button
        final Model<String> conModel = Model.of("");

        messageCon = new ManageMessageConnexion();
        contSuccess = new WebMarkupContainer("success") {

            @Override
            public boolean isVisible() {
                return messageCon.visibleSuccess();
            }
        };
        contSuccess.setVisible(false);
        contSuccess.setOutputMarkupId(true);
        contSuccess.setOutputMarkupPlaceholderTag(true);
        add(contSuccess);

        contError = new WebMarkupContainer("errors") {

            @Override
            public boolean isVisible() {
                return messageCon.visibleError();
            }
        };
        contError.setVisible(false);
        contError.setOutputMarkupId(true);
        contError.setOutputMarkupPlaceholderTag(true);
        add(contError);

        contWaiting = new WebMarkupContainer("waiting") {

            @Override
            public boolean isVisible() {
                return messageCon.visibleWaiting();
            }

        };
        contWaiting.setVisible(false);
        contWaiting.setOutputMarkupId(true);
        contWaiting.setOutputMarkupPlaceholderTag(true);
        add(contWaiting);

//        final Label conMessage = new Label("testMessage", conModel);
//        conMessage.setOutputMarkupId(true);
//        conMessage.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)));
//        add(conMessage);
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
//                target.add(conMessage);

            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                target.add(feedBack);
                messageCon.setEtat(Etat.WAITING);
//                target.add(contWaiting);
//                 target.add(contError);
//                target.add(contSuccess);
                contWaiting.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)) {

                    @Override
                    protected void onPostProcessTarget(AjaxRequestTarget target) {
                        super.onPostProcessTarget(target); //To change body of generated methods, choose Tools | Templates.
                        System.out.println("call ajax post porcess meth");
                    }
                }
                );
                contSuccess.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)));
                contError.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)));
//               

//                conModel.setObject(getString("global.connexion.loading"));
//                conMessage.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(2)));
//                target.add(conMessage);
                final RegisterLog registerLog = new RegisterLog(null, "");
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("begin td");
                        if (bService.testConnexion(bugServer, registerLog)) {
                            messageCon.setEtat(Etat.SUCCESS);
                            System.out.println("true td");
//                            conModel.setObject(success);
                        } else {
//                            conModel.setObject(error);
                            messageCon.setEtat(Etat.ERROR);
                            System.out.println("false  td");
                        }
                        System.out.println("end td");
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
                if (bugServer.getPassword() == null || "".equals(bugServer.getPassword())) {
                    bugServer.setPassword(savPassword);
                }
                bService.saveOrUpdate(bugServer);
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));

            }
        });
    }

    private static enum Etat {

        ERROR, WAITING, SUCCESS, NONE
    }

    public static class ManageMessageConnexion implements Serializable {

        private Etat etat;

        public ManageMessageConnexion() {
            etat = Etat.NONE;
        }

        public Etat getEtat() {
            return etat;
        }

        public void setEtat(Etat etat) {
            this.etat = etat;
        }

        public boolean visibleError() {
            return etat.equals(Etat.ERROR);
        }

        public boolean visibleWaiting() {
            return etat.equals(Etat.WAITING);
        }

        public boolean visibleSuccess() {
            return etat.equals(Etat.SUCCESS);
        }

    }

}
