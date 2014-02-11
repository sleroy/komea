
package org.komea.product.backend.service;


import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.HasProjectTagDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonGroupCriteria;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public final class EntityService implements IEntityService {
    
    @Autowired
    private PersonDao                personDAO;
    
    @Autowired
    private CustomerDao              customerDAO;
    
    @Autowired
    private PersonGroupDao           personGroupDao;
    
    @Autowired
    private ProjectDao               projectDao;
    
    @Autowired
    private HasProjectPersonDao      projectPersonDAO;
    
    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDAO;
    
    @Autowired
    private HasProjectTagDao         projectTagsAO;
    
    public EntityService() {
    
        super();
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.IEntityService#getEntity(org.komea.product.database.enums.EntityType, int)
     */
    @Override
    public <TEntity extends IEntity> TEntity getEntity(final EntityType _entityType, final int _key) {
    
        switch (_entityType) {
            case PERSON:
                return (TEntity) personDAO.selectByPrimaryKey(_key);
            case PERSON_GROUP:
                return (TEntity) personGroupDao.selectByPrimaryKey(_key);
            case PROJECT:
                return (TEntity) projectDao.selectByPrimaryKey(_key);
        }
        return null;
        
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.IEntityService#getEntityAssociatedToKpi(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public IEntity getEntityAssociatedToKpi(final KpiKey _kpiKey) {
    
        return getEntity(_kpiKey.getEntityType(), _kpiKey.getEntityID());
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.IEntityService#getEntityOrFail(org.komea.product.database.enums.EntityType, int)
     */
    @Override
    public IEntity getEntityOrFail(final EntityType _entityType, final int _entityID) {
    
        final IEntity entity = getEntity(_entityType, _entityID);
        if (entity == null) {
            throw new EntityNotFoundException(_entityID, _entityType);
        }
        return entity;
    }
    
    @Override
    public <TEntity extends IEntity> List<TEntity> loadEntities(final EntityType _entityType, final List<Integer> _keys) {
    
        final List<TEntity> listOfEntities = new ArrayList<TEntity>(_keys.size());
        for (final Integer key : _keys) {
            IEntity entity = getEntity(_entityType, key);
            if (entity != null) {
                listOfEntities.add((TEntity) entity);
            }
        }
        return listOfEntities;
    }
    
    @Override
    public List<PersonDto> getPersonList() {
    
        // TOTO STUB
        PersonCriteria request = new PersonCriteria();
        List<Person> persons = personDAO.selectByCriteria(request);
        
        List<PersonDto> personDtos = Lists.newArrayList();
        for (Person person : persons) {
            PersonDto personDto = new PersonDto();
            personDto.setEmail(person.getEmail());
            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            personDto.setLogin(person.getLogin());
            List<PersonGroup> departments = getDepartment(person);
            personDtos.add(personDto);
            
        }
        return personDtos;
    }
    
    private List<PersonGroup> getDepartment(final Person _person) {
    
        return null;
    }
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.IEntityService#getAllDepartments()
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
     * @see org.komea.product.backend.service.IEntityService#getAllProjects()
     */
    @Override
    public List<ProjectDto> getAllProjects() {
    
        // TOTO STUB
        ProjectCriteria request = new ProjectCriteria();
        List<Project> projects = projectDao.selectByCriteria(request);
        List<ProjectDto> projectDTOs = Lists.newArrayList();
        for (Project project : projects) {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setDescription(project.getDescription());
            projectDto.setName(project.getName());
            projectDto.setProjectKey(project.getProjectKey());
            // projectDto.associatePersonList(getPersonsAssociateToProject(project.getId()));
            // projectDto.associateTeamList(getTeamsAssociateToProject(project.getId()));
            // projectDto.setCustomer(customerDAO.selectByPrimaryKey(project.getIdCustomer()).getName());
            // projectDto.setTags(getProjectTags(project.getId()));
            // projectDTOs.add(projectDto);
        }
        
        // TODO
        return projectDTOs;
    }
    
    private List<String> getProjectTags(final Integer _id) {
    
        // projectTagsAO.selectByCriteria(null);
        return null;
    }
    private List<PersonGroup> getTeamsAssociateToProject(final Integer _projectID) {
    
        List<PersonGroup> groupList = Lists.newArrayList();
        HasProjectPersonGroupCriteria criteria = new HasProjectPersonGroupCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectID);
        List<HasProjectPersonGroupKey> selection = projectPersonGroupDAO.selectByCriteria(criteria);
        for (HasProjectPersonGroupKey hasProjectPersonGroupKey : selection) {
            PersonGroup personGroup = personGroupDao.selectByPrimaryKey(hasProjectPersonGroupKey.getIdPersonGroup());
            if (personGroup.getType() == PersonGroupType.TEAM) {
                groupList.add(personGroup);
            }
        }
        return groupList;
    }
    
    private List<Person> getPersonsAssociateToProject(final int _projectID) {
    
        List<Person> personList = Lists.newArrayList();
        HasProjectPersonCriteria criteria = new HasProjectPersonCriteria();
        criteria.createCriteria().andIdProjectEqualTo(_projectID);
        List<HasProjectPersonKey> selection = projectPersonDAO.selectByCriteria(criteria);
        for (HasProjectPersonKey hasProjectPersonKey : selection) {
            personList.add(personDAO.selectByPrimaryKey(hasProjectPersonKey.getIdPerson()));
        }
        return personList;
    }
    
    @Override
    public List<TeamDto> getAllTeams() {
    
        // TODO Auto-generated getAllDepartments STUB
        TeamDto teamDTO = new TeamDto();
        teamDTO.setName("devs");
        teamDTO.setDescription("developers Department");
        
        List<TeamDto> teamDtos = Lists.newArrayList(teamDTO);
        return teamDtos;
    }
}
