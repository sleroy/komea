/**
 * 
 */

package org.komea.product.backend.service.entities;



import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.ExtendedEntityType;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
public class GetSubEntitiesAndConvertIntoDTO
{
    
    
    private final ExtendedEntityType  extendedEntityType;
    private final List<BaseEntityDto> parentEntities;
    private final IPersonService      personService;
    private final IProjectService     projectService;
    
    
    
    /**
     * @param _personService
     * @param _projectService
     */
    public GetSubEntitiesAndConvertIntoDTO(
            final ExtendedEntityType extendedEntityType,
            final List<BaseEntityDto> parentEntities,
            final IPersonService _personService,
            final IProjectService _projectService) {
    
    
        super();
        this.extendedEntityType = extendedEntityType;
        this.parentEntities = parentEntities;
        personService = _personService;
        projectService = _projectService;
    }
    
    
    public List<BaseEntityDto> getSubEntities() {
    
    
        final List<BaseEntityDto> entities;
        if (extendedEntityType.isForGroups()) {
            entities = Lists.newArrayList();
            final List<Integer> entityIds = Lists.newArrayList();
            for (final BaseEntityDto parentEntity : parentEntities) {
                final Integer entityId = parentEntity.getId();
                final GetSubEntities getSubEntities =
                        new GetSubEntities(entityId, extendedEntityType, personService,
                                projectService);
                final List<? extends IEntity> subEntities = getSubEntities.getSubEntities();
                if (subEntities != null && !subEntities.isEmpty()) {
                    final List<BaseEntityDto> subEntitiesDto =
                            BaseEntityDto.convertEntities(subEntities);
                    for (final BaseEntityDto subEntityDto : subEntitiesDto) {
                        final Integer subEntityId = subEntityDto.getId();
                        if (!entityIds.contains(subEntityId)) {
                            entityIds.add(subEntityId);
                            entities.add(subEntityDto);
                        }
                    }
                }
            }
        } else {
            entities = parentEntities;
        }
        return entities;
    }
    
}
