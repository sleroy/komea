/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;

/**
 *
 * @author rgalerme
 */
public abstract class SelectDialog extends AbstractFormDialog<String> {

    private static final long serialVersionUID = 1L;

    protected final DialogButton btnSelect = new DialogButton("Select"); //with a customized text
    protected final DialogButton btnCancel = new DialogButton("Cancel");

    private Form form;
    private FeedbackPanel feedback;

    private Provider selectedProvider = null;

    public Provider getSelectedProvider() {
        return selectedProvider;
    }

    public void setSelectedProvider(Provider selectedProvider) {
        this.selectedProvider = selectedProvider;
    }

    public SelectDialog(String id, String title, ProviderDao providerService) {
        super(id, title, true);
        this.form = new Form<String>("form");

        List<Provider> list = providerService.selectByCriteria(new ProviderCriteria());
        setSelectedProvider(list.get(0));
//        list.add("element 1");
//        list.add("element 2");

        ListChoice<Provider> listEntite = new ListChoice<Provider>("table",
                new PropertyModel<Provider>(this, "selectedProvider"), list);
        listEntite.setChoiceRenderer(new IChoiceRenderer<Provider>() {

            @Override
            public Object getDisplayValue(Provider t) {
               return t.getName();
            }

            @Override
            public String getIdValue(Provider t, int i) {
            return String.valueOf(t.getId());
            }

        });
        listEntite.setNullValid(false);
        listEntite.setMaxRows(8);
        this.form.add(listEntite);
        this.feedback = new JQueryFeedbackPanel("feedback");
//        this.form.add(new Label("monlabel", "message affiche"));
        this.add(this.form);
    }

    @Override
    protected List<DialogButton> getButtons() {
        return Arrays.asList(this.btnSelect, this.btnCancel);
    }

    @Override
    protected DialogButton getSubmitButton() {
        return this.btnSelect;
    }

    @Override
    public Form<?> getForm() {
        return this.form;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void setModelObject(String user) {
        this.setDefaultModel(new CompoundPropertyModel<String>(user));
    }

    // Events //
    @Override
    protected void onOpen(AjaxRequestTarget target) {
        target.add(this.form);
    }

    @Override
    public void onError(AjaxRequestTarget target) {
//        target.add(this.feedback);
    }
}
