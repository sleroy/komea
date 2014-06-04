/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.cronpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.wicket.widget.api.IAjaxEditAction;

/**
 *
 * @author rgalerme
 */
public class CustomPanelLaunch extends Panel {

    private final IAjaxEditAction<CronDetails> editAction;

    public CustomPanelLaunch(String id, IModel<CronDetails> imodel,final IAjaxEditAction<CronDetails> _editAction) {
        super(id,imodel);
        editAction = _editAction;
        add(new AjaxLink<String>("edit") {

            @Override
            public void onClick(final AjaxRequestTarget _target) {

                editAction.selected((CronDetails) CustomPanelLaunch.this.getDefaultModelObject(),_target);

            }
        });
    }

}