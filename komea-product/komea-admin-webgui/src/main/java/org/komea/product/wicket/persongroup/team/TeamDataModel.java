/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.persongroup.team;

import java.util.Iterator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.model.PersonGroup;

/**
 *
 * @author rgalerme
 */
public class TeamDataModel extends SortableDataProvider<PersonGroup, String>  {

    private final IPersonGroupService personGroupService;

    public TeamDataModel(IPersonGroupService personGroupService) {
        this.personGroupService = personGroupService;
    }

    @Override
    public Iterator<? extends PersonGroup> iterator(long l, long l1) {
        
        return this.personGroupService.getAllTeamsPG().subList((int)l, (int)l1).iterator();
    }

    @Override
    public long size() {
        return this.personGroupService.getAllDepartments().size();
    }

    @Override
    public IModel<PersonGroup> model(PersonGroup t) {
        return Model.of(t);
    }
    


 
}
