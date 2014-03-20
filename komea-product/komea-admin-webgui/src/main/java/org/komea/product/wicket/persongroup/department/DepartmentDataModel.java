/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.persongroup.department;



import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.utils.IteratorUtil;



/**
 * @author rgalerme
 */
public class DepartmentDataModel extends SortableDataProvider<PersonGroup, String>
{
    
    
    private final IPersonGroupService personGroupService;
    
    
    
    public DepartmentDataModel(final IPersonGroupService personGroupService) {
    
    
        this.personGroupService = personGroupService;
    }
    
    
    @Override
    public Iterator<? extends PersonGroup> iterator(final long _first, final long _count) {
    
    
        return IteratorUtil.buildIterator(personGroupService.getAllDepartmentsPG(), _first, _count);
    }
    
    
    @Override
    public IModel<PersonGroup> model(final PersonGroup t) {
    
    
        return Model.of(t);
    }
    
    
    @Override
    public long size() {
    
    
        return personGroupService.getAllDepartments().size();
    }
    
    
}
