package org.komea.product.wicket.widget;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 * Panel that houses row-actions
 */
public class TableActionPanel<T> extends Panel {

    private final IDeleteAction<T> deleteAction;
    private final IEditAction<T> editAction;

    /**
     * @param id component id
     * @param model model for contact
     */
    public TableActionPanel(
            final String id,
            final IModel<T> model,
            final IDeleteAction<T> _action,
            final IEditAction<T> _editAction) {

        super(id, model);
        deleteAction = _action;
        editAction = _editAction;
        add(new AjaxLink<String>("edit") {

            @Override
            public void onClick(final AjaxRequestTarget _target) {

                editAction.selected((T) TableActionPanel.this.getDefaultModelObject());

            }
        });

        final AjaxLink<String> removeLink = new AjaxLink<String>("delete") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                debug("Deleting item " + TableActionPanel.this.getDefaultModelObject());
                deleteAction.delete((T) TableActionPanel.this.getDefaultModelObject(),target);
            }
        };

        add(removeLink);
    }
}
