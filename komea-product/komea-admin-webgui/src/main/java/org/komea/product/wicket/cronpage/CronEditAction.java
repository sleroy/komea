/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.cronpage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.widget.api.IAjaxEditAction;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class CronEditAction  implements IAjaxEditAction<CronDetails>{

    private final CronDialog cronDialog;
    
    CronEditAction(CronDialog cd) {
        cronDialog=cd;
    }

    @Override
    public void selected(CronDetails _object, AjaxRequestTarget _target) {
        cronDialog.setCronValue(_object, _target);
        cronDialog.open(_target);
    }
    
}
