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
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.api.IEntity;

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
    private List<T> list;

    private T selectedItem;

    public SelectDialog(String id, String title, IGenericService service) {
        this(id, title, (List<T>) service.selectAll(), null);
    }

    public SelectDialog(String id, String title, List<T> objectList) {
        this(id, title, objectList, null);
    }

    public SelectDialog(String id, String title, IGenericService service, IChoiceRenderer<T> rendener) {
        this(id, title, (List<T>) service.selectAll(), rendener);
    }

    public SelectDialog(String id, String title, List<T> objectList, IChoiceRenderer<T> _rendener) {
        super(id, title, true);
        this.form = new Form<String>("form");
        list = objectList;
        IChoiceRenderer<T> rendener = _rendener;
        if (!list.isEmpty()) {
            selectedItem = list.get(0);
        }
        if (rendener == null) {
            rendener = new IChoiceRenderer<T>() {
                @Override
                public Object getDisplayValue(T t) {
                    return ((IEntity) t).getDisplayName();
                }

                @Override
                public String getIdValue(T t, int i) {
                    return String.valueOf(((IEntity) t).getId());
                }
            };
        }
        ListChoice<T> listEntite = new ListChoice<T>("table",
                new PropertyModel<T>(this, "selectedItem"), list);
        listEntite.setChoiceRenderer(rendener);
        listEntite.setNullValid(true);
        listEntite.setMaxRows(8);
        this.form.add(listEntite);
        this.feedback = new JQueryFeedbackPanel("feedback");
        this.add(this.form);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public T getSelected() {
        return selectedItem;
    }

    public void setSelected(T selectedProvider) {
        this.selectedItem = selectedProvider;
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
    }

}
