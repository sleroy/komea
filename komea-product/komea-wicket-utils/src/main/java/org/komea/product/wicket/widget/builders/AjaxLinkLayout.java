/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.widget.builders;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

/**
 *
 * @author rgalerme
 */
public abstract class AjaxLinkLayout<T> extends AjaxLink {

    private final T page;

    public AjaxLinkLayout(final String string, final IModel imodel, final T page) {

        super(string, imodel);
        this.page = page;
    }

    public AjaxLinkLayout(final String string, final T page) {

        super(string);
        this.page = page;
    }

    public T getCustom() {

        return page;
    }

}
