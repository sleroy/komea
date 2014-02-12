
package org.komea.product.backend.service.entities;


import java.util.List;

import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public final class PersonGroupService implements IPersonGroupService {
    
    @Autowired
    private PersonGroupDao           personGroupDao;
    
    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDAO;
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IPersonGroupService#getAllDepartments()
     */
    @Override
    public List<DepartmentDto> getAllDepartments() {
    
        // TODO Auto-generated getAllDepartments STUB
        DepartmentDto department = new DepartmentDto();
        department.setName("devs");
        department.setDescription("developers Department");
        
        List<DepartmentDto> departmentDtos = Lists.newArrayList(department);
        return departmentDtos;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IPersonGroupService#getAllTeams()
     */
    @Override
    public List<TeamDto> getAllTeams() {
    
        // TODO Auto-generated getAllDepartments STUB
        TeamDto teamDTO = new TeamDto();
        teamDTO.setName("komea");
        teamDTO.setDescription("komea team");
        
        List<TeamDto> teamDtos = Lists.newArrayList(teamDTO);
        return teamDtos;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IPersonGroupService#getDepartment(java.lang.Integer)
     */
    @Override
    public PersonGroup getDepartment(final Integer _groupID) {
    
        PersonGroup group = personGroupDao.selectByPrimaryKey(_groupID);
        if (group == null) {
            return null;
        }
        if (group.getType() == PersonGroupType.DEPARTMENT) {
            return group;
        }
        return getDepartment(group.getIdPersonGroupParent());
    }
    
    @Override
    public PersonGroup getTeam(final Integer _groupID) {
    
        PersonGroup group = personGroupDao.selectByPrimaryKey(_groupID);
        if (group == null) {
            return null;
        }
        if (group.getType() == PersonGroupType.TEAM) {
            return group;
        }
        return getTeam(group.getIdPersonGroupParent());
    }
}
