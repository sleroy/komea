/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup;

import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public class PersonGroupDeleteAction implements IDeleteAction<PersonGroup> {

    final private IPersonGroupService personGroupService;
    private final List<PersonGroup> PersonGroupAffichage;

    public PersonGroupDeleteAction(IPersonGroupService personGroupService, List<PersonGroup> _PersonGroupAffichage) {
        this.personGroupService = personGroupService;
        this.PersonGroupAffichage = _PersonGroupAffichage;
    }


    @Override
    public void delete(PersonGroup _object, AjaxRequestTarget _target) {
        this.personGroupService.deletePersonGroup(_object);
        this.PersonGroupAffichage.remove(_object);
    }

}
