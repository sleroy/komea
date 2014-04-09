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
import org.apache.wicket.Component;

/**
 * @author rgalerme
 */
public abstract class SelectMultipleDialog extends AbstractFormDialog<String> {

    private static final long serialVersionUID = 1L;

    private FeedbackPanel feedback;
    private List<IEntity> filter;

    private Form form;
    private List<IEntity> listSave;
    private List<IEntity> listUsed;
    private List<IEntity> selectedItems;

    protected final DialogButton btnCancel = new DialogButton("Cancel");
    protected final DialogButton btnSelect = new DialogButton("Select"); // with a customized text

    public SelectMultipleDialog(final String id, final String title, final IGenericService service) {

        this(id, title, service.selectAll(), null);
    }

    public SelectMultipleDialog(
            final String id,
            final String title,
            final IGenericService service,
            final IChoiceRenderer<IEntity> rendener) {

        this(id, title, service.selectAll(), rendener);
    }

    public SelectMultipleDialog(final String id, final String title, final List<IEntity> objectList) {

        this(id, title, objectList, null);

    }

    public SelectMultipleDialog(
            final String id,
            final String title,
            final List<IEntity> objectList,
            final IChoiceRenderer<IEntity> _rendener) {

        super(id, title, true);
        this.form = new Form<String>("formMult");
        this.listSave = objectList;
        this.listUsed = new ArrayList<IEntity>();
        this.filter = new ArrayList<IEntity>();
        IChoiceRenderer<IEntity> rendener = _rendener;

        if (rendener == null) {
            rendener = new IChoiceRenderer<IEntity>() {

                @Override
                public Object getDisplayValue(final IEntity t) {

                    return t.getDisplayName();
                }

                @Override
                public String getIdValue(final IEntity t, final int i) {

                    return String.valueOf( t.getId());
                }
            };
        }
        final ListMultipleChoice<IEntity> listEntite
                = new ListMultipleChoice<IEntity>("tableMult",
                        new PropertyModel<List<IEntity>>(this, "selectedItems"), listUsed);
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

    public List<IEntity> getList() {

        return listUsed;
    }

    public List<IEntity> getSelected() {

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

    public void setFilter(final List<IEntity> personsOfGroup) {

        this.filter = personsOfGroup;
    }

    public void setList(final List<IEntity> list) {

        this.listUsed = list;
    }

    @Override
    public void setModelObject(final String user) {

        setDefaultModel(new CompoundPropertyModel<String>(user));
    }

    public void setSelected(final List<IEntity> selectedProvider) {

        this.selectedItems = selectedProvider;
    }

    private void filtre() {

        this.listUsed.clear();
        for (final IEntity type1 : listSave) {
            boolean found = false;
            for (final IEntity type2 : filter) {
                if (type1.getId() == type2.getId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.listUsed.add(type1);
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
