/**
 * 
 */
package org.komea.product.wicket.kpiimport;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class ImportPanel<T> extends Panel
{


    public ImportPanel(final String id, final IModel<T> model, final Component _ajaxLink) {


        super(id, model);
        add(_ajaxLink);
    }
}