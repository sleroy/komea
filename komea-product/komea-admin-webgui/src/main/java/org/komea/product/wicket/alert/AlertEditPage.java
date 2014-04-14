/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.alert;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.alert.IAlertTypeService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.wicket.LayoutPage;

/**
 *
 * @author rgalerme
 */
public final class AlertEditPage extends LayoutPage {

    @SpringBean
    private IAlertTypeService alertService;

    @SpringBean
    private IKPIService kpiService;

    public AlertEditPage(PageParameters _parameters) {
        this(_parameters, new KpiAlertType(), true);
    }

    public AlertEditPage(PageParameters _parameters, KpiAlertType _alertType) {
        this(_parameters, _alertType, false);
    }

    public AlertEditPage(PageParameters _parameters, KpiAlertType _alertType, boolean isNew) {
        super(_parameters);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);

        AlertForm alertForm = new AlertForm(isNew, kpiService, alertService, feedbackPanel, this, _alertType, "form", new CompoundPropertyModel<KpiAlertType>(_alertType));
        String message;
        if (isNew) {
            message = "Add alert";
        } else {
            message = "Edit alert";
        }
        alertForm.add(new Label("legend", message));
        add(alertForm);
    }

    @Override
    public String getTitle() {

        return getString("AlertEditPage.title");
    }
}
