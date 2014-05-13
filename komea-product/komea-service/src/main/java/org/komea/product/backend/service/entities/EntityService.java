
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
import org.komea.product.service.dto.EntityStringKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
@Transactional
public final class EntityService implements IEntityService {
    
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
     * super();
     * }
     * /**
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
    
    @Override
    public <TEntity extends IEntity> TEntity findEntityByEntityStringKey(final EntityStringKey _entityKey) {
    
        Validate.notNull(_entityKey);
        switch (_entityKey.getEntityType()) {
            case PERSON:
                return (TEntity) personService.selectByKey(_entityKey.getKey());
            case DEPARTMENT:
            case TEAM:
                return (TEntity) personGroupService.selectByKey(_entityKey.getKey());
            case PROJECT:
                return (TEntity) projectService.selectByKey(_entityKey.getKey());
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
    public List<BaseEntityDto> getBaseEntityDTOS(final EntityType _entityType, final List<String> _entityKeys) {
    
        Validate.notNull(_entityKeys);
        Validate.notNull(_entityType);
        final List<? extends IEntity> entitiesWithKeys;
        if (_entityKeys.isEmpty()) {
            // FIXME : The design there is Wrong!
            entitiesWithKeys = getEntitiesByEntityType(_entityType);
        } else {
            entitiesWithKeys = findEntitiesByTypeAndKeys(_entityType, _entityKeys);
        }
        return BaseEntityDto.convertEntities(entitiesWithKeys);
        
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.entities.IEntityService#loadEntities(org.komea.product.database.enums.EntityType)
     */
    @Override
    public <T extends IEntity> List<T> getEntitiesByEntityType(final EntityType _entityType) {
    
        Validate.notNull(_entityType);
        
        switch (_entityType) {
            case PERSON:
                return List.class.cast(personService.selectAll());
            case DEPARTMENT:
                return List.class.cast(personGroupService.getAllDepartmentsPG());
            case TEAM:
                return List.class.cast(personGroupService.getAllTeamsPG());
            case PROJECT:
                return List.class.cast(projectService.selectAll());
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
    public <TEntity extends IEntity> List<TEntity> getEntitiesByPrimaryKey(final EntityType _entityType, final List<Integer> _keys) {
    
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
        if (entity == null) {
            throw new EntityNotFoundException(_entityKey.getId(), _entityKey.getEntityType());
        }
        return entity;
    }
    
    @Override
    public IEntity getEntityOrFail(final EntityStringKey _entityKey) {
    
        Validate.notNull(_entityKey);
        final IEntity entity = findEntityByEntityStringKey(_entityKey);
        if (entity == null) {
            throw new EntityNotFoundException(_entityKey.getKey(), _entityKey.getEntityType());
        }
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
    public List<BaseEntityDto> getSubEntities(final ExtendedEntityType extendedEntityType, final List<BaseEntityDto> parentEntities) {
    
        final GetSubEntitiesAndConvertIntoDTO subEntities = new GetSubEntitiesAndConvertIntoDTO(extendedEntityType, parentEntities,
                personService, projectService);
        return subEntities.getSubEntities();
    }
    
    @Override
    public <T extends IEntity> List<T> getSubEntities(final Integer _entityId, final ExtendedEntityType _extendedEntityType) {
    
        final GetSubEntities getSubEntities = new GetSubEntities(_entityId, _extendedEntityType, personService, projectService);
        return getSubEntities.getSubEntities();
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
    private List<? extends IEntity> findEntitiesByTypeAndKeys(final EntityType _entityType, final List<String> _entityKeys) {
    
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
