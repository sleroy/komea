package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KpiKey;

/**
 */
public interface IEntityService {

    /**
     * Method getEntities.
     *
     * @param entityType EntityType
     * @param entityKeys List<String>
     * @return List<BaseEntityDto>
     */
    List<BaseEntityDto> getBaseEntityDTOS(final EntityType entityType, final List<String> entityKeys);

    /**
     * Loads an entity from the database.
     *
     * @param _entityType the entity type
     * @param _key the key
     * @return the entity or null if the entity does not exist
     */
    <TEntity extends IEntity> TEntity findEntityByEntityKey(EntityKey _entityKey);

    /**
     * Method getEntityAssociatedToKpi.
     *
     * @param _kpiKey KpiKey
     * @return IEntity
     */
    IEntity findEntityAssociatedToKpi(KpiKey _kpiKey);

    /**
     * Returns the entity or fail
     *
     * @param _entityType the entity type
     * @param _entityID the entity ID
     * @return the entity.
     */
    IEntity getEntityOrFail(EntityKey _entityKey);

    /**
     * Load entities.
     *
     * @param _entityType the entity type.
     * @return the list of entities.
     */
    List<IEntity> getEntitiesByEntityType(EntityType _entityType);

    List<? extends IEntity> getSubEntities(Integer _entityId,
            ExtendedEntityType _extendedEntityType);

    /**
     * Loads a list of entities from the database.
     *
     * @param _entityType the entity type
     * @param _keys the keys
     * @return the entity
     */
    <TEntity extends IEntity> List<TEntity> getEntitiesByPrimaryKey(EntityType _entityType, List<Integer> _keys);

    // /**
    // * This method return the complete person list
    // *
    // * @return
    // */
    // List<PersonDto> getPersonList();
    //
    // /**
    // * This method return the complete department list
    // *
    // * @return the department list
    // */
    // List<DepartmentDto> getAllDepartments();
    //
    // /**
    // * This method return the complete project list
    // *
    // * @return the department list
    // */
    // List<ProjectDto> getAllProjects();
    //
    // /**
    // * This method return the complete team list
    // *
    // * @return the department list
    // */
    // List<TeamDto> getAllTeams();
}
