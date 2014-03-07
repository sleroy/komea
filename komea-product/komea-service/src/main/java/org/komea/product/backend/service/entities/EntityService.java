
package org.komea.product.backend.service.entities;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 */
@Service
@Transactional
public final class EntityService implements IEntityService
{
    
    
    @Autowired
    private PersonDao           personDAO;
    
    @Autowired
    private PersonGroupDao      personGroupDao;
    
    @Autowired
    private IPersonGroupService personGroupService;
    
    @Autowired
    private IPersonService      personService;
    
    @Autowired
    private ProjectDao          projectDao;
    
    @Autowired
    private IProjectService     projectService;
    
    
    
    public EntityService() {
    
    
        super();
    }
    
    
    /**
     * Method getEntities.
     * 
     * @param _entityType
     *            EntityType
     * @param _entityKeys
     *            List<String>
     * @return List<BaseEntity>
     */
    @Override
    public List<BaseEntity> getEntities(final EntityType _entityType, final List<String> _entityKeys) {
    
    
        final List<BaseEntity> entities = new ArrayList<BaseEntity>(_entityKeys.size());
        switch (_entityType) {
            case PERSON:
                final List<Person> persons = personService.searchPersonWithGivenLogin(_entityKeys);
                entities.addAll(personService.convertPersonsToBaseEntities(persons));
                break;
            case TEAM:
            case DEPARTMENT:
                final List<PersonGroup> personGroups =
                        personGroupService.getPersonGroups(_entityKeys, _entityType);
                entities.addAll(personGroupService.personGroupsToBaseEntities(personGroups,
                        _entityType));
                break;
            case PROJECT:
                final List<Project> projects = projectService.getProjects(_entityKeys);
                entities.addAll(projectService.projectsToBaseEntities(projects));
                break;
            case SYSTEM:
                entities.add(new BaseEntity(_entityType, 1, "system", "System", "Komea System"));
                break;
        }
        return entities;
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @param _entityType
     *            EntityType
     * @param _key
     *            Integer
     * @return TEntity
     */
    @Override
    public <TEntity extends IEntity> TEntity getEntity(final EntityKey _entityKey) {
    
    
        switch (_entityKey.getEntityType()) {
            case PERSON:
                return (TEntity) personDAO.selectByPrimaryKey(_entityKey.getId());
            case DEPARTMENT:
            case TEAM:
                return (TEntity) personGroupDao.selectByPrimaryKey(_entityKey.getId());
            case PROJECT:
                return (TEntity) projectDao.selectByPrimaryKey(_entityKey.getId());
            case SYSTEM:
                break;
            default:
                break;
        
        }
        return null;
        
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IEntityService#getEntityAssociatedToKpi(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public IEntity getEntityAssociatedToKpi(final KpiKey _kpiKey) {
    
    
        return getEntity(_kpiKey.getEntityKey());
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @param _entityType
     *            EntityType
     * @param _entityID
     *            Integer
     * @return IEntity
     * @see org.komea.product.backend.service.entities.IEntityService#getEntityOrFail(org.komea.product.database.enums.EntityType, int)
     */
    @Override
    public IEntity getEntityOrFail(final EntityKey _entityKey) {
    
    
        final IEntity entity = getEntity(_entityKey);
        if (entity == null) { throw new EntityNotFoundException(_entityKey.getId(),
                _entityKey.getEntityType()); }
        return entity;
    }
    
    
    /**
     * @return the personDAO
     */
    public PersonDao getPersonDAO() {
    
    
        return personDAO;
    }
    
    
    /**
     * @return the personGroupDao
     */
    public PersonGroupDao getPersonGroupDao() {
    
    
        return personGroupDao;
    }
    
    
    /**
     * @return the personGroupService
     */
    public IPersonGroupService getPersonGroupService() {
    
    
        return personGroupService;
    }
    
    
    /**
     * @return the personService
     */
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    /**
     * @return the projectDao
     */
    public ProjectDao getProjectDao() {
    
    
        return projectDao;
    }
    
    
    /**
     * @return the projectService
     */
    public IProjectService getProjectService() {
    
    
        return projectService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.entities.IEntityService#loadEntities(org.komea.product.database.enums.EntityType)
     */
    @Override
    public List<IEntity> loadEntities(final EntityType _entityType) {
    
    
        switch (_entityType) {
            case PERSON:
                return (List) personDAO.selectByCriteria(new PersonCriteria());
            case DEPARTMENT:
            case TEAM:
                return (List) personGroupDao.selectByCriteria(new PersonGroupCriteria());
            case PROJECT:
                return (List) projectDao.selectByCriteria(new ProjectCriteria());
            case SYSTEM:
            default:
                return Collections.EMPTY_LIST;
                
        }
    }
    
    
    /**
     * Method loadEntities.
     * 
     * @param _entityType
     *            EntityType
     * @param _keys
     *            List<Integer>
     * @return List<TEntity>
     * @see
     *      org.komea.product.backend.service.entities.IEntityService#loadEntities(EntityType, List<Integer>)
     */
    @Override
    public <TEntity extends IEntity> List<TEntity> loadEntities(
            final EntityType _entityType,
            final List<Integer> _keys) {
    
    
        final List<TEntity> listOfEntities = new ArrayList<TEntity>(_keys.size());
        for (final Integer key : _keys) {
            final IEntity entity = getEntity(EntityKey.of(_entityType, key));
            if (entity != null) {
                listOfEntities.add((TEntity) entity);
            }
        }
        return listOfEntities;
    }
    
    
    /**
     * @param _personDAO
     *            the personDAO to set
     */
    public void setPersonDAO(final PersonDao _personDAO) {
    
    
        personDAO = _personDAO;
    }
    
    
    /**
     * @param _personGroupDao
     *            the personGroupDao to set
     */
    public void setPersonGroupDao(final PersonGroupDao _personGroupDao) {
    
    
        personGroupDao = _personGroupDao;
    }
    
    
    /**
     * @param _personGroupService
     *            the personGroupService to set
     */
    public void setPersonGroupService(final IPersonGroupService _personGroupService) {
    
    
        personGroupService = _personGroupService;
    }
    
    
    /**
     * @param _personService
     *            the personService to set
     */
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
    
    /**
     * @param _projectDao
     *            the projectDao to set
     */
    public void setProjectDao(final ProjectDao _projectDao) {
    
    
        projectDao = _projectDao;
    }
    
    
    /**
     * @param _projectService
     *            the projectService to set
     */
    public void setProjectService(final IProjectService _projectService) {
    
    
        projectService = _projectService;
    }
    
}
