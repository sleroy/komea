/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.StatelessLayoutPage;

/**
 *
 * @author rgalerme
 */
public final class BugZillaEditPage extends StatelessLayoutPage {

    @SpringBean
    private IBZConfigurationDAO bService;

    public BugZillaEditPage(PageParameters _parameters) {
        this(_parameters, new BZServerConfiguration(), true);
    }

    public BugZillaEditPage(PageParameters _parameters, BZServerConfiguration _bugServer) {
        this(_parameters, _bugServer, false);
    }

    private BugZillaEditPage(PageParameters params, BZServerConfiguration _bugServer, boolean _isNew) {
        super(params);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        BugZillaForm bform = new BugZillaForm(bService, _bugServer, feedbackPanel, this, "form", new CompoundPropertyModel<BZServerConfiguration>(_bugServer));
        add(bform);
        String message;
        if (_isNew) {
            message = getString("bugzillapage.save.add.title");
        } else {
            message = getString("bugzillapage.save.edit.title");
        }
        bform.add(new Label("legend", message));
    }

    @Override
    public String getTitle() {

        return getString("bugzillapage.main.title");
    }
}
