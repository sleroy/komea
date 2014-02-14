package org.komea.product.database.enums;

public enum EntityTypeExtended {

    PROJECT(EntityType.PROJECT), PERSON(EntityType.PERSON),
    TEAM(EntityType.PERSON_GROUP), DEPARTMENT(EntityType.PERSON_GROUP);

    private EntityTypeExtended(EntityType entityType) {
        this.entityType = entityType;
    }

    private final EntityType entityType;

    public EntityType getEntityType() {
        return entityType;
    }

}
