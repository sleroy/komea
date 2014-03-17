package org.komea.product.backend.service.entities;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.kpi.IMeasureHistoryService;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.Pair;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.HasProjectPersonGroupCriteria;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.MeasureCriteria;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonGroupService.class
            .getName());

    @Autowired
    private IPersonService personService;

    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDao;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IMeasureHistoryService measureService;

    @Autowired
    private PersonGroupDao requiredDAO;

    /**
     * Method personGroupsToBaseEntities.
     *
     * @param personGroups List<PersonGroup>
     * @param entityType EntityType
     * @return List<BaseEntityDto>
     * @see
     * org.komea.product.backend.service.entities.IPersonGroupService#personGroupsToBaseEntities(List<PersonGroup>,
     * EntityType)
     */
    @Override
    public List<BaseEntityDto> convertPersonGroupsToBaseEntities(
            final List<PersonGroup> personGroups,
            final EntityType entityType) {

        final List<BaseEntityDto> entities = new ArrayList<BaseEntityDto>(personGroups.size());
        for (final PersonGroup personGroup : personGroups) {
            final BaseEntityDto entity
                    = new BaseEntityDto(entityType, personGroup.getId(),
                            personGroup.getPersonGroupKey(), personGroup.getName(),
                            personGroup.getDescription());
            entities.add(entity);
        }
        return entities;
    }

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

    @Override
    public List<PersonGroup> getAllDepartmentsPG() {

        return getAllPersonGroups(PersonGroupType.DEPARTMENT);
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

    @Override
    public List<PersonGroup> getAllTeamsPG() {

        return getAllPersonGroups(PersonGroupType.TEAM);
    }

    @Override
    public List<PersonGroup> getChildren(final Integer groupId) {

        final PersonGroupCriteria criteria = new PersonGroupCriteria();
        criteria.createCriteria().andIdPersonGroupParentEqualTo(groupId);
        return requiredDAO.selectByCriteria(criteria);
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

    @Override
    public PersonGroup getParent(final Integer groupId) {

        return getParent(selectByPrimaryKey(groupId));
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

    @Override
    public List<PersonGroup> getTeamsOfProject(final Integer _projectId) {

        final List<PersonGroup> groupList = Lists.newArrayList();
        final HasProjectPersonGroupCriteria criteria = new HasProjectPersonGroupCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectId);
        final List<HasProjectPersonGroupKey> selection
                = projectPersonGroupDao.selectByCriteria(criteria);
        for (final HasProjectPersonGroupKey hasProjectPersonGroupKey : selection) {
            final PersonGroupCriteria departmentCriteria = new PersonGroupCriteria();
            departmentCriteria.createCriteria()
                    .andIdEqualTo(hasProjectPersonGroupKey.getIdPersonGroup())
                    .andTypeEqualTo(PersonGroupType.DEPARTMENT);
            groupList.addAll(selectByCriteria(departmentCriteria));
        }
        return groupList;
    }

    public void setRequiredDAO(final PersonGroupDao _requiredDAO) {

        requiredDAO = _requiredDAO;
    }

    private DepartmentDto convertToDepartmentDto(final PersonGroup group) {

        final DepartmentDto department = new DepartmentDto();
        department.setKey(group.getPersonGroupKey());
        department.setName(group.getName());
        department.setDescription(group.getDescription());
        final Integer groupId = group.getId();
        final List<Person> persons = personService.getPersonsOfPersonGroup(groupId);
        final List<PersonGroup> children = getChildren(group.getId());
        for (final PersonGroup child : children) {
            department.getTeams().put(child.getPersonGroupKey(), child.getName());
        }
        for (final Person person : persons) {
            department.getPersons().put(person.getLogin(), person.getFullName());
        }
        return department;
    }

    private List<DepartmentDto> convertToDepartmentDtoList(final List<PersonGroup> groups) {

        final List<DepartmentDto> departments = new ArrayList<DepartmentDto>(groups.size());
        for (final PersonGroup group : groups) {
            final DepartmentDto department = convertToDepartmentDto(group);
            departments.add(department);
        }
        return departments;
    }

    private TeamDto convertToTeamDto(final PersonGroup group) {

        final TeamDto team = new TeamDto();
        team.setKey(group.getPersonGroupKey());
        team.setName(group.getName());
        team.setDescription(group.getDescription());
        final Integer groupId = group.getId();
        final PersonGroup parent = getParent(group);
        final List<Project> projects = projectService.getProjectsOfPersonGroup(groupId);
        final List<Person> persons = personService.getPersonsOfPersonGroup(groupId);
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

    private List<TeamDto> convertToTeamDtoList(final List<PersonGroup> groups) {

        final List<TeamDto> teams = new ArrayList<TeamDto>(groups.size());
        for (final PersonGroup group : groups) {
            final TeamDto team = convertToTeamDto(group);
            teams.add(team);
        }
        return teams;
    }

    private List<PersonGroup> getAllPersonGroups(final PersonGroupType type) {

        final PersonGroupCriteria criteria = new PersonGroupCriteria();
        criteria.createCriteria().andTypeEqualTo(type);
        return selectByCriteria(criteria);
    }

    private PersonGroup getParent(final PersonGroup group) {

        final PersonGroupType parentType = getParentType(group);
        return getPersonGroup(parentType, group.getId());
    }

    private PersonGroupType getParentType(final PersonGroup group) {

        final int parentTypeOrdinal = group.getType().ordinal() + 1;
        if (PersonGroupType.values().length > parentTypeOrdinal) {
            return PersonGroupType.values()[parentTypeOrdinal];
        }
        return null;
    }

    private PersonGroup getPersonGroup(final PersonGroupType type, final Integer _groupID) {

        if (type == null || _groupID == null) {
            return null;
        }
        final PersonGroup personGroup = requiredDAO.selectByPrimaryKey(_groupID);
        if (personGroup != null && !type.equals(personGroup.getType())) {
            return getPersonGroup(
                    type, personGroup.getIdPersonGroupParent());
        }
        return personGroup;
    }

    @Override
    protected PersonGroupCriteria createPersonCriteriaOnLogin(final String key) {

        final PersonGroupCriteria criteria = new PersonGroupCriteria();
        criteria.createCriteria().andPersonGroupKeyEqualTo(key);
        return criteria;
    }

    @Override
    public void saveOrUpdatePersonGroup(final PersonGroup personGroup, final List<PersonGroup> children,
            final List<Project> projects, final List<Person> persons) {
        saveOrUpdate(personGroup);
        final Integer idPersonGroup = personGroup.getId();

        final List<PersonGroup> oldChildren = getChildren(idPersonGroup);
        for (final PersonGroup child : oldChildren) {
            child.setIdPersonGroupParent(null);
            saveOrUpdate(child);
        }
        if (children != null) {
            for (final PersonGroup child : children) {
                child.setIdPersonGroupParent(idPersonGroup);
                saveOrUpdate(child);
            }
        }

        final List<Person> oldPersons = personService.getPersonsOfPersonGroup(idPersonGroup);
        for (final Person person : oldPersons) {
            person.setIdPersonGroup(null);
            personService.saveOrUpdate(person);
        }
        if (persons != null) {
            for (final Person person : persons) {
                person.setIdPersonGroup(idPersonGroup);
                personService.saveOrUpdate(person);
            }
        }

        final HasProjectPersonGroupCriteria hasProjectPersonGroupCriteria = new HasProjectPersonGroupCriteria();
        hasProjectPersonGroupCriteria.createCriteria().andIdPersonGroupEqualTo(idPersonGroup);
        projectPersonGroupDao.deleteByCriteria(hasProjectPersonGroupCriteria);
        if (projects != null) {
            for (final Project project : projects) {
                projectPersonGroupDao.insert(new HasProjectPersonGroupKey(project.getId(), idPersonGroup));
            }
        }
    }

    @Override
    public void deletePersonGroup(final PersonGroup personGroup) {
        final Integer idPersonGroup = personGroup.getId();

        final List<PersonGroup> children = getChildren(idPersonGroup);
        for (final PersonGroup child : children) {
            child.setIdPersonGroupParent(null);
            saveOrUpdate(child);
        }

        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.createCriteria().andIdPersonGroupEqualTo(idPersonGroup);
        measureService.deleteByCriteria(measureCriteria);

        final List<Person> persons = personService.getPersonsOfPersonGroup(idPersonGroup);
        for (final Person person : persons) {
            person.setIdPersonGroup(null);
            personService.saveOrUpdate(person);
        }

        final HasProjectPersonGroupCriteria hasProjectPersonGroupCriteria = new HasProjectPersonGroupCriteria();
        hasProjectPersonGroupCriteria.createCriteria().andIdPersonGroupEqualTo(idPersonGroup);
        projectPersonGroupDao.deleteByCriteria(hasProjectPersonGroupCriteria);

        delete(personGroup);
    }
}
