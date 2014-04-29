package org.komea.product.database.enums;

public enum ExtendedEntityType {

    PROJECT,
    PERSON,
    TEAM,
    DEPARTMENT,
    PROJECTS_PERSON,
    PROJECTS_TEAM,
    PROJECTS_DEPARTMENT;

    public EntityType getEntityType() {
        if (name().contains("_")) {
            return EntityType.valueOf(name().split("_")[1]);
        }
        return EntityType.valueOf(name());
    }

    public EntityType getKpiType() {
        return name().contains("PROJECT") ? EntityType.PROJECT : EntityType.PERSON;
    }

    public boolean isForGroups() {
        return !PROJECT.equals(this) && !PERSON.equals(this);
    }
}
