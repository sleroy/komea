/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.utils;



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

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;



/**
 * @author rgalerme
 */
public abstract class SelectDialog<T> extends AbstractFormDialog<String>
{
    
    
    private static final long    serialVersionUID = 1L;
    
    private FeedbackPanel        feedback;
    private Form                 form;
    
    private List<T>              list;
    private T                    selectedItem;
    protected final DialogButton btnCancel        = new DialogButton("Cancel");
    
    protected final DialogButton btnSelect        = new DialogButton("Select"); // with a customized text
                                                                                
                                                                                
    
    public SelectDialog(final String id, final String title, final IGenericService service) {
    
    
        this(id, title, service.selectAll(), null);
    }
    
    
    public SelectDialog(
            final String id,
            final String title,
            final IGenericService service,
            final IChoiceRenderer<T> rendener) {
    
    
        this(id, title, service.selectAll(), rendener);
    }
    
    
    public SelectDialog(final String id, final String title, final List<T> objectList) {
    
    
        this(id, title, objectList, null);
    }
    
    
    public SelectDialog(
            final String id,
            final String title,
            final List<T> objectList,
            final IChoiceRenderer<T> _rendener) {
    
    
        super(id, title, true);
        this.form = new Form<String>("form");
        list = objectList;
        IChoiceRenderer<T> rendener = _rendener;
        if (!list.isEmpty()) {
            selectedItem = list.get(0);
        }
        if (rendener == null) {
            rendener = new IChoiceRenderer<T>()
            {
                
                
                @Override
                public Object getDisplayValue(final T t) {
                
                
                    return ((IEntity) t).getDisplayName();
                }
                
                
                @Override
                public String getIdValue(final T t, final int i) {
                
                
                    return String.valueOf(((IEntity) t).getId());
                }
            };
        }
        final ListChoice<T> listEntite =
                new ListChoice<T>("table", new PropertyModel<T>(this, "selectedItem"), list);
        listEntite.setChoiceRenderer(rendener);
        listEntite.setNullValid(true);
        listEntite.setMaxRows(8);
        this.form.add(listEntite);
        this.feedback = new JQueryFeedbackPanel("feedback");
        this.add(this.form);
    }
    
    
    @Override
    public Form<?> getForm() {
    
    
        return this.form;
    }
    
    
    public List<T> getList() {
    
    
        return list;
    }
    
    
    public T getSelected() {
    
    
        return selectedItem;
    }
    
    
    @Override
    public boolean isResizable() {
    
    
        return true;
    }
    
    
    @Override
    public void onError(final AjaxRequestTarget target) {
    
    
        // FIXME complete the method
    }
    
    
    public void setList(final List<T> list) {
    
    
        this.list = list;
    }
    
    
    @Override
    public void setModelObject(final String user) {
    
    
        setDefaultModel(new CompoundPropertyModel<String>(user));
    }
    
    
    public void setSelected(final T selectedProvider) {
    
    
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
    
    
    // Events //
    @Override
    protected void onOpen(final AjaxRequestTarget target) {
    
    
        target.add(this.form);
    }
    
}
