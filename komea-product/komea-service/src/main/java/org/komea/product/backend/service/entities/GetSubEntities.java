/**
 * 
 */

package org.komea.product.backend.service.entities;



import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.ExtendedEntityType;



/**
 * @author sleroy
 */
public class GetSubEntities
{
    
    
    private final Integer            entityId;
    private final ExtendedEntityType extendedEntityType;
    private final IPersonService     personService;
    private final IProjectService    projectService;
    
    
    
    /**
     * @param _personService
     * @param _projectService
     */
    public GetSubEntities(
            final Integer _entityId,
            final ExtendedEntityType _extendedEntityType,
            final IPersonService _personService,
            final IProjectService _projectService) {
    
    
        super();
        entityId = _entityId;
        extendedEntityType = _extendedEntityType;
        personService = _personService;
        projectService = _projectService;
    }
    
    
    /**
     * Returns the list of sub entities.
     * 
     * @return the list of sub entities.
     */
    public <T extends IEntity> List<T> getSubEntities() {
    
    
        List<T> entities = null;
        switch (extendedEntityType) {
            case PROJECTS_PERSON:
                entities = List.class.cast(projectService.getProjectsOfAMember(entityId));
                break;
            case PROJECTS_TEAM:
            case PROJECTS_DEPARTMENT:
                entities =
                        List.class.cast(projectService
                                .getProjectsOfPersonGroupRecursively(entityId));
                break;
            case MEMBERS_TEAM:
            case MEMBERS_DEPARTMENT:
                entities =
                        List.class.cast(personService.getPersonsOfPersonGroupRecursively(entityId));
                break;
        }
        return entities;
    }
    
    
}
