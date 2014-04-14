/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.userinterface;

import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class BugZillaEditAction implements IEditAction<BZServerConfiguration> {
    
    private final BugZillaPage bpage;
    
    public BugZillaEditAction(BugZillaPage bpage) {
        this.bpage = bpage;
    }
    
    @Override
    public void selected(BZServerConfiguration _object) {
        this.bpage.setResponsePage(new BugZillaEditPage(this.bpage.getPageParameters(),_object));
    }
    
}
