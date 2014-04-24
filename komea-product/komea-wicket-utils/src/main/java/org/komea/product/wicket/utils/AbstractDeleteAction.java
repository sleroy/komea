/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import static com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog.LBL_OK;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public abstract class AbstractDeleteAction<T> implements IDeleteAction<T> {

    private final LayoutPage page;
    private final MessageDialog dialog;
    private T object;

    public AbstractDeleteAction(LayoutPage _page, String wicketId) {
        page = _page;
        dialog = new MessageDialog(wicketId, page.getString("global.popup.warning.title"), page.getString("global.popup.warning.delete.confirm"),
                DialogButtons.OK_CANCEL, DialogIcon.WARN) {

                    @Override
                    public void onClose(AjaxRequestTarget art, DialogButton button) {
                        if (button != null && button.toString().equals(LBL_OK)) {
                            deleteAction();
                            art.add(page);
                        }
                    }
                };
        page.add(dialog);

    }

    @Override
    public void delete(T _object, AjaxRequestTarget _target) {
        object = _object;
        dialog.open(_target);
    }

    public T getObject() {
        return object;
    }

    public abstract void deleteAction();

}
