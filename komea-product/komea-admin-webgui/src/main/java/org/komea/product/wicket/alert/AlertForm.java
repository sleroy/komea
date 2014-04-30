/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.alert;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.alert.IAlertTypeService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.enums.Operator;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.SelectBoxBuilder;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class AlertForm extends StatelessForm<KpiAlertType> {

    private final IAlertTypeService alertService;
    private final Component feedBack;
    private final LayoutPage page;
    private final KpiAlertType alert;
    private final NameGeneric nameEntity;
    private final TextField customerField;
    private final IKPIService kpiService;
    private Boolean alertEnabled;
    private final boolean isNew;

    public AlertForm(boolean _isNew, IKPIService _kpiService, IAlertTypeService _alertService, Component _feedBack, LayoutPage _page, KpiAlertType _alert, String id, IModel<KpiAlertType> model) {
        super(id, model);
        this.isNew = _isNew;
        this.alertService = _alertService;
        this.feedBack = _feedBack;
        this.page = _page;
        this.alert = _alert;
        this.kpiService = _kpiService;
        nameEntity = new NameGeneric("");
        feedBack.setVisible(false);

        //field
        add(TextFieldBuilder.<String>createRequired("name", this.alert, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip(getString("global.field.tooltip.name")).build());

        TextFieldBuilder<String> keyField = TextFieldBuilder.<String>createRequired("alertKey", this.alert, "kpiAlertKey").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip(getString("global.field.tooltip.key"));

        if (isNew) {
            keyField.UniqueStringValidator(getString("global.field.key"), alertService);
        } else {
            keyField.buildTextField().setEnabled(false);
        }

        add(keyField.build());

        add(TextFieldBuilder.<String>createRequired("value", this.alert, "value").highlightOnErrors()
                .withTooltip(getString("alertpage.save.form.field.tooltip.condition")).build());

        add(TextAreaBuilder.<String>create("description", this.alert, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip(getString("global.field.tooltip.description")).build());

        
        add(SelectBoxBuilder.<Operator>createWithEnumCustom("operator", this.alert,
                Operator.class,new ChoiceRenderer<Operator>("value")).withTooltip(getString("alertpage.save.form.field.tooltip.operator")).build());

        add(SelectBoxBuilder.<Severity>createWithEnum("severity", this.alert,
                Severity.class).withTooltip(getString("alertpage.save.form.field.tooltip.severity")).build());

        this.alertEnabled = alert.getEnabled();
        PropertyModel<Boolean> modelchcekBox = new PropertyModel<Boolean>(this, "alertEnabled");
        CheckBox checkBox = new CheckBox("enabled", modelchcekBox);
        add(checkBox);
//        add(SelectBoxBuilder.<Boolean>createWithBooleanRequire("enabled", this.alert).build());

        Kpi selectByPrimaryKey = kpiService.selectByPrimaryKey(this.alert.getIdKpi());
        if (selectByPrimaryKey != null) {
            nameEntity.setName(selectByPrimaryKey.getName());
        }

        this.customerField = TextFieldBuilder.<String>createRequired("kpi", nameEntity, "name").withTooltip(getString("global.field.tooltip.kpi")).buildTextField();
        this.customerField.setOutputMarkupId(true);
        add(customerField);

        initSelectKpi();

        //button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new AlertPage(page.getPageParameters()));
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
                alert.setEnabled(isAlertEnabled());
                alertService.saveOrUpdate(alert);
                page.setResponsePage(new AlertPage(page.getPageParameters()));

            }
        });
    }

    public void initSelectKpi() {
        List<IHasKey> allKpi = (List<IHasKey>) (List<?>) kpiService.selectAll();
        final SelectDialog DialogKpi = new SelectDialog("kpiDialog", getString("global.field.popup.kpi"), allKpi,alert.getIdKpi()) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                IHasKey selectedKpi = getSelected();
                if (selectedKpi != null) {
                    alert.setIdKpi(selectedKpi.getId());
                    nameEntity.setName(selectedKpi.getDisplayName());
                } else {
                    alert.setIdKpi(null);
                    nameEntity.setName("");
                }
                customerField.clearInput();
                target.add(customerField);
            }

        };
        add(DialogKpi);
        add(new AjaxLinkLayout<LayoutPage>("kpiButton", null) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                DialogKpi.open(art);

            }
        });
    }

    public Boolean isAlertEnabled() {
        return alertEnabled;
    }

    public void setAlertEnabled(Boolean alertEnabled) {
        this.alertEnabled = alertEnabled;
    }

    public NameGeneric getNameEntity() {
        return nameEntity;
    }

}
