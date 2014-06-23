package org.komea.product.database.enums;

public enum ExtendedEntityType {

    PROJECT,
    PERSON,
    MEMBERS_PROJECT,
    MEMBERS_TEAM,
    MEMBERS_DEPARTMENT,
    PROJECTS_PERSON,
    PROJECTS_TEAM,
    PROJECTS_DEPARTMENT;

    public EntityType getEntityType() {
        if (isForGroups()) {
            return EntityType.valueOf(name().split("_")[1]);
        }
        return EntityType.valueOf(name());
    }

    public EntityType getKpiType() {
        return name().startsWith("PROJECT") ? EntityType.PROJECT : EntityType.PERSON;
    }

    public boolean isForGroups() {
        return name().contains("_");
    }
}
