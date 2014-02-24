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
import java.io.Serializable;
import java.util.Map;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
 
public abstract class AreYouSurePanel extends Panel {
 
    protected ModalWindow confirmModal;
    protected ConfirmationAnswer answer;
    protected Map<String,String> modifiersToApply;
 
    public AreYouSurePanel(String id, String buttonName, String modalMessageText) {
        super(id);
        answer = new ConfirmationAnswer(false);
        addElements(id, buttonName, modalMessageText);
    }
 
    protected void addElements(String id, String buttonName, String modalMessageText) {
 
        confirmModal = createConfirmModal(id, modalMessageText);
 
        Form form = new Form("confirmForm");
        add(form);
 
        AjaxButton confirmButton = new AjaxButton("confirmButton", new Model(buttonName)) {
 
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                confirmModal.show(target);
            }
        };
 
        form.add(confirmButton);
 
        form.add(confirmModal);
 
    }
 
    protected abstract void onConfirm(AjaxRequestTarget target);
    protected abstract void onCancel(AjaxRequestTarget target);
 
    protected ModalWindow createConfirmModal(String id, String modalMessageText) {
 
        ModalWindow modalWindow = new ModalWindow("modal");
        modalWindow.setCookieName(id);
        modalWindow.setContent(new YesNoPanel(modalWindow.getContentId(), modalMessageText, modalWindow, answer));
        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
 
            @Override
            public void onClose(AjaxRequestTarget target) {
                if (answer.isAnswer()) {
                    onConfirm(target);
                } else {
                    onCancel(target);
                }
            }
        });
 
        return modalWindow;
    }
 
    public static class ConfirmationAnswer implements Serializable {
 
        private boolean answer;
 
        public ConfirmationAnswer(boolean answer) {
            this.answer = answer;
        }
 
        public boolean isAnswer() {
            return answer;
        }
 
        public void setAnswer(boolean answer) {
            this.answer = answer;
        }
    }
 
}