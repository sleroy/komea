/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.cronpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.wicket.widget.api.IAjaxEditAction;
import org.quartz.Trigger;

/**
 *
 * @author rgalerme
 */
public class CustomPanelLaunch extends Panel {

    private final IAjaxEditAction<CronDetails> editAction;
    private final IAjaxEditAction<CronDetails> enableAction;
    private final IAjaxEditAction<CronDetails> disableAction;
    private final AjaxLink<String> buttonEnable;
    private final AjaxLink<String> buttonDisable;

    public CustomPanelLaunch(String id, IModel<CronDetails> imodel, final IAjaxEditAction<CronDetails> _editAction, final IAjaxEditAction<CronDetails> _activeAction, final IAjaxEditAction<CronDetails> _disalbeAction) {
        super(id, imodel);
        enableAction = _activeAction;
        editAction = _editAction;
        disableAction = _disalbeAction;
        buttonEnable = new AjaxLinkEnable("disable");
        buttonDisable = new AjaxLinkDisable("enable");

        buttonEnable.setOutputMarkupId(true);
        buttonEnable.setOutputMarkupPlaceholderTag(true);
        buttonDisable.setOutputMarkupId(true);
        buttonDisable.setOutputMarkupPlaceholderTag(true);

        add(buttonDisable);
        add(buttonEnable);

        CronDetails object = imodel.getObject();
        Trigger.TriggerState triggerState = object.getTriggerState();
        if (triggerState.equals(triggerState.PAUSED)) {
            buttonDisable.setVisible(false);

        } else {
            buttonEnable.setVisible(false);
        }

        add(new AjaxLink<String>("edit") {

            @Override
            public void onClick(final AjaxRequestTarget _target) {

                editAction.selected((CronDetails) CustomPanelLaunch.this.getDefaultModelObject(), _target);

            }
        });

    }

    private class AjaxLinkDisable extends AjaxLink<String> {

        public AjaxLinkDisable(String id) {
            super(id);
        }

        @Override
        public void onClick(final AjaxRequestTarget _target) {

            buttonDisable.setVisible(false);
            buttonEnable.setVisible(true);
            _target.add(buttonDisable);
            _target.add(buttonEnable);
            disableAction.selected((CronDetails) CustomPanelLaunch.this.getDefaultModelObject(), _target);

        }
    }

    private class AjaxLinkEnable extends AjaxLink<String> {

        public AjaxLinkEnable(String id) {
            super(id);
        }

        @Override
        public void onClick(final AjaxRequestTarget _target) {

            buttonEnable.setVisible(false);
            buttonDisable.setVisible(true);
            _target.add(buttonDisable);
            _target.add(buttonEnable);
            enableAction.selected((CronDetails) CustomPanelLaunch.this.getDefaultModelObject(), _target);

        }
    }

}
