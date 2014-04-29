
package org.komea.product.backend.service.entities;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
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
    private IPersonGroupService personGroupService;
    
    @Autowired
    private IPersonService      personService;
    
    @Autowired
    private IProjectService     projectService;
    
    
    
    public EntityService() {
    
    
        super();
    }
    
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.backend.service.entities.IEntityService#findEntityAssociatedToKpi(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public IEntity findEntityAssociatedToKpi(final KpiKey _kpiKey) {
    
    
        Validate.notNull(_kpiKey);
        
        return findEntityByEntityKey(_kpiKey.getEntityKey());
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
    public <TEntity extends IEntity> TEntity findEntityByEntityKey(final EntityKey _entityKey) {
    
    
        Validate.notNull(_entityKey);
        switch (_entityKey.getEntityType()) {
            case PERSON:
                return (TEntity) personService.selectByPrimaryKey(_entityKey.getId());
            case DEPARTMENT:
            case TEAM:
                return (TEntity) personGroupService.selectByPrimaryKey(_entityKey.getId());
            case PROJECT:
                return (TEntity) projectService.selectByPrimaryKey(_entityKey.getId());
            default:
                break;
        
        }
        return null;
        
    }
    
    
    /**
     * Method getEntities.
     * 
     * @param _entityType
     *            EntityType
     * @param _entityKeys
     *            List<String>
     * @return List<BaseEntityDto>
     */
    @Override
    public List<BaseEntityDto> getBaseEntityDTOS(
            final EntityType _entityType,
            final List<String> _entityKeys) {
    
    
        Validate.notNull(_entityKeys);
        Validate.notNull(_entityType);
        final List<? extends IEntity> entitiesWithKeys;
        entitiesWithKeys = findEntitiesByTypeAndKeys(_entityType, _entityKeys);
        return BaseEntityDto.convertEntities(entitiesWithKeys);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.entities.IEntityService#loadEntities(org.komea.product.database.enums.EntityType)
     */
    @Override
    public List<? extends IEntity> getEntitiesByEntityType(final EntityType _entityType) {
    
    
        Validate.notNull(_entityType);
        
        switch (_entityType) {
            case PERSON:
                return personService.selectAll();
            case DEPARTMENT:
                return personGroupService.getAllDepartmentsPG();
            case TEAM:
                return personGroupService.getAllTeamsPG();
            case PROJECT:
                return projectService.selectAll();
        }
        return Collections.emptyList();
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
     *      org.komea.product.backend.service.entities.IEntityService#loadEntities(EntityType,
     *      List<Integer>)
     */
    @Override
    public <TEntity extends IEntity> List<TEntity> getEntitiesByPrimaryKey(
            final EntityType _entityType,
            final List<Integer> _keys) {
    
    
        Validate.notNull(_entityType);
        Validate.notNull(_keys);
        
        final List<TEntity> listOfEntities = new ArrayList<TEntity>(_keys.size());
        for (final Integer key : _keys) {
            final IEntity entity = findEntityByEntityKey(EntityKey.of(_entityType, key));
            if (entity != null) {
                listOfEntities.add((TEntity) entity);
            }
        }
        return Collections.unmodifiableList(listOfEntities);
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
    
    
        Validate.notNull(_entityKey);
        final IEntity entity = findEntityByEntityKey(_entityKey);
        if (entity == null) { throw new EntityNotFoundException(_entityKey.getId(),
                _entityKey.getEntityType()); }
        return entity;
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
     * @return the projectService
     */
    public IProjectService getProjectService() {
    
    
        return projectService;
    }
    
    
    @Override
    public List<? extends IEntity> getSubEntities(
            final Integer _entityId,
            final ExtendedEntityType _extendedEntityType) {
    
    
        List<? extends IEntity> entities = null;
        switch (_extendedEntityType) {
            case PROJECTS_PERSON:
                entities = projectService.getProjectsOfAMember(_entityId);
                break;
            case PROJECTS_TEAM:
            case PROJECTS_DEPARTMENT:
                entities = projectService.getProjectsOfPersonGroupRecursively(_entityId);
                break;
            case TEAM:
            case DEPARTMENT:
                entities = personService.getPersonsOfPersonGroupRecursively(_entityId);
                break;
        }
        return entities;
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
     * @param _projectService
     *            the projectService to set
     */
    public void setProjectService(final IProjectService _projectService) {
    
    
        projectService = _projectService;
    }
    
    
    /**
     * Returns the list of entities
     * 
     * @param _entityType
     *            the entity type
     * @param _entityKeys
     *            the entity keys
     * @return the list of entities filtered by entity type and keys.
     */
    private List<? extends IEntity> findEntitiesByTypeAndKeys(
            final EntityType _entityType,
            final List<String> _entityKeys) {
    
    
        Validate.notNull(_entityKeys);
        Validate.notNull(_entityType);
        
        switch (_entityType) {
            case PERSON:
                return personService.selectByKeys(_entityKeys);
            case TEAM:
            case DEPARTMENT:
                return personGroupService.selectByKeys(_entityKeys);
            case PROJECT:
                return projectService.selectByKeys(_entityKeys);
            default:
                return Collections.EMPTY_LIST;
        }
    }
}
