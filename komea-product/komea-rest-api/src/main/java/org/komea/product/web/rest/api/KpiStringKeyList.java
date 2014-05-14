
package org.komea.product.web.rest.api;


import java.util.Collection;
import java.util.Set;

import org.komea.product.service.dto.EntityStringKey;
import org.komea.product.service.dto.KpiStringKey;

import com.google.common.collect.Sets;

public class KpiStringKeyList {
    
    private final Set<String>          kpiKey;
    private final Set<EntityStringKey> entityKeys;
    
    public KpiStringKeyList() {
    
        kpiKey = Sets.newHashSet();
        entityKeys = Sets.newHashSet();
    }
    
    public KpiStringKeyList(final Set<String> _kpiKey, final Set<EntityStringKey> _entityKeys) {
    
        super();
        kpiKey = _kpiKey;
        entityKeys = _entityKeys;
    }
    
    public Set<String> getKpiKey() {
    
        return kpiKey;
    }
    
    public Set<EntityStringKey> getEntityKeys() {
    
        return entityKeys;
    }
    
    public boolean addEntityKey(final EntityStringKey _entityKey) {
    
        return entityKeys.add(_entityKey);
    }
    
    public boolean addEntityKeys(final Collection<? extends EntityStringKey> _entityKeys) {
    
        return entityKeys.addAll(_entityKeys);
    }
    
    public boolean addKpiKeys(final Collection<? extends String> _keys) {
    
        return kpiKey.addAll(_keys);
    }
    
    public void addKpiKey(final String _key) {
    
        kpiKey.add(_key);
    }
    
    public void addKpiStringKey(final KpiStringKey _kpieKey) {
    
        addKpiKey(_kpieKey.getKpiName());
        addEntityKey(_kpieKey.getEntityKey());
    }
    
    public void addKpiStringKeys(final Collection<KpiStringKey> _kpieKeys) {
    
        for (KpiStringKey kpiKey : _kpieKeys) {
            
            addKpiKey(kpiKey.getKpiName());
            addEntityKey(kpiKey.getEntityKey());
        }
    }
    
}
