/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup;

import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public class PersonGroupDeleteAction implements IDeleteAction<PersonGroup> {

    final private IPersonGroupService personGroupService;

    public PersonGroupDeleteAction(IPersonGroupService personGroupService) {
        this.personGroupService = personGroupService;
    }

    @Override
    public void delete(PersonGroup _object) {
        
        this.personGroupService.deletePersonGroup(_object);
    }

}
