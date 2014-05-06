
package org.komea.product.backend.service.kpi;


import java.util.Collection;
import java.util.List;

import org.komea.product.database.enums.ExtendedEntityType;

import com.google.common.collect.Lists;

public class HistoryStringKeyList {
    
    /**
     * the kpi key
     */
    private final List<String> KpiKeys;
    
    /**
     * the entity measure with the kpi
     */
    private final List<String> entityKeys;
    
    /**
     * the kpi type : PROJECT, GROUP....
     */
    private ExtendedEntityType entityType;
    
    public HistoryStringKeyList() {
    
        KpiKeys = Lists.newArrayList();
        entityKeys = Lists.newArrayList();
    }
    
    public HistoryStringKeyList(final ExtendedEntityType _entityType) {
    
        KpiKeys = Lists.newArrayList();
        entityKeys = Lists.newArrayList();
        entityType = _entityType;
    }
    
    public HistoryStringKeyList(final List<String> _kpiKeys, final List<String> _entityKeys, final ExtendedEntityType _entityType) {
    
        super();
        KpiKeys = _kpiKeys;
        entityKeys = _entityKeys;
        entityType = _entityType;
    }
    
    public boolean addAllKpiKeys(final Collection<String> _kpiKeys) {
    
        return KpiKeys.addAll(_kpiKeys);
    }
    
    public void addKpiKey(final String _kpiKey) {
    
        KpiKeys.add(_kpiKey);
    }
    
    public boolean addAllEntityKeys(final Collection<String> _entityKeys) {
    
        return entityKeys.addAll(_entityKeys);
    }
    
    public void addEntityKey(final String _entityKey) {
    
        entityKeys.add(_entityKey);
    }
    
    public List<String> getKpiKeys() {
    
        return KpiKeys;
    }
    
    public List<String> getEntityKeys() {
    
        return entityKeys;
    }
    
    public ExtendedEntityType getEntityType() {
    
        return entityType;
    }
    
    public void setEntityType(final ExtendedEntityType _entityType) {
    
        entityType = _entityType;
    }
    
}
