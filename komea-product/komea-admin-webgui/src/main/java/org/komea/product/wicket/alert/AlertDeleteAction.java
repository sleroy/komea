/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.alert;

import java.util.List;

import org.komea.product.backend.service.alert.IAlertTypeService;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

/**
 *
 * @author rgalerme
 */
public class AlertDeleteAction extends AbstractDeleteAction<KpiAlertType> {

    private final List<KpiAlertType> alertAffichage;
    private final IAlertTypeService alertService;

    public AlertDeleteAction(List<KpiAlertType> alertAffichage, IAlertTypeService alertService,LayoutPage page) {
        super(page, "dialogdelete");
        this.alertAffichage = alertAffichage;
        this.alertService = alertService;
    }



    @Override
    public void deleteAction() {
       this.alertAffichage.remove(getObject());
        this.alertService.delete(getObject());
    }

}
