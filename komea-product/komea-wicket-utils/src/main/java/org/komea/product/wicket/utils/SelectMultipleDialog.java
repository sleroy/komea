/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.utils;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
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
public abstract class SelectMultipleDialog<T> extends AbstractFormDialog<String>
{
    
    
    private static final long    serialVersionUID = 1L;
    
    private FeedbackPanel        feedback;
    private List<T>              filter;
    
    private Form                 form;
    private List<T>              listSave;
    private List<T>              listUsed;
    private List<T>              selectedItems;
    
    protected final DialogButton btnCancel        = new DialogButton("Cancel");
    protected final DialogButton btnSelect        = new DialogButton("Select"); // with a customized text
                                                                                
                                                                                
    
    public SelectMultipleDialog(final String id, final String title, final IGenericService service) {
    
    
        this(id, title, service.selectAll(), null);
    }
    
    
    public SelectMultipleDialog(
            final String id,
            final String title,
            final IGenericService service,
            final IChoiceRenderer<T> rendener) {
    
    
        this(id, title, service.selectAll(), rendener);
    }
    
    
    public SelectMultipleDialog(final String id, final String title, final List<T> objectList) {
    
    
        this(id, title, objectList, null);
        
    }
    
    
    public SelectMultipleDialog(
            final String id,
            final String title,
            final List<T> objectList,
            final IChoiceRenderer<T> _rendener) {
    
    
        super(id, title, true);
        this.form = new Form<String>("formMult");
        this.listSave = objectList;
        this.listUsed = new ArrayList<T>();
        this.filter = new ArrayList<T>();
        IChoiceRenderer<T> rendener = _rendener;
        
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
        final ListMultipleChoice<T> listEntite =
                new ListMultipleChoice<T>("tableMult",
                        new PropertyModel<List<T>>(this, "selectedItems"), listUsed);
        listEntite.setChoiceRenderer(rendener);
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
    
    
        return listUsed;
    }
    
    
    public List<T> getSelected() {
    
    
        return selectedItems;
    }
    
    
    @Override
    public boolean isResizable() {
    
    
        return true;
    }
    
    
    @Override
    public void onError(final AjaxRequestTarget target) {
    
    
        // FIXME complete the method
    }
    
    
    public void setFilter(final List<T> personsOfGroup) {
    
    
        this.filter = personsOfGroup;
    }
    
    
    public void setList(final List<T> list) {
    
    
        this.listUsed = list;
    }
    
    
    @Override
    public void setModelObject(final String user) {
    
    
        setDefaultModel(new CompoundPropertyModel<String>(user));
    }
    
    
    public void setSelected(final List<T> selectedProvider) {
    
    
        this.selectedItems = selectedProvider;
    }
    
    
    private void filtre() {
    
    
        this.listUsed.clear();
        for (final T type : listSave) {
            if (!filter.contains(type)) {
                this.listUsed.add(type);
            }
        }
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
    
    
        filtre();
        target.add(this.form);
    }
    
}
