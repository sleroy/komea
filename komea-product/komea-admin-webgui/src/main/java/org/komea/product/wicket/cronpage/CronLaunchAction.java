/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.cronpage;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.wicket.widget.api.IAjaxEditAction;

/**
 *
 * @author rgalerme
 */
public class CronLaunchAction implements IAjaxEditAction<CronDetails> {

    private final ICronRegistryService cronService;
    private  Component composant;

    public CronLaunchAction(ICronRegistryService cronService) {
        this.cronService = cronService;
       
    }

    public void setComposant(Component composant) {
        this.composant = composant;
    }
    
    

    @Override
    public void selected(CronDetails _object, AjaxRequestTarget _target) {
        cronService.forceNow(_object.getCronName());
        _target.add(composant);
    }

}
