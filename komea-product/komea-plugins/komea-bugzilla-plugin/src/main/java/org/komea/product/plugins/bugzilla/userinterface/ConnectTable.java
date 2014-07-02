/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable;
import com.googlecode.wicket.jquery.ui.interaction.sortable.Sortable.HashListView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.komea.product.wicket.LayoutPage;

/**
 *
 * @author rgalerme
 */
public class ConnectTable {

    public static HashListView<String> newListView(String id, IModel<List<String>> model, ContextProcess context) {
        return new HashListViewImpl(id, model, context);
    }

    public static List<String> newList(String... items) {
        ArrayList<String> list = new ArrayList<String>();

        for (String item : items) {
            list.add(item);
        }

        return list;
    }

    public static class ContextProcess implements Serializable {

        private final HashMap<String, Sortable<String>> data;

        public ContextProcess() {
            data = new HashMap<String, Sortable<String>>();
        }

        public void addSortable(String value, Sortable<String> sortable) {
            data.put(value, sortable);
        }

        public void removeItem(String value) {

            for (Sortable<String> sortable : data.values()) {
                List<String> modelObject = sortable.getModelObject();
                modelObject.remove(value);
            }

        }

        public void updateList(AjaxRequestTarget art) {
            for (Sortable<String> sortable : data.values()) {
                art.add(sortable);
            }
        }

        public List<String> getListItem(String name) {
            Sortable<String> sort = data.get(name);
            return sort.getModelObject();
            
        }

        public void addItem(String item) {
            data.values().iterator().next().getModelObject().add(item);
        }

    }

    public static class AjaxLinkImpl extends AjaxLink {

        private final ContextProcess context;
        private final String value;

        public AjaxLinkImpl(String id, ContextProcess _context, String _value) {
            super(id);
            context = _context;
            value = _value;
        }

        @Override
        public void onClick(AjaxRequestTarget art) {
            context.removeItem(value);
            context.updateList(art);
        }
    }

    public static Sortable<String> newSortable(final String id, List<String> list, ContextProcess context) {
        return new SortableImpl(id, list, context);
    }

    public static class HashListViewImpl extends HashListView<String> {

        private final ContextProcess context;

        public HashListViewImpl(String id, IModel<? extends List<? extends String>> model, ContextProcess _context) {
            super(id, model);
            this.context = _context;
        }

        private static final long serialVersionUID = 1L;

        @Override
        protected void populateItem(ListItem<String> item) {
//                item.add(new EmptyPanel("icon").add(AttributeModifier.append("class", "ui-icon " + JQueryIcon.ARROW_2_N_S)));
            item.add(new Label("item", item.getModelObject()));

            AjaxLink ajaxLink = new AjaxLinkImpl("icon", context, item.getModelObject());
            ajaxLink.add(AttributeModifier.append("class", "ui-icon " + JQueryIcon.TRASH));
            item.add(ajaxLink);
            item.add(AttributeModifier.append("class", "ui-state-default"));
        }
    }

    public static class SortableImpl extends Sortable<String> {

        private final ContextProcess context;

        public SortableImpl(String id, List<String> list, ContextProcess _context) {
            super(id, list);
            this.context = _context;
        }
        private static final long serialVersionUID = 1L;

        @Override
        protected HashListView<String> newListView(IModel<List<String>> model) {
            return ConnectTable.newListView("items", model, context);
        }
    }
}
