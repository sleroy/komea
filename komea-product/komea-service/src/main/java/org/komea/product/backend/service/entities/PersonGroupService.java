package org.komea.product.backend.service.entities;

import java.util.ArrayList;
import java.util.List;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.Pair;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public final class PersonGroupService extends
        AbstractService<PersonGroup, Integer, PersonGroupCriteria> implements IPersonGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonGroupService.class.getName());

    @Autowired
    private PersonGroupDao requiredDAO;

    @Autowired
    private IProjectPersonGroupService projectPersonGroupService;

    @Autowired
    private IPersonService personService;

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#getAllDepartments()
     */
    @Override
    public List<DepartmentDto> getAllDepartments() {
        final List<PersonGroup> groups = getAllPersonGroups(PersonGroupType.DEPARTMENT);
        return convertToDepartmentDtoList(groups);
    }

    private List<PersonGroup> getAllPersonGroups(final PersonGroupType type) {
        final PersonGroupCriteria criteria = new PersonGroupCriteria();
        criteria.createCriteria().andTypeEqualTo(type);
        return selectByCriteria(criteria);
    }

    /**
     * get all teams converted in TeamDto
     *
     * @return teams
     */
    @Override
    public List<TeamDto> getAllTeams() {
        final List<PersonGroup> groups = getAllPersonGroups(PersonGroupType.TEAM);
        return convertToTeamDtoList(groups);
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#getDepartment(java.lang.Integer)
     */
    @Override
    public PersonGroup getDepartment(final Integer _groupID) {
        return getPersonGroup(PersonGroupType.DEPARTMENT, _groupID);
    }

    private PersonGroup getPersonGroup(final PersonGroupType type, final Integer _groupID) {
        if (type == null || _groupID == null) {
            return null;
        }
        final PersonGroup personGroup = requiredDAO.selectByPrimaryKey(_groupID);
        if (personGroup != null && !type.equals(personGroup.getType())) {
            return getPersonGroup(type, personGroup.getIdPersonGroupParent());
        }
        return personGroup;
    }

    /**
     * Method getPersonGroups.
     *
     * @param personGroupKeys List<String>
     * @param entityType EntityType
     * @return List<PersonGroup>
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#getPersonGroups(List<String>,
     * EntityType)
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
     * @param _groupID Integer
     * @return PersonGroup
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#getTeam(Integer)
     */
    @Override
    public PersonGroup getTeam(final Integer _groupID) {
        return getPersonGroup(PersonGroupType.TEAM, _groupID);
    }

    /**
     * Method personGroupsToBaseEntities.
     *
     * @param personGroups List<PersonGroup>
     * @param entityType EntityType
     * @return List<BaseEntity>
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#personGroupsToBaseEntities(List<PersonGroup>,
     * EntityType)
     */
    @Override
    public List<BaseEntity> personGroupsToBaseEntities(
            final List<PersonGroup> personGroups,
            final EntityType entityType) {

        final List<BaseEntity> entities = new ArrayList<BaseEntity>(personGroups.size());
        for (final PersonGroup personGroup : personGroups) {
            final BaseEntity entity
                    = new BaseEntity(entityType, personGroup.getId(),
                            personGroup.getPersonGroupKey(), personGroup.getName(),
                            personGroup.getDescription());
            entities.add(entity);
        }
        return entities;
    }

    public void setRequiredDAO(final PersonGroupDao _requiredDAO) {

        requiredDAO = _requiredDAO;
    }

    private PersonGroupType getParentType(final PersonGroup group) {
        final int parentTypeOrdinal = group.getType().ordinal() + 1;
        if (PersonGroupType.values().length > parentTypeOrdinal) {
            return PersonGroupType.values()[parentTypeOrdinal];
        }
        return null;
    }

    private PersonGroup getParent(final PersonGroup group) {
        final Integer idParent = group.getIdPersonGroupParent();
        final PersonGroupType parentType = getParentType(group);
        return getPersonGroup(parentType, idParent);
    }

    private List<PersonGroup> getChildren(final PersonGroup group) {
        final PersonGroupCriteria criteria = new PersonGroupCriteria();
        criteria.createCriteria().andIdPersonGroupParentEqualTo(group.getId());
        return requiredDAO.selectByCriteria(criteria);
    }

    private List<TeamDto> convertToTeamDtoList(final List<PersonGroup> groups) {
        final List<TeamDto> teams = new ArrayList<TeamDto>(groups.size());
        for (final PersonGroup group : groups) {
            final TeamDto team = convertToTeamDto(group);
            teams.add(team);
        }
        return teams;
    }

    private List<DepartmentDto> convertToDepartmentDtoList(final List<PersonGroup> groups) {
        final List<DepartmentDto> departments = new ArrayList<DepartmentDto>(groups.size());
        for (final PersonGroup group : groups) {
            final DepartmentDto department = convertToDepartmentDto(group);
            departments.add(department);
        }
        return departments;
    }

    private DepartmentDto convertToDepartmentDto(final PersonGroup group) {
        final DepartmentDto department = new DepartmentDto();
        department.setKey(group.getPersonGroupKey());
        department.setName(group.getName());
        department.setDescription(group.getDescription());
        final Integer groupId = group.getId();
        final List<Person> persons = personService.getPersonsOfGroup(groupId);
        final List<PersonGroup> children = getChildren(group);
        for (final PersonGroup child : children) {
            department.getTeams().put(child.getPersonGroupKey(), child.getName());
        }
        for (final Person person : persons) {
            department.getPersons().put(person.getLogin(), person.getFullName());
        }
        return department;
    }

    private TeamDto convertToTeamDto(final PersonGroup group) {
        final TeamDto team = new TeamDto();
        team.setKey(group.getPersonGroupKey());
        team.setName(group.getName());
        team.setDescription(group.getDescription());
        final Integer groupId = group.getId();
        final PersonGroup parent = getParent(group);
        final List<Project> projects = projectPersonGroupService.getProjectsAssociateToPersonGroup(groupId);
        final List<Person> persons = personService.getPersonsOfGroup(groupId);
        if (parent != null) {
            team.setDepartment(Pair.create(parent.getPersonGroupKey(), parent.getName()));
        }
        for (final Person person : persons) {
            team.getPersons().put(person.getLogin(), person.getFullName());
        }
        for (final Project project : projects) {
            team.getProjects().put(project.getProjectKey(), project.getName());
        }
        return team;
    }

    @Override
    public List<PersonGroup> getAllDepartmentsPG() {
        return getAllPersonGroups(PersonGroupType.DEPARTMENT);
    }

    @Override
    public List<PersonGroup> getAllTeamsPG() {
        return getAllPersonGroups(PersonGroupType.TEAM);
    }

    @Override
    protected PersonGroupCriteria getCriteriaKey(String key) {
        final PersonGroupCriteria criteria = new PersonGroupCriteria();
        criteria.createCriteria().andPersonGroupKeyEqualTo(key);
        return criteria;
    }
}
