package org.komea.product.service.dto;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import org.komea.product.database.enums.EntityType;

public class KpiStringKeyList {

    private Set<String> kpiKeys;
    private Set<String> entityKeys;
    private EntityType entityType;

    public KpiStringKeyList() {

        kpiKeys = Sets.newHashSet();
        entityKeys = Sets.newHashSet();
        entityType = EntityType.PROJECT;
    }

    public void setKpiKeys(final Set<String> _kpiKeys) {

        kpiKeys = _kpiKeys;
    }

    public void setEntityKeys(final Set<String> _entityKeys) {

        entityKeys = _entityKeys;
    }

    public void setEntityType(final EntityType _entityType) {

        entityType = _entityType;
    }

    public KpiStringKeyList(final Set<String> _kpiKey, final Set<String> _entityKeys, final EntityType _entityType) {

        super();
        kpiKeys = _kpiKey;
        entityKeys = _entityKeys;
        entityType = _entityType;
    }

    public boolean addEntityKey(final String _entityKey) {

        return entityKeys.add(_entityKey);
    }

    public boolean addEntityKeys(final Collection<? extends String> _entityKeys) {

        return entityKeys.addAll(_entityKeys);
    }

    public void addKpiKey(final String _key) {

        kpiKeys.add(_key);
    }

    public boolean addKpiKeys(final Collection<? extends String> _keys) {

        return kpiKeys.addAll(_keys);
    }

    public Set<String> getEntityKeys() {

        return entityKeys;
    }

    public EntityType getEntityType() {

        return entityType;
    }

    public Set<String> getKpiKeys() {

        return kpiKeys;
    }

    @Override
    public String toString() {
        return "KpiStringKeyList{" + "kpiKeys=" + kpiKeys + ", entityKeys=" + entityKeys + ", entityType=" + entityType + '}';
    }

}
