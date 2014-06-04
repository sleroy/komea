/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.cronpage;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.Arrays;
import java.util.List;
import static org.apache.wicket.Component.PARENT_PATH;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.backend.service.cron.CronUtils;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public abstract class CronDialog extends AbstractFormDialog<String> {

    private final Form form;
    private final NameGeneric cronValue;
    private final TextField<String> cronField;
    private CronDetails cronObject;
    private final FeedbackPanel feedbackPanel;

    protected final DialogButton btnCancel = new DialogButton("Cancel");
    protected final DialogButton btnSelect = new DialogButton("Save"); // with a customized text

    public CronDialog(String id, String title) {
        super(id, title, new Model<String>(), true);
        feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        feedbackPanel.setVisible(false);
        form = new Form<String>("formCron");
        cronValue = new NameGeneric();
        cronField = TextFieldBuilder.<String>createRequired("cron", cronValue, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip(getString("cronpage.form.dialog.cronfield.tooltip")).buildTextField();
        cronField.add(new IValidator<String>() {

            @Override
            public void validate(IValidatable<String> iv) {
                String value = iv.getValue();
                if (!CronUtils.isValidCronExpression(value)) {
                    final ValidationError error = new ValidationError();
                    error.setMessage(getString("cronpage.form.dialog.error.invalidexpression"));
                    iv.error(error);
                }

            }
        });
        cronField.setOutputMarkupId(true);
        cronField.setOutputMarkupPlaceholderTag(true);
        form.add(cronField);
        CronDialogForm cronDialogForm = new CronDialogForm("formmakecron", this, feedbackPanel);
        add(cronDialogForm);
        add(form);

    }

    public void setCronValue(CronDetails value, AjaxRequestTarget art) {
        cronObject = value;
        cronValue.setName(value.getCronExpression());
        art.add(cronField);
    }

    public void setCronValue(String value, AjaxRequestTarget art) {
        cronValue.setName(value);
        art.add(cronField);
    }

    public CronDetails getCronDetails() {
        return cronObject;
    }

    public NameGeneric getCronValue() {
        return cronValue;
    }

    @Override
    protected List<DialogButton> getButtons() {

        return Arrays.asList(btnSelect, btnCancel);
    }

    @Override
    public boolean isResizable() {

        return false;
    }

    @Override
    protected DialogButton getSubmitButton() {
        return btnSelect;
    }

    @Override
    public Form<?> getForm() {
        return form;
    }

    @Override
    protected void onError(AjaxRequestTarget art) {
        feedbackPanel.setVisible(true);
        art.add(feedbackPanel);
    }

}
