/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.persongroup.department;

import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.persongroup.department.DepartmentEditPage;
import org.komea.product.wicket.persongroup.department.DepartmentPage;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class DepartmentEditAction implements IEditAction<PersonGroup> {
    private final DepartmentPage departmentPage;

    public DepartmentEditAction(DepartmentPage departmentPage) {
        this.departmentPage = departmentPage;
    }

    @Override
    public void selected(PersonGroup _object) {
        this.departmentPage.setResponsePage(new DepartmentEditPage(this.departmentPage.getPageParameters(),_object));
    }
    
}
