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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.generic.IGenericService;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.Collections;
import java.util.Comparator;
import org.komea.product.database.api.IHasKey;

/**
 * @author rgalerme
 */
public abstract class SelectDialog extends AbstractFormDialog<String> {

    private static final long serialVersionUID = 1L;

    private Form form;

    private List<IHasKey> list;
    private IHasKey selectedItem;
    protected final DialogButton btnCancel = new DialogButton("Cancel");

    protected final DialogButton btnSelect = new DialogButton("Select"); // with a customized text

    public SelectDialog(final String id, final String title, final IGenericService service) {

        this(id, title, service.selectAll(), null);
    }

    public SelectDialog(
            final String id,
            final String title,
            final IGenericService service,
            final IChoiceRenderer<IHasKey> rendener) {

        this(id, title, service.selectAll(), rendener);
    }

    public SelectDialog(final String id, final String title, final List<IHasKey> objectList) {

        this(id, title, objectList, null);
    }

    public SelectDialog(
            final String id,
            final String title,
            final List<IHasKey> objectList,
            final IChoiceRenderer<IHasKey> _rendener) {

        super(id, title, true);
        this.form = new Form<String>("form");
        list = objectList;
        IChoiceRenderer<IHasKey> rendener = _rendener;
        if (!list.isEmpty()) {
            selectedItem = list.get(0);
        }
        if (rendener == null) {
            rendener = new IChoiceRenderer<IHasKey>() {

                @Override
                public Object getDisplayValue(final IHasKey t) {

                    return t.getDisplayName();
                }

                @Override
                public String getIdValue(final IHasKey t, final int i) {

                    return String.valueOf(t.getId());
                }
            };
        }
        final ListChoice<IHasKey> listEntite
                = new ListChoice<IHasKey>("table", new PropertyModel<IHasKey>(this, "selectedItem"), list);
        listEntite.setChoiceRenderer(rendener);
        listEntite.setNullValid(true);
        listEntite.setMaxRows(8);
        this.form.add(listEntite);
        this.add(this.form);
    }

    @Override
    public Form<?> getForm() {

        return this.form;
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

        Collections.sort(list, new Comparator<IHasKey>() {
            @Override
            public int compare(IHasKey o1, IHasKey o2) {
                return o1.getDisplayName().toLowerCase().compareTo(o2.getDisplayName().toLowerCase());
            }
        });
        target.add(this.form);
    }

}
