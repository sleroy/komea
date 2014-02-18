package org.komea.product.backend.service.entities;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
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

@Service
public final class PersonGroupService implements IPersonGroupService {

    @Autowired
    private PersonGroupDao personGroupDao;

    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDAO;

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#getAllDepartments()
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
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#getAllTeams()
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
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#getDepartment(java.lang.Integer)
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

    @Override
    public List<PersonGroup> getPersonGroups(final List<String> personGroupKeys,
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
        return personGroupDao.selectByCriteria(personGroupCriteria);
    }

    @Override
    public List<BaseEntity> personGroupsToBaseEntities(List<PersonGroup> personGroups,
            final EntityType entityType) {
        final List<BaseEntity> entities = new ArrayList<BaseEntity>(personGroups.size());
        for (final PersonGroup personGroup : personGroups) {
            final BaseEntity entity = new BaseEntity(entityType, personGroup.getId(),
                    personGroup.getPersonGroupKey(), personGroup.getName(), personGroup.getDescription());
            entities.add(entity);
        }
        return entities;
    }
}
