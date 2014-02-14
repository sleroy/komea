package org.komea.product.database.dto;

import java.io.Serializable;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;

public class BaseEntity implements IEntity, Serializable {

    private static final long serialVersionUID = 1L;

    private EntityType entityType;
    private Integer id;
    private String key;
    private String name;
    private String description;

    public BaseEntity() {
    }

    public BaseEntity(EntityType entityType, Integer id, String key, String name, String description) {
        this.entityType = entityType;
        this.id = id;
        this.key = key;
        this.name = name;
        this.description = description;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public EntityType entityType() {
        return getEntityType();
    }

    @Override
    public String toString() {
        return "BaseEntity{" + "entityType=" + entityType + ", id=" + id
                + ", key=" + key + ", name=" + name + ", description=" + description + '}';
    }

}
