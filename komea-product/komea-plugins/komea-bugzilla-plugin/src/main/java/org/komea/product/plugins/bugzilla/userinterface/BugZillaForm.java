/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.utils.DisplayTraceDialog;
import org.komea.product.wicket.utils.ManageMessageConnexion;
import org.komea.product.wicket.utils.ManageMessageConnexion.Etat;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 * @author rgalerme
 */
public class BugZillaForm extends Form<BZServerConfiguration> {

    private final IBZConfigurationDAO bService;
    private final BZServerConfiguration bugServer;
    private final FeedbackPanel feedBack;
    private final StatelessLayoutPage page;
    private final ManageMessageConnexion messageCon;
    private String savPassword;
    private final WebMarkupContainer contSuccess;
    private final WebMarkupContainer contWaiting;
    private final WebMarkupContainer contError;
    private final WebMarkupContainer globalContenaire;
    private final IModel<String> conModel;
    private final IModel<String> stackTraceDialog;
    private final Form formAddStatus;
    private final ConnectTable.ContextProcess context_status;
    private final ConnectTable.ContextProcess context_resolution;

    private final NameGeneric addstatus;

    public BugZillaForm(
            final IBZConfigurationDAO _bService,
            final BZServerConfiguration _bugServer,
            final FeedbackPanel _feedBack,
            final StatelessLayoutPage _page,
            final String id,
            final IModel<BZServerConfiguration> model) {

        super(id, model);
        bService = _bService;
        bugServer = _bugServer;
        feedBack = _feedBack;
        page = _page;
        feedBack.setVisible(false);
        context_status = new ConnectTable.ContextProcess();
        context_resolution = new ConnectTable.ContextProcess();

        List<String> closedList = new ArrayList<String>();
        List<String> openList = new ArrayList<String>();
        List<String> notfixedList = new ArrayList<String>();
        List<String> fixedList = new ArrayList<String>();

        if (bugServer.getClosedStatus() != null) {
            closedList.addAll(bugServer.getClosedStatus());
        }
        if (bugServer.getOpenedStatus() != null) {
            openList.addAll(bugServer.getOpenedStatus());
        }
        if (bugServer.getNotfixedStatus() != null) {
            notfixedList.addAll(bugServer.getNotfixedStatus());
        }
        if (bugServer.getFixedStatus() != null) {
            fixedList.addAll(bugServer.getFixedStatus());
        }

        addConnectTable(this, openList, closedList, fixedList, notfixedList);
        addstatus = new NameGeneric("");

        formAddStatus = new Form("formAddStatus");
        final TextField<String> textFieldAddStatus = TextFieldBuilder.<String>create("addstatus", addstatus, "name")
                .simpleValidator(0, 255).withTooltip(getString("bugzillapage.save.form.tooltip.addstatus")).buildTextField();
        formAddStatus.add(textFieldAddStatus);
        formAddStatus.add(new AjaxButton("btnAddstatus", formAddStatus) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                if (!"".equals(addstatus.getName())) {
                    target.add(textFieldAddStatus);
                    feedBack.setVisible(false);
                    context_status.addItem(addstatus.getName());
                    context_status.updateList(target);
                    addstatus.setName("");

                } else {
                    feedBack.setVisible(true);
                    FeedbackMessage feedbackMessage = new FeedbackMessage(textFieldAddStatus, getString("bugzillapage.save.form.error.emptystatus"), FeedbackMessage.ERROR);
                    feedBack.getFeedbackMessagesModel().getObject().add(feedbackMessage);
                }
                target.add(feedBack);

            }

        });
        formAddStatus.add(new AjaxButton("btnAddresolution", formAddStatus) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                if (!"".equals(addstatus.getName())) {
                    target.add(textFieldAddStatus);
                    feedBack.setVisible(false);
                    context_resolution.addItem(addstatus.getName());
                    context_resolution.updateList(target);
                    addstatus.setName("");

                } else {
                    feedBack.setVisible(true);
                    FeedbackMessage feedbackMessage = new FeedbackMessage(textFieldAddStatus, getString("bugzillapage.save.form.error.sameresolution"), FeedbackMessage.ERROR);
                    feedBack.getFeedbackMessagesModel().getObject().add(feedbackMessage);
                }
                target.add(feedBack);

            }

        });
        formAddStatus.setOutputMarkupId(true);
        add(formAddStatus);
        add(TextFieldBuilder.createURL("address", bugServer, "address")
                .withTooltip(getString("global.save.form.field.tooltip.serverloc")).simpleValidator(3, 255).build());

        add(TextFieldBuilder.<String>createRequired("login", bugServer, "login")
                .simpleValidator(0, 255).withTooltip(getString("global.save.form.field.tooltip.login")).build());
        savPassword = bugServer.getPassword();
        add(TextFieldBuilder.<String>createPasswordNoRequire("password", bugServer, "password")
                .simpleValidator(0, 255).withTooltip(getString("global.save.form.field.tooltip.password")).build());

//        add(TextFieldBuilder.<String> createRequired("reminderAlert", bugServer, "reminderAlert")
//                .withTooltip(getString("bugzillapage.save.form.field.tooltip.reminder")).build());
        // panel de test de connexion
        // dialog
        stackTraceDialog = Model.of("Test d'affichage");
        IModel<String> titleTraceDialog = Model.of(getString("bugzillapage.connexion.dialog.title"));

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

        add(new AjaxLinkLayout<StatelessLayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                final StatelessLayoutPage page = getCustom();
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));
            }
        });

        // partie de 
        final String erroraddress = getString("global.connexion.error.address");

        AjaxButton testButton;
        testButton = new AjaxButton("testButton", this) {

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
                BugZillaForm.this.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)) {

                    @Override
                    protected void onPostProcessTarget(AjaxRequestTarget target) {
                        super.onPostProcessTarget(target); //To change body of generated methods, choose Tools | Templates.
                        updateStatusServerTest();
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
                        if (bugServer.getPassword() == null || "".equals(bugServer.getPassword())) {
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
                        } catch (IOException ex) {
                            registerExeption(ex);
                        } catch (ConnectionException ex) {
                            registerExeption(ex);
                        } catch (BugzillaException ex) {
                            registerExeption(ex);
                        }
                    }
                });
                executorService.shutdown();

            }
        };
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

                bugServer.setOpenedStatus(new HashSet(context_status.getListItem("open")));
                bugServer.setClosedStatus(new HashSet(context_status.getListItem("close")));
                bugServer.setFixedStatus(new HashSet(context_resolution.getListItem("fixed")));
                bugServer.setNotfixedStatus(new HashSet(context_resolution.getListItem("notfixed")));

                bService.saveOrUpdate(bugServer);
                page.setResponsePage(new BugZillaPage(page.getPageParameters()));

            }
        });
    }

    void registerExeption(Exception ex) {
        messageCon.setEtat(Etat.ERROR);
        conModel.setObject(ex.getMessage());
        String recursiveDisplayTrace = ManageMessageConnexion.recursiveDisplayTrace(ex);
        stackTraceDialog.setObject(recursiveDisplayTrace);
    }

    private void updateStatusServerTest() {
        contError.setVisible(messageCon.visibleError());
        contSuccess.setVisible(messageCon.visibleSuccess());
        contWaiting.setVisible(messageCon.visibleWaiting());
    }

    private String getChaineWithList(List<String> chaineString) {
        StringBuilder builder = new StringBuilder("");
        if (!chaineString.isEmpty()) {
            for (int i = 0; i < chaineString.size() - 1; i++) {
                builder.append(chaineString.get(i));
                builder.append(",");
            }
            builder.append(chaineString.get(chaineString.size() - 1));
        }

        return builder.toString();
    }

    private List<String> getChaineWithList(String chaineString) {
        String[] split = chaineString.split(",");
        return Arrays.asList(split);
    }

    private void addConnectTable(Form page, List<String> open, List<String> close, List<String> fixe, List<String> notfixe) {

        // Sortables //
        final Sortable<String> sortable1 = ConnectTable.newSortable("sortable1", open, context_status);
        page.add(sortable1);

        final Sortable<String> sortable2 = ConnectTable.newSortable("sortable2", close, context_status);
        page.add(sortable2);

        final Sortable<String> sortable3 = ConnectTable.newSortable("sortable3", fixe, context_resolution);
        page.add(sortable3);

        final Sortable<String> sortable4 = ConnectTable.newSortable("sortable4", notfixe, context_resolution);
        page.add(sortable4);

        context_status.addSortable("open", sortable1);
        context_status.addSortable("close", sortable2);
        context_resolution.addSortable("fixed", sortable3);
        context_resolution.addSortable("notfixed", sortable4);

        // Dual connect the sortables
        sortable1.connectWith(sortable2);
        sortable2.connectWith(sortable1);

        sortable3.connectWith(sortable4);
        sortable4.connectWith(sortable3);
    }

}
