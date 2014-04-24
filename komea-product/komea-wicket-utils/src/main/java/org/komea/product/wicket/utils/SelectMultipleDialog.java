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

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.Collections;
import java.util.Comparator;
import org.apache.wicket.Component;
import org.komea.product.database.api.IHasKey;

/**
 * @author rgalerme
 */
public abstract class SelectMultipleDialog extends AbstractFormDialog<String> {

    private static final long serialVersionUID = 1L;

    private FeedbackPanel feedback;
    private List<IHasKey> filter;

    private Form form;
    private List<IHasKey> listSave;
    private List<IHasKey> listUsed;
    private List<IHasKey> selectedItems;
    private List<ICustomFilter> iCustomFilters;

    protected final DialogButton btnCancel = new DialogButton("Cancel");
    protected final DialogButton btnSelect = new DialogButton("Select"); // with a customized text

    public SelectMultipleDialog(final String id, final String title, final IGenericService service) {

        this(id, title, service.selectAll(), null);
    }

    public SelectMultipleDialog(
            final String id,
            final String title,
            final IGenericService service,
            final IChoiceRenderer<IHasKey> rendener) {

        this(id, title, service.selectAll(), rendener);
    }

    public SelectMultipleDialog(final String id, final String title, final List<IHasKey> objectList) {

        this(id, title, objectList, null);

    }

    public SelectMultipleDialog(
            final String id,
            final String title,
            final List<IHasKey> objectList,
            final IChoiceRenderer<IHasKey> _rendener) {

        super(id, title, true);
        this.iCustomFilters = new ArrayList<ICustomFilter>();
        this.form = new Form<String>("formMult");
        this.listSave = objectList;
        this.listUsed = new ArrayList<IHasKey>();
        this.filter = new ArrayList<IHasKey>();
        IChoiceRenderer<IHasKey> rendener = _rendener;

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

        final ListMultipleChoice<IHasKey> listEntite
                = new ListMultipleChoice<IHasKey>("tableMult",
                        new PropertyModel<List<IHasKey>>(this, "selectedItems"), listUsed);
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

    public List<IHasKey> getList() {

        return listUsed;
    }

    public List<IHasKey> getSelected() {

        return selectedItems;
    }

    public void addCustomFilter(ICustomFilter filter) {
        this.iCustomFilters.add(filter);
    }

    @Override
    public boolean isResizable() {

        return false;
    }

    @Override
    public void onError(final AjaxRequestTarget target) {

        // FIXME complete the method
    }

    public void setFilter(final List<IHasKey> personsOfGroup) {

        this.filter = personsOfGroup;
    }

    public void setList(final List<IHasKey> list) {

        this.listUsed = list;
    }

    @Override
    public void setModelObject(final String user) {

        setDefaultModel(new CompoundPropertyModel<String>(user));
    }

    public void setSelected(final List<IHasKey> selectedProvider) {

        this.selectedItems = selectedProvider;
    }

    private void filtre() {

        this.listUsed.clear();
        for (final IHasKey type1 : listSave) {
            boolean found = false;
            for (final IHasKey type2 : filter) {
                if (type1.getId() == type2.getId()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                boolean valid = true;
                for (ICustomFilter filter : iCustomFilters) {
                    if (filter.isNoDisplay(type1)) {
                        valid = false;
                        break;
                    }
                }
                if(valid)
                {
                this.listUsed.add(type1);
                }
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
        Collections.sort(listUsed, new Comparator<IHasKey>() {
            @Override
            public int compare(IHasKey o1, IHasKey o2) {
                return o1.getDisplayName().toLowerCase().compareTo(o2.getDisplayName().toLowerCase());
            }
        });
        target.add(this.form);
    }

}
