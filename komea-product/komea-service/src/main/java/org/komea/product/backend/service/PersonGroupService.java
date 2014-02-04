
package org.komea.product.backend.service;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.model.PersonGroup;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public final class PersonGroupService implements IPersonGroupService
{
    
    @Override
    public List<PersonGroup> getAllDepartments() {
    
        // TODO Auto-generated getAllDepartments STUB
        ArrayList<PersonGroup> departments = Lists.newArrayList();
        PersonGroup group = new PersonGroup();
        group.setName("devs");
        group.setId(1);
        group.setIdGroupKind(2);
        group.setDescription("developers Department");
        departments.add(group);
        return departments;
    }
    //
}
