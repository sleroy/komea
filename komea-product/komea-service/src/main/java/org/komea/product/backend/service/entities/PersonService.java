
package org.komea.product.backend.service.entities;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.esper.IEventConversionAndValidationService;
import org.komea.product.backend.service.kpi.IMeasureHistoryService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.Pair;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    
    private static final Logger                  LOGGER = LoggerFactory
                                                                .getLogger(PersonService.class);
    
    @Autowired
    private IEventConversionAndValidationService eventConversionAndValidationService;
    
    @Autowired
    private IPersonGroupService                  groupService;
    
    @Autowired
    private IMeasureHistoryService               measureService;
    
    @Autowired
    private IPersonRoleService                   personRoleService;
    
    @Autowired
    private IProjectPersonService                projectPersonService;
    
    @Autowired
    private IProjectService                      projectService;
    
    @Autowired
    private PersonDao                            requiredDAO;
    
    
    
    /**
     *
     */
    public PersonService() {
    
    
        super();
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IPersonService#convertAllPersonsIntoPersonDTO()
     */
    @Override
    public List<PersonDto> convertAllPersonsIntoPersonDTO() {
    
    
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
            final PersonGroup personGroup =
                    groupService.selectByPrimaryKey(person.getIdPersonGroup());
            if (personGroup != null) {
                final Pair<String, String> group =
                        Pair.create(personGroup.getPersonGroupKey(), personGroup.getDisplayName());
                if (PersonGroupType.TEAM.equals(personGroup.getType())) {
                    personDto.setTeam(group);
                } else {
                    personDto.setDepartment(group);
                }
            }
            final PersonRole role = personRoleService.selectByPrimaryKey(person.getIdPersonRole());
            if (role != null) {
                personDto.setRole(role.getName());
            }
            personDto.associateToProjectList(projectService.getProjectsOfPerson(person.getId()));
            personDtos.add(personDto);
            
        }
        return personDtos;
    }
    
    
    /**
     * Convert a person into base entity (its a dto)
     * 
     * @param persons
     *            List<Person>
     * @return List<BaseEntityDto>
     * @see
     *      org.komea.product.backend.service.entities.IPersonService#personsToBaseEntities(List<Person>)
     */
    @Override
    public List<BaseEntityDto> convertPersonsToBaseEntities(final List<Person> persons) {
    
    
        final List<BaseEntityDto> entities = new ArrayList<BaseEntityDto>(persons.size());
        for (final Person person : persons) {
            final BaseEntityDto entity =
                    new BaseEntityDto(EntityType.PERSON, person.getId(), person.getLogin(),
                            person.getFirstName() + " " + person.getLastName(),
                            person.getPassword());
            entities.add(entity);
        }
        return entities;
    }
    
    
    @Override
    public void deletePerson(final Person _person) {
    
    
        projectPersonService.updateProjectsOfPerson(Collections.<Project> emptyList(), _person);
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.createCriteria().andIdPersonEqualTo(_person.getId());
        measureService.deleteByCriteria(measureCriteria);
        delete(_person);
    }
    
    
    /**
     * Tests if the login is already present
     * 
     * @param _loginFromEmail
     *            the login
     * @return true if the login is alread present
     */
    @Override
    public boolean existLogin(final String _loginFromEmail) {
    
    
        return !selectByCriteria(createKeyCriteria(_loginFromEmail)).isEmpty();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.entities.IPersonService#existUserByEmail(java.lang.String)
     */
    @Override
    public boolean existUserByEmail(final String _email) {
    
    
        return findUserByEmail(_email) != null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.entities.IPersonService#findOrCreatePersonByEmail(java.lang.String)
     */
    @Override
    public Person findOrCreatePersonByEmail(final String _email) {
    
    
        final PersonCriteria criteria = new PersonCriteria();
        criteria.createCriteria().andEmailEqualTo(_email);
        Person personRequested = CollectionUtil.singleOrNull(selectByCriteria(criteria));
        
        if (personRequested == null) {
            final String login = stripLoginFromEmail(_email);
            personRequested = selectByKey(login);
            
            if (personRequested == null) {
                personRequested = newPerson(_email, login);
            }
            
            personRequested.setEmail(_email);
            eventConversionAndValidationService.validateObject(personRequested);
            saveOrUpdate(personRequested);
        }
        
        return personRequested;
    }
    
    
    /**
     * Find or create a person if missing by its login.
     * 
     * @param _login
     *            the person login
     * @return the entity (previously created if not).
     */
    public Person findOrCreatePersonByLogin(final String _login) {
    
    
        if (!existLogin(_login)) {
            final Person person = newPerson(_login, _login + "@dummy.org");
            saveOrUpdate(person);
            return person;
        }
        
        return selectByKey(_login);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.entities.IPersonService#findUserByEmail(java.lang.String)
     */
    @Override
    public Person findUserByEmail(final String _email) {
    
    
        final PersonCriteria personCriteria = new PersonCriteria();
        personCriteria.createCriteria().andEmailEqualTo(_email);
        return CollectionUtil.singleOrNull(selectByCriteria(personCriteria));
    }
    
    
    @Override
    public List<Person> getPersonsOfPersonGroup(final Integer groupId) {
    
    
        final PersonCriteria criteria = new PersonCriteria();
        criteria.createCriteria().andIdPersonGroupEqualTo(groupId);
        return requiredDAO.selectByCriteria(criteria);
    }
    
    
    @Override
    public List<Person> getPersonsOfProject(final Integer _projectId) {
    
    
        final List<HasProjectPersonKey> result =
                projectPersonService.getPersonIdsOfProject(_projectId);
        final List<Person> persons = new ArrayList<Person>(result.size());
        for (final HasProjectPersonKey hasProjectPersonKey : result) {
            final Person person = selectByPrimaryKey(hasProjectPersonKey.getIdPerson());
            if (person != null) {
                persons.add(person);
            }
        }
        return persons;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public IGenericDAO<Person, Integer, PersonCriteria> getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    @Override
    public void saveOrUpdatePerson(final Person _person, final List<Project> _projects) {
    
    
        saveOrUpdate(_person);
        projectPersonService.updateProjectsOfPerson(_projects, _person);
    }
    
    
    public void setEventConversionAndValidationService(
            final IEventConversionAndValidationService _eventConversionAndValidationService) {
    
    
        eventConversionAndValidationService = _eventConversionAndValidationService;
    }
    
    
    /**
     * @param _groupService
     *            the groupService to set
     */
    public void setGroupService(final IPersonGroupService _groupService) {
    
    
        groupService = _groupService;
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
     * Strip login from email.
     * 
     * @param email
     *            the email
     * @return the login.
     */
    public String stripLoginFromEmail(final String email) {
    
    
        final int indexOf = email.indexOf('@');
        if (indexOf == -1) { return email; }
        return email.substring(0, indexOf);
    }
    
    
    private Person newPerson(final String _email, final String login) {
    
    
        Person personRequested;
        LOGGER.debug("Create a person by its email since it does not exist : {}", _email);
        personRequested = new Person();
        personRequested.setLogin(login);
        personRequested.setFirstName(personRequested.getLogin());
        personRequested.setLastName("");
        personRequested.setPassword("");
        personRequested.setUserBdd(UserBdd.KOMEA);
        return personRequested;
    }
    
    
    @Override
    protected PersonCriteria createKeyCriteria(final String key) {
    
    
        final PersonCriteria criteria = new PersonCriteria();
        criteria.createCriteria().andLoginEqualTo(key);
        return criteria;
    }
}
