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
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;

/**
 *
 * @author rgalerme
 */
public abstract class SelectDialog<T> extends AbstractFormDialog<String> {

    private static final long serialVersionUID = 1L;

    protected final DialogButton btnSelect = new DialogButton("Select"); //with a customized text
    protected final DialogButton btnCancel = new DialogButton("Cancel");

    private Form form;
    private FeedbackPanel feedback;
   

    private T selectedProvider;
    


    public SelectDialog(String id, String title, IGenericService service,IChoiceRenderer<T> rendener) {
        this(id, title, (List<T>)service.selectAll(), rendener);
    }

     public SelectDialog(String id, String title, List<T> objectList,IChoiceRenderer<T> rendener) {
        super(id, title, true);
        this.form = new Form<String>("form");
        List<T> list = objectList;
//        List<Provider> list = service.selectByCriteria(new ProviderCriteria());
        selectedProvider = list.get(0);


        ListChoice<T> listEntite = new ListChoice<T>("table",
                new PropertyModel<T>(this, "selectedProvider"), list);
        listEntite.setChoiceRenderer(rendener);
        listEntite.setNullValid(false);
        listEntite.setMaxRows(8);
        this.form.add(listEntite);
        this.feedback = new JQueryFeedbackPanel("feedback");
//        this.form.add(new Label("monlabel", "message affiche"));
        this.add(this.form);
    }


    
        public T getSelectedProvider() {
        return selectedProvider;
    }

    public void setSelectedProvider(T selectedProvider) {
        this.selectedProvider = selectedProvider;
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
