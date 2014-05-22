/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.utils;



import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.api.IHasKey;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.Model;



/**
 * @author rgalerme
 */
public abstract class SelectDialog extends AbstractFormDialog<String>
{
    
    
    private static final long    serialVersionUID = 1L;
    
    private final Form           form;
    
    private List<IHasKey>        list;
    private IHasKey              selectedItem;
    protected final DialogButton btnCancel        = new DialogButton("Cancel");
    
    protected final DialogButton btnSelect        = new DialogButton("Select"); // with a customized text
                                                                                
                                                                                
    
    public SelectDialog(
            final String id,
            final String title,
            final IGenericService service,
            final IChoiceRenderer<IHasKey> rendener,
            final Integer itemSelectionne) {
    
    
        this(id, title, service.selectAll(), rendener, itemSelectionne);
    }
    
    
    public SelectDialog(
            final String id,
            final String title,
            final IGenericService service,
            final Integer itemSelectionne) {
    
    
        this(id, title, service.selectAll(), null, itemSelectionne);
    }
    
    
    public SelectDialog(
            final String id,
            final String title,
            final List<IHasKey> objectList,
            final IChoiceRenderer<IHasKey> _rendener,
            final Integer itemSelectionne) {
    
    
        super(id, title,new Model<String>(), true);
        form = new Form<String>("form");
        list = objectList;
        IChoiceRenderer<IHasKey> rendener = _rendener;
        if (!list.isEmpty()) {
            
            for (final IHasKey iHasKey : list) {
                if (iHasKey.getId().equals(itemSelectionne)) {
                    selectedItem = iHasKey;
                }
            }
            if (selectedItem == null) {
                selectedItem = list.get(0);
            }
        }
        if (rendener == null) {
            rendener = new IHasKeyChoiceRenderer();
        }
        final ListChoice<IHasKey> listEntite =
                new ListChoice<IHasKey>("table", new PropertyModel<IHasKey>(this, "selectedItem"),
                        list);
        listEntite.setChoiceRenderer(rendener);
        listEntite.setNullValid(true);
        listEntite.setMaxRows(8);
        form.add(listEntite);
        this.add(form);
    }
    
    
    public SelectDialog(
            final String id,
            final String title,
            final List<IHasKey> objectList,
            final Integer itemSelectionne) {
    
    
        this(id, title, objectList, null, itemSelectionne);
    }
    
    
    @Override
    public Form<?> getForm() {
    
    
        return form;
    }
    
    
    public List<IHasKey> getList() {
    
    
        return list;
    }
    
    
    public IHasKey getSelected() {
    
    
        return selectedItem;
    }
    
    
    @Override
    public boolean isResizable() {
    
    
        return false;
    }
    
    
    @Override
    public void onError(final AjaxRequestTarget target) {
    
    
        // FIXME complete the method
    }
    
    
    public void setList(final List<IHasKey> list) {
    
    
        this.list = list;
    }
    
    
    @Override
    public void setModelObject(final String user) {
    
    
        setDefaultModel(new CompoundPropertyModel<String>(user));
    }
    
    
    public void setSelected(final IHasKey selectedProvider) {
    
    
        selectedItem = selectedProvider;
    }
    
    
    @Override
    protected List<DialogButton> getButtons() {
    
    
        return Arrays.asList(btnSelect, btnCancel);
    }
    
    
    @Override
    protected DialogButton getSubmitButton() {
    
    
        return btnSelect;
    }
    
    
    // Events //
    @Override
    protected void onOpen(final AjaxRequestTarget target) {
    
    
        Collections.sort(list, new Comparator<IHasKey>()
        {
            
            
            @Override
            public int compare(final IHasKey o1, final IHasKey o2) {
            
            
                return o1.getDisplayName().toLowerCase()
                        .compareTo(o2.getDisplayName().toLowerCase());
            }
        });
        target.add(form);
    }
    
}
