/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.kpis;

/**
 *
 * @author rgalerme
 */
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.komea.product.wicket.kpis.AreYouSurePanel.ConfirmationAnswer;
 
public class YesNoPanel extends Panel {
 
    public YesNoPanel(String id, String message, final ModalWindow modalWindow, final ConfirmationAnswer answer) {
        super(id);
 
        Form yesNoForm = new Form("yesNoForm");
 
        MultiLineLabel messageLabel = new MultiLineLabel("message", message);
        yesNoForm.add(messageLabel);
        modalWindow.setTitle("Please confirm");
        modalWindow.setInitialHeight(200);
        modalWindow.setInitialWidth(350);
 
        AjaxButton yesButton = new AjaxButton("yesButton", yesNoForm) {
 
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                if (target != null) {
                    answer.setAnswer(true);
                    modalWindow.close(target);
                }
            }
        };
 
        AjaxButton noButton = new AjaxButton("noButton", yesNoForm) {
 
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                if (target != null) {
                    answer.setAnswer(false);
                    modalWindow.close(target);
                }
            }
        };
 
        yesNoForm.add(yesButton);
        yesNoForm.add(noButton);
 
        add(yesNoForm);
    }
 
}
