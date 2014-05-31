/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.mantis.userinterface;

import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class MantisEditAction implements IEditAction<MantisServerConfiguration> {
    
    private final MantisPage bpage;
    
    public MantisEditAction(MantisPage bpage) {
        this.bpage = bpage;
    }
    
    @Override
    public void selected(MantisServerConfiguration _object) {
        this.bpage.setResponsePage(new MantisEditPage(this.bpage.getPageParameters(),_object));
    }
    
}
