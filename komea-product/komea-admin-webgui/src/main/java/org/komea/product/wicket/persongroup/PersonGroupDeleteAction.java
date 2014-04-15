/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup;

import java.util.List;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

/**
 *
 * @author rgalerme
 */
public class PersonGroupDeleteAction extends AbstractDeleteAction<PersonGroup> {

    final private IPersonGroupService personGroupService;
    private final List<PersonGroup> PersonGroupAffichage;

    public PersonGroupDeleteAction(IPersonGroupService personGroupService, List<PersonGroup> _PersonGroupAffichage,LayoutPage page) {
        super(page, "dialogdelete");
        this.personGroupService = personGroupService;
        this.PersonGroupAffichage = _PersonGroupAffichage;
    }

    @Override
    public void deleteAction() {
        this.personGroupService.deletePersonGroup(getObject());
        this.PersonGroupAffichage.remove(getObject());
    }

}
