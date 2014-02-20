
package org.komea.product.backend.service.entities;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonGroupCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;



/**
 */
@Service
public final class PersonGroupService extends
        AbstractService<PersonGroup, Integer, PersonGroupCriteria> implements IPersonGroupService
{
    
    
    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDAO;
    
    @Autowired
    private PersonGroupDao           requiredDAO;
    
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IPersonGroupService#getAllDepartments()
     */
    @Override
    public List<DepartmentDto> getAllDepartments() {
    
    
        // TODO Auto-generated getAllDepartments STUB
        final DepartmentDto department = new DepartmentDto();
        department.setName("devs");
        department.setDescription("developers Department");
        
        final List<DepartmentDto> departmentDtos = Lists.newArrayList(department);
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
        final TeamDto teamDTO = new TeamDto();
        teamDTO.setName("komea");
        teamDTO.setDescription("komea team");
        
        final List<TeamDto> teamDtos = Lists.newArrayList(teamDTO);
        return teamDtos;
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IPersonGroupService#getDepartment(java.lang.Integer)
     */
    @Override
    public PersonGroup getDepartment(final Integer _groupID) {
    
    
        final PersonGroup group = requiredDAO.selectByPrimaryKey(_groupID);
        if (group == null) { return null; }
        if (group.getType() == PersonGroupType.DEPARTMENT) { return group; }
        return getDepartment(group.getIdPersonGroupParent());
    }
    
    
    /**
     * Method getPersonGroups.
     * 
     * @param personGroupKeys
     *            List<String>
     * @param entityType
     *            EntityType
     * @return List<PersonGroup>
     * @see org.komea.product.backend.service.entities.IPersonGroupService#getPersonGroups(List<String>, EntityType)
     */
    @Override
    public List<PersonGroup> getPersonGroups(
            final List<String> personGroupKeys,
            final EntityType entityType) {
    
    
        final PersonGroupCriteria personGroupCriteria = new PersonGroupCriteria();
        final PersonGroupType type = PersonGroupType.valueOf(entityType.name());
        if (personGroupKeys.isEmpty()) {
            personGroupCriteria.createCriteria().andTypeEqualTo(type);
        } else {
            for (final String entityKey : personGroupKeys) {
                final PersonGroupCriteria.Criteria criteria = personGroupCriteria.or();
                criteria.andPersonGroupKeyEqualTo(entityKey).andTypeEqualTo(type);
            }
        }
        return requiredDAO.selectByCriteria(personGroupCriteria);
    }
    
    
    @Override
    public PersonGroupDao getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    /**
     * Method getTeam.
     * 
     * @param _groupID
     *            Integer
     * @return PersonGroup
     * @see org.komea.product.backend.service.entities.IPersonGroupService#getTeam(Integer)
     */
    @Override
    public PersonGroup getTeam(final Integer _groupID) {
    
    
        final PersonGroup group = requiredDAO.selectByPrimaryKey(_groupID);
        if (group == null) { return null; }
        if (group.getType() == PersonGroupType.TEAM) { return group; }
        return getTeam(group.getIdPersonGroupParent());
    }
    
    
    /**
     * Method personGroupsToBaseEntities.
     * 
     * @param personGroups
     *            List<PersonGroup>
     * @param entityType
     *            EntityType
     * @return List<BaseEntity>
     * @see org.komea.product.backend.service.entities.IPersonGroupService#personGroupsToBaseEntities(List<PersonGroup>, EntityType)
     */
    @Override
    public List<BaseEntity> personGroupsToBaseEntities(
            final List<PersonGroup> personGroups,
            final EntityType entityType) {
    
    
        final List<BaseEntity> entities = new ArrayList<BaseEntity>(personGroups.size());
        for (final PersonGroup personGroup : personGroups) {
            final BaseEntity entity =
                    new BaseEntity(entityType, personGroup.getId(),
                            personGroup.getPersonGroupKey(), personGroup.getName(),
                            personGroup.getDescription());
            entities.add(entity);
        }
        return entities;
    }
    
    
    public void setRequiredDAO(final PersonGroupDao _requiredDAO) {
    
    
        requiredDAO = _requiredDAO;
    }
}
