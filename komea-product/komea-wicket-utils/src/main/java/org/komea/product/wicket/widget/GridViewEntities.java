package org.komea.product.wicket.widget;

import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.komea.product.database.api.IEntity;

public class GridViewEntities<T extends IEntity> extends GridView<T> {

    private static final long serialVersionUID = 1L;
    private final String id;

    public GridViewEntities(final String id, final List<T> data) {
        super(id, new ListDataProvider<T>(data));
        this.id = id;
        this.setColumns(1);
        if (!data.isEmpty()) {
            this.setRows(data.size());
        }
    }

    @Override
    protected void populateEmptyItem(Item<T> item) {
    }

    @Override
    protected void populateItem(Item<T> item) {
        final T modelObject = item.getModelObject();
        item.add(new Label(id + "Element", "- " + modelObject.getDisplayName()));
    }

}
