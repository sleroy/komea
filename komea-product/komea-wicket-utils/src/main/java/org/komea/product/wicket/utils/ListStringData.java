/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import java.util.Iterator;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 *
 * @author rgalerme
 */
public class ListStringData extends SortableDataProvider<String, String> {

    private List<String> listData;

    public ListStringData(List<String> listData) {
        this.listData = listData;
    }

    @Override
    public Iterator<? extends String> iterator(long l, long l1) {
        return this.listData.subList((int) l, (int) l1).iterator();
    }

    @Override
    public long size() {
        return this.listData.size();
    }

    @Override
    public IModel<String> model(String t) {
        return Model.of(t);
    }

}
