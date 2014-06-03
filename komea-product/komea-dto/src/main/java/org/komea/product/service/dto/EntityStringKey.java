/**
 * 
 */

package org.komea.product.service.dto;


import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.enums.EntityType;

/**
 * This key defines an entity.
 * 
 * @author sleroy
 */
public class EntityStringKey implements Serializable, Comparable<EntityStringKey> {
    
    /**
     * This field describes
     */
    private static final long serialVersionUID = -3288694032746408088L;
    
    /**
     * Builds an entity key
     * 
     * @param _entityType
     *            the entity type
     * @return the key
     */
    public static EntityStringKey of(final EntityType _entityType) {
    
        return new EntityStringKey(_entityType, null);
    }
    
    /**
     * Builds an entity key
     * 
     * @param _entityType
     *            the entity type
     * @param _key
     *            the key
     * @return the key
     */
    public static EntityStringKey of(final EntityType _entityType, final String _key) {
    
        return new EntityStringKey(_entityType, _key);
    }
    
    @NotNull
    private EntityType entityType;
    
    @NotNull
    @Size(min = 0, max = 255)
    private String     key;
    
    /**
     * Entity Key.
     */
    public EntityStringKey() {
    
        super();
    }
    
    /**
     * This class builds an entity key.
     * 
     * @param _entityType
     *            entity type
     * @param _id
     *            entity id.
     */
    public EntityStringKey(final EntityType _entityType, final String _key) {
    
        super();
        entityType = _entityType;
        key = _key;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final EntityStringKey _o) {
    
        if (this == _o) {
            return 0;
        }
        final int res = key.compareTo(_o.getKey());
        if (res != 0) {
            return res;
        }
        return entityType.compareTo(_o.getEntityType());
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
        final EntityStringKey other = (EntityStringKey) obj;
        if (entityType != other.entityType) {
            return false;
        }
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
    
    public EntityType getEntityType() {
    
        return entityType;
    }
    
    public String getKey() {
    
        return key;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (entityType == null ? 0 : entityType.hashCode());
        result = prime * result + (key == null ? 0 : key.hashCode());
        return result;
    }
    
    /**
     * @return Tests if the key is a department key.
     */
    @JsonIgnore
    public boolean isDepartmentKey() {
    
        return EntityType.DEPARTMENT.equals(entityType);
    }
    
    /**
     * Tests if this key refers to an entity type only.
     * 
     * @return true if this key refers to entity type.
     */
    @JsonIgnore
    public boolean isEntityReferenceKey() {
    
        return entityType != null && key != null;
    }
    
    /**
     * Tests if this key refers to an entity type only.
     * 
     * @return true if this key refers to entity type.
     */
    @JsonIgnore
    public boolean isEntityTypeKey() {
    
        return entityType != null && key == null;
    }
    
    /**
     * @return Tests if the key is a team key.
     */
    @JsonIgnore
    public boolean isPersonKey() {
    
        return EntityType.PERSON.equals(entityType);
    }
    
    /**
     * @return Tests if the key is a project key.
     */
    @JsonIgnore
    public boolean isProjectKey() {
    
        return EntityType.PROJECT.equals(entityType);
    }
    
    /**
     * @return Tests if the key is a team key.
     */
    @JsonIgnore
    public boolean isTeamKey() {
    
        return EntityType.TEAM.equals(entityType);
    }
    
    /**
     * Tests if this key refers to an entity type only.
     * 
     * @return true if this key refers to entity type.
     */
    @JsonIgnore
    public boolean isUncompleteKey() {
    
        return !isEntityReferenceKey() && !isEntityTypeKey();
    }
    
    public void setEntityType(final EntityType _entityType) {
    
        entityType = _entityType;
    }
    
    public void setKey(final String _key) {
    
        key = _key;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
        return "EntityKey [entityType=" + entityType + ", id=" + key + "]";
    }
}
