
package org.komea.product.backend.service.entities;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;



/**
 */
@Service
@Transactional
public class PersonService extends AbstractService<Person, Integer, PersonCriteria> implements
        IPersonService
{
    
    
    @Autowired
    private IPersonGroupService   groupService;
    
    @Autowired
    private ProjectDao            projectDAO;
    
    @Autowired
    private HasProjectPersonDao   projectPersonDao;
    
    @Autowired
    private IProjectPersonService projectPersonService;
    
    @Autowired
    private PersonDao             requiredDAO;
    @Autowired
    private PersonRoleDao         roleDao;
    
    
    
    /**
     * 
     */
    public PersonService() {
    
    
        super();
    }
    
    
    /**
     * @return the groupService
     */
    public IPersonGroupService getGroupService() {
    
    
        return groupService;
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IPersonService#getPersonList()
     */
    @Override
    public List<PersonDto> getPersonList() {
    
    
        // TOTO STUB
        final PersonCriteria request = new PersonCriteria();
        final List<Person> persons = requiredDAO.selectByCriteria(request);
        
        final List<PersonDto> personDtos = Lists.newArrayList();
        for (final Person person : persons) {
            final PersonDto personDto = new PersonDto();
            personDto.setId(person.getId());
            personDto.setEmail(person.getEmail());
            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            personDto.setLogin(person.getLogin());
            personDto.modifyDepartment(groupService.getDepartment(person.getIdPersonGroup()));
            personDto.modifyTeam(groupService.getTeam(person.getIdPersonGroup()));
            final PersonRole role = roleDao.selectByPrimaryKey(person.getIdPersonRole());
            if (role != null) {
                personDto.setRole(role.getName());
            }
            personDto.associateToProjectList(getProjectsAssociateToAPerson(person.getId()));
            personDtos.add(personDto);
            
        }
        return personDtos;
    }
    
    
    /**
     * Method getPersons.
     * 
     * @param logins
     *            List<String>
     * @return List<Person>
     * @see org.komea.product.backend.service.entities.IPersonService#getPersons(List<String>)
     */
    @Override
    public List<Person> getPersons(final List<String> logins) {
    
    
        final PersonCriteria personCriteria = new PersonCriteria();
        if (logins.isEmpty()) {
            personCriteria.createCriteria();
        } else {
            for (final String entityKey : logins) {
                final PersonCriteria.Criteria criteria = personCriteria.or();
                criteria.andLoginEqualTo(entityKey);
            }
        }
        return requiredDAO.selectByCriteria(personCriteria);
    }
    
    
    /**
     * @return the projectDAO
     */
    public ProjectDao getProjectDAO() {
    
    
        return projectDAO;
    }
    
    
    /**
     * @return the projectPersonDao
     */
    public HasProjectPersonDao getProjectPersonDao() {
    
    
        return projectPersonDao;
    }
    
    
    /**
     * @return the projectPersonService
     */
    public IProjectPersonService getProjectPersonService() {
    
    
        return projectPersonService;
    }
    
    
    /**
     * Method getProjectsAssociateToAPerson.
     * 
     * @param _personId
     *            Integer
     * @return List<Project>
     * @see org.komea.product.backend.service.entities.IPersonService#getProjectsAssociateToAPerson(Integer)
     */
    @Override
    public List<Project> getProjectsAssociateToAPerson(final Integer _personId) {
    
    
        final HasProjectPersonCriteria criteria = new HasProjectPersonCriteria();
        criteria.createCriteria().andIdPersonEqualTo(_personId);
        final List<HasProjectPersonKey> result = projectPersonDao.selectByCriteria(criteria);
        final List<Project> projects = Lists.newArrayList();
        for (final HasProjectPersonKey hasProjectPersonKey : result) {
            final Project project =
                    projectDAO.selectByPrimaryKey(hasProjectPersonKey.getIdProject());
            if (project != null) {
                projects.add(project);
            }
        }
        return projects;
    }
    
    
    @Override
    public PersonDao getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    /**
     * @return the roleDao
     */
    public PersonRoleDao getRoleDao() {
    
    
        return roleDao;
    }
    
    
    /**
     * Method personsToBaseEntities.
     * 
     * @param persons
     *            List<Person>
     * @return List<BaseEntity>
     * @see org.komea.product.backend.service.entities.IPersonService#personsToBaseEntities(List<Person>)
     */
    @Override
    public List<BaseEntity> personsToBaseEntities(final List<Person> persons) {
    
    
        final List<BaseEntity> entities = new ArrayList<BaseEntity>(persons.size());
        for (final Person person : persons) {
            final BaseEntity entity =
                    new BaseEntity(EntityType.PERSON, person.getId(), person.getLogin(),
                            person.getFirstName() + " " + person.getLastName(),
                            person.getPassword());
            entities.add(entity);
        }
        return entities;
    }
    
    
    /**
     * Method saveOrUpdate.
     * 
     * @param _person
     *            Person
     * @param _selectedProject
     *            Project
     * @param _personRole
     *            PersonRole
     * @param _personGroup
     *            PersonGroup
     */
    @Override
    public void saveOrUpdate(
            final Person _person,
            final Project _selectedProject,
            final PersonRole _personRole,
            final PersonGroup _personGroup) {
    
    
        getDaoEventRegistry().notifyUpdated(_person);
        getDaoEventRegistry().notifyUpdated(_selectedProject);
        getDaoEventRegistry().notifyUpdated(_personRole);
        getDaoEventRegistry().notifyUpdated(_personGroup);
        if (_personRole != null) {
            _person.setIdPersonRole(_personRole.getId());
        } else {
            _person.setIdPersonRole(null);
        }
        if (_personGroup != null) {
            _person.setIdPersonGroup(_personGroup.getId());
        } else {
            _person.setIdPersonGroup(null);
        }
        
        if (_person.getId() != null) {
            requiredDAO.updateByPrimaryKey(_person);
        } else {
            requiredDAO.insert(_person);
        }
        projectPersonService.updateProjectPersonLink(_selectedProject, _person);
        
    }
    
    
    /**
     * @param _groupService
     *            the groupService to set
     */
    public void setGroupService(final IPersonGroupService _groupService) {
    
    
        groupService = _groupService;
    }
    
    
    /**
     * @param _projectDAO
     *            the projectDAO to set
     */
    public void setProjectDAO(final ProjectDao _projectDAO) {
    
    
        projectDAO = _projectDAO;
    }
    
    
    /**
     * @param _projectPersonDao
     *            the projectPersonDao to set
     */
    public void setProjectPersonDao(final HasProjectPersonDao _projectPersonDao) {
    
    
        projectPersonDao = _projectPersonDao;
    }
    
    
    /**
     * @param _projectPersonService
     *            the projectPersonService to set
     */
    public void setProjectPersonService(final IProjectPersonService _projectPersonService) {
    
    
        projectPersonService = _projectPersonService;
    }
    
    
    public void setRequiredDAO(final PersonDao _requiredDAO) {
    
    
        requiredDAO = _requiredDAO;
    }
    
    
    /**
     * @param _roleDao
     *            the roleDao to set
     */
    public void setRoleDao(final PersonRoleDao _roleDao) {
    
    
        roleDao = _roleDao;
    }
}
