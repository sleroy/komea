package org.komea.product.database.dto;

import java.util.ArrayList;
import java.util.List;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.service.dto.EntityKey;

public class BaseEntityDto implements IEntity, Comparable<BaseEntityDto> {

    private static final long serialVersionUID = 1L;

    /**
     * Converts a list of entities into a list of base entity dtos.
     *
     * @param _entityList the list of entities
     * @return the list of base entities;
     */
    public static List<BaseEntityDto> convertEntities(final List<? extends IEntity> _entityList) {

        final List<BaseEntityDto> baseEntityDtosList = new ArrayList<BaseEntityDto>();
        for (final IEntity entity : _entityList) {
            baseEntityDtosList.add(newFromEntity(entity));
        }
        return baseEntityDtosList;
    }

    /**
     * Creates a new dto from a member.
     *
     * @param _person the persn
     * @return the member
     */
    public static BaseEntityDto newFromMember(final Person _person) {

        return new BaseEntityDto(EntityType.PERSON, _person.getId(), _person.getLogin(),
                _person.getFullName(), _person.getEmail());
    }

    /**
     * @param _personGroup
     * @return
     */
    public static BaseEntityDto newFromPersonGroup(final PersonGroup _personGroup) {

        return new BaseEntityDto(_personGroup.getEntityKey().getEntityType(), _personGroup.getId(),
                _personGroup.getPersonGroupKey(), _personGroup.getName(),
                _personGroup.getDescription());

    }

    /**
     * Converts a project into a base entity DTO.
     *
     * @param _project the project
     * @return the base entity DTO
     */
    public static BaseEntityDto newFromProject(final Project _project) {

        return new BaseEntityDto(_project.getEntityKey().getEntityType(), _project.getId(),
                _project.getProjectKey(), _project.getName(), _project.getDescription());

    }

    /**
     * Converts an entity into a base entity dto.
     *
     * @param _entity the entity.
     * @return the entity dto.
     */
    private static BaseEntityDto newFromEntity(final IEntity _entity) {

        if (_entity == null) {
            throw new IllegalArgumentException("The validated object is null");
        }
        switch (_entity.getEntityKey().getEntityType()) {
            case PERSON:
                return newFromMember((Person) _entity);
            case DEPARTMENT:
            case TEAM:
                return newFromPersonGroup((PersonGroup) _entity);
            case PROJECT:
                return newFromProject((Project) _entity);
        }
        return null;
    }

    private String description = "";

    private EntityType entityType;

    private Integer id;

    private String key = "";

    private String name = "";

    public BaseEntityDto() {

        super();

    }

    public BaseEntityDto(
            final EntityType entityType,
            final Integer id,
            final String key,
            final String name,
            final String description) {

        this.entityType = entityType;
        this.id = id;
        this.key = key;
        this.name = name;
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IHasKey#accept(org.komea.product.database.api.IKeyVisitor)
     */
    @Override
    public void accept(final IKeyVisitor _visitor) {

        // this.
    }

    @Override
    public int compareTo(final BaseEntityDto o) {

        return getDisplayName().toLowerCase().compareTo(o.getDisplayName().toLowerCase());
    }

    @Override
    public EntityType entityType() {

        return getEntityType();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntityDto other = (BaseEntityDto) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public String getDescription() {

        return description;
    }

    @Override
    public String getDisplayName() {

        return name;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.api.IEntity#getEntityKey()
     */
    @Override
    public EntityKey getEntityKey() {

        return new EntityKey(getEntityType(), getId());
    }

    public EntityType getEntityType() {

        return entityType;
    }

    @Override
    public Integer getId() {

        return id;
    }

    @Override
    public String getKey() {

        return key;
    }

    public String getName() {

        return name;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }

    public void setDescription(final String description) {

        this.description = description;
    }

    public void setEntityType(final EntityType entityType) {

        this.entityType = entityType;
    }

    @Override
    public void setId(final Integer id) {

        this.id = id;
    }

    public void setKey(final String key) {

        this.key = key;
    }

    public void setName(final String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        return "BaseEntityDto{"
                + "entityType=" + entityType + ", id=" + id + ", key=" + key + ", name=" + name
                + ", description=" + description + '}';
    }
}
