/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.cronpage;

import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public abstract class CronDialog extends AbstractFormDialog<String> {

    private final Form form;
    private final NameGeneric cronValue;
    private final Component cronField;
    private CronDetails cronObject;

    protected final DialogButton btnCancel = new DialogButton("Cancel");
    protected final DialogButton btnSelect = new DialogButton("Save"); // with a customized text

    public CronDialog(String id, String title) {
        super(id, title, new Model<String>(), true);
        form = new Form<String>("formCron");
        cronValue = new NameGeneric();
        cronField = TextFieldBuilder.<String>createRequired("name", cronValue, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip(getString("cronpage.form.dialog.cronfield.tooltip")).build();
        cronField.setOutputMarkupId(true);
        cronField.setOutputMarkupPlaceholderTag(true);
        form.add(cronField);
        CronDialogForm cronDialogForm = new CronDialogForm("formmakecron");
        form.add(cronDialogForm);
        add(form);

    }

    public void setCronValue(CronDetails value, AjaxRequestTarget art) {
        cronObject = value;
        cronValue.setName(value.getCronExpression());
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
        // 
    }

    private static class AjaxLinkImpl extends AjaxLink {

        private final NameGeneric cronValue;
        private final Component composant;
        private final String btnValue;

        public AjaxLinkImpl(String id, NameGeneric _cronValue, Component _composant, String _btnvalue) {
            super(id);
            cronValue = _cronValue;
            composant = _composant;
            btnValue = _btnvalue;

        }

        @Override
        public void onClick(AjaxRequestTarget art) {

        }
    }

}
