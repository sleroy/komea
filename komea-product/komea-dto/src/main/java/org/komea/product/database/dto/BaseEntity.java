
package org.komea.product.database.dto;



import java.io.Serializable;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.api.IKeyVisitor;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;



public class BaseEntity implements IEntity, Serializable
{
    
    
    private static final long serialVersionUID = 1L;
    
    private String            description;
    private EntityType        entityType;
    private Integer           id;
    private String            key;
    private String            name;
    
    
    
    public BaseEntity() {
    
    
    }
    
    
    public BaseEntity(
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
    public EntityType entityType() {
    
    
        return getEntityType();
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof BaseEntity)) { return false; }
        final BaseEntity other = (BaseEntity) obj;
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        return true;
    }
    
    
    public String getDescription() {
    
    
        return description;
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
    
    
        return "BaseEntity{"
                + "entityType=" + entityType + ", id=" + id + ", key=" + key + ", name=" + name
                + ", description=" + description + '}';
    }
}
