/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 *
 * @author rgalerme
 */
public class DisplayTraceDialog extends AbstractDialog<String> {

    private final Form form;

    public DisplayTraceDialog(String id, IModel<String> title, IModel<String> text) {
        super(id, title);
        form = new Form<String>("formTrace");
        add(form);
        form.add(new Label("labelText", text));
    }

    protected final DialogButton btnSure = new DialogButton("Ok");

    @Override
    public void onConfigure(JQueryBehavior behavior) {
        super.onConfigure(behavior); //To change body of generated methods, choose Tools | Templates.
        behavior.setOption("width", 860);
        behavior.setOption("height", 630);

    }

    @Override
    public void onClose(AjaxRequestTarget art, DialogButton db) {
        // noting to do
    }

    @Override
    public boolean isModal() {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    protected void onOpen(AjaxRequestTarget target) {
        target.add(form);
    }

    @Override
    protected List<DialogButton> getButtons() {
        return Arrays.asList(btnSure);
    }

}
