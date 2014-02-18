package org.komea.product.backend.service.entities;


import java.util.List;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.KpiKey;



public interface IEntityService
{
    

    /**
     * Loads an entity from the database.
     *
     * @param _entityType the entity type
     * @param _key the key
     * @return the entity or null if the entity does not exist
     */
    <TEntity extends IEntity> TEntity getEntity(EntityType _entityType, Integer _key);
    

    IEntity getEntityAssociatedToKpi(KpiKey _kpiKey);

    
    /**
     * Returns the entity or fail
     *
     * @param _entityType the entity type
     * @param _entityID the entity ID
     * @return the entity.
     */
    IEntity getEntityOrFail(EntityType _entityType, Integer _entityID);
    

    /**
     * Loads a list of entities from the database.
     *
     * @param _entityType the entity type
     * @param _keys the keys
     * @return the entity
     */
    <TEntity extends IEntity> List<TEntity> loadEntities(EntityType _entityType, List<Integer> _keys);

    List<BaseEntity> getEntities(final EntityType entityType, final List<String> entityKeys);

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
