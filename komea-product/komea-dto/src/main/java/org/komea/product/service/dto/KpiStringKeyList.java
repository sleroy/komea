
package org.komea.product.service.dto;


import java.util.Collection;
import java.util.Set;

import org.komea.product.database.enums.EntityType;

import com.google.common.collect.Sets;

public class KpiStringKeyList {
    
    private final Set<String> kpiKey;
    private final Set<String> entityKeys;
    private final EntityType  entityType;
    
    public KpiStringKeyList() {
    
        kpiKey = Sets.newHashSet();
        entityKeys = Sets.newHashSet();
        entityType = EntityType.PROJECT;
    }
    
    public KpiStringKeyList(final Set<String> _kpiKey, final Set<String> _entityKeys, final EntityType _entityType) {
    
        super();
        kpiKey = _kpiKey;
        entityKeys = _entityKeys;
        entityType = _entityType;
    }
    
    public Set<String> getKpiKey() {
    
        return kpiKey;
    }
    
    public Set<String> getEntityKeys() {
    
        return entityKeys;
    }
    
    public boolean addEntityKey(final String _entityKey) {
    
        return entityKeys.add(_entityKey);
    }
    
    public boolean addEntityKeys(final Collection<? extends String> _entityKeys) {
    
        return entityKeys.addAll(_entityKeys);
    }
    
    public boolean addKpiKeys(final Collection<? extends String> _keys) {
    
        return kpiKey.addAll(_keys);
    }
    
    public void addKpiKey(final String _key) {
    
        kpiKey.add(_key);
    }
    
}
