package org.komea.product.database.enums;

public enum ExtendedEntityType {

    PROJECT(EntityType.PROJECT, EntityType.PROJECT),
    PERSON(EntityType.PERSON, EntityType.PERSON),
    TEAM(EntityType.TEAM, EntityType.PERSON),
    DEPARTMENT(EntityType.DEPARTMENT, EntityType.PERSON),
    PROJECTS_PERSON(EntityType.PERSON, EntityType.PROJECT),
    PROJECTS_TEAM(EntityType.TEAM, EntityType.PROJECT),
    PROJECTS_DEPARTMENT(EntityType.DEPARTMENT, EntityType.PROJECT);
    private final EntityType kpiType;
    private final EntityType entityType;

    private ExtendedEntityType(EntityType entityType, EntityType kpiType) {
        this.entityType = entityType;
        this.kpiType = kpiType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public EntityType getKpiType() {
        return kpiType;
    }

    public boolean isForGroups() {
        return !PROJECT.equals(this) && !PERSON.equals(this);
    }
}
