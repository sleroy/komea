/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.alert;

import org.komea.product.database.model.KpiAlertType;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class AlertEditAction implements IEditAction<KpiAlertType> {

    private final AlertPage alertPage;

    public AlertEditAction(AlertPage alertPage) {
        this.alertPage = alertPage;
    }
    
    
    @Override
    public void selected(KpiAlertType _alert) {
        this.alertPage.setResponsePage(new AlertEditPage(this.alertPage.getPageParameters(),_alert));
    }
    
}
