/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.cronpage;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.wicket.widget.api.IAjaxEditAction;

/**
 *
 * @author rgalerme
 */
public class CronEnableAction implements IAjaxEditAction<CronDetails> {

    private final ICronRegistryService cronService;
    private final List<CronDetails> cronTasks;
    private Component composant;

    public CronEnableAction(ICronRegistryService cronService, List<CronDetails> cronTasks) {
        this.cronService = cronService;
        this.cronTasks = cronTasks;
    }

    public Component getComposant() {
        return composant;
    }

    public void setComposant(Component composant) {
        this.composant = composant;
    }

    @Override
    public void selected(CronDetails _object, AjaxRequestTarget _target) {

        cronService.enableCronTask(_object.getCronName());
        cronTasks.clear();
        cronTasks.addAll(cronService.getCronTasks());
        _target.add(composant);

    }

}
