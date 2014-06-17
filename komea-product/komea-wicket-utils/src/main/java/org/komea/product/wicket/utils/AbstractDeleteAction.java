/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.utils;



import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;



/**
 * @author rgalerme
 */
public abstract class AbstractDeleteAction<T> implements IDeleteAction<T>
{


    private final MessageDialog dialog;
    private T                   object;
    private final LayoutPage    page;



    public AbstractDeleteAction(final LayoutPage _page, final String wicketId) {


        page = _page;
        dialog =
                new MessageDialog(wicketId, page.getString("global.popup.warning.title"),
                        page.getString("global.popup.warning.delete.confirm"),
                        DialogButtons.OK_CANCEL, DialogIcon.WARN)
        {


            @Override
                    public void onClose(final AjaxRequestTarget art, final DialogButton button) {


                if (button != null && button.toString().equals(LBL_OK)) {
                            deleteAction();
                            art.add(page);
                        }
                    }
                };
        page.add(dialog);

    }


    @Override
    public void delete(final T _object, final AjaxRequestTarget _target) {


        object = _object;
        dialog.open(_target);
    }


    public abstract void deleteAction();


    public T getObject() {


        return object;
    }

}
