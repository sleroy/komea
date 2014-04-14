/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.alert;

import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.backend.service.alert.IAlertTypeService;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public class AlertDeleteAction implements IDeleteAction<KpiAlertType> {

    private final List<KpiAlertType> alertAffichage;
    private final IAlertTypeService alertService;

    public AlertDeleteAction(List<KpiAlertType> alertAffichage, IAlertTypeService alertService) {
        this.alertAffichage = alertAffichage;
        this.alertService = alertService;
    }


    @Override
    public void delete(KpiAlertType _alert, AjaxRequestTarget _target) {
          this.alertAffichage.remove(_alert);
        this.alertService.delete(_alert);
    }

}
