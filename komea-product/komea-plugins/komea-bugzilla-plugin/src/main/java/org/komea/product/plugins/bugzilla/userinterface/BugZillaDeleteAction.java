/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.userinterface;



import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;

import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.widget.api.IDeleteAction;



/**
 * @author rgalerme
 */
public class BugZillaDeleteAction implements IDeleteAction<BZServerConfiguration>
{
    
    
    private final IBZConfigurationDAO         bugZillaService;
    private final List<BZServerConfiguration> serverAffiche;
    
    
    
    public BugZillaDeleteAction(
            final List<BZServerConfiguration> _listAffichage,
            final IBZConfigurationDAO bugZillaService) {
    
    
        serverAffiche = _listAffichage;
        this.bugZillaService = bugZillaService;
    }


    @Override
    public void delete(BZServerConfiguration _object, AjaxRequestTarget _target) {
       
        final boolean delete = bugZillaService.delete(_object);
        if (delete) {
            serverAffiche.remove(_object);
        }
    }
    
}
