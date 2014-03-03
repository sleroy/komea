/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.persongroup.team;

import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class TeamEditAction implements IEditAction<PersonGroup> {
    
    private final TeamPage teamPage;
    
    public TeamEditAction(TeamPage teamPage) {
        this.teamPage = teamPage;
    }
    
    @Override
    public void selected(PersonGroup _object) {
        this.teamPage.setResponsePage(new TeamEditPage(this.teamPage.getPageParameters(),_object));
    }
    
}
