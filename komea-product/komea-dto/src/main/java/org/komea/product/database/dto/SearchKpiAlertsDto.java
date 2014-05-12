
package org.komea.product.database.dto;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.Severity;

import com.google.common.collect.Lists;

public class SearchKpiAlertsDto implements Serializable {
    
    private static final long  serialVersionUID = 1L;
    
    private ExtendedEntityType extendedEntityType;
    private List<String>       kpiAlertTypeKeys;
    private List<String>       entityKeys;
    private Severity           severityMin;
    private boolean            activatedOnly;
    
    public SearchKpiAlertsDto() {
    
        kpiAlertTypeKeys = Lists.newArrayList();
        entityKeys = Lists.newArrayList();
    }
    
    public SearchKpiAlertsDto(final ExtendedEntityType entityType, final List<String> kpiAlertTypeKeys, final List<String> entityKeys,
            final Severity severityMin, final boolean activatedOnly) {
    
        extendedEntityType = entityType;
        this.kpiAlertTypeKeys = kpiAlertTypeKeys;
        this.entityKeys = entityKeys;
        this.severityMin = severityMin;
        this.activatedOnly = activatedOnly;
    }
    
    public boolean addAlertKey(final String _alertKey) {
    
        return kpiAlertTypeKeys.add(_alertKey);
    }
    
    public boolean addAlertKeyList(final Collection<? extends String> _alertKeys) {
    
        return kpiAlertTypeKeys.addAll(_alertKeys);
    }
    
    public boolean addEntityKey(final String _entityKey) {
    
        return entityKeys.add(_entityKey);
    }
    
    public boolean addEntityKeyList(final Collection<? extends String> _entityKeys) {
    
        return entityKeys.addAll(_entityKeys);
    }
    
    public List<String> getEntityKeys() {
    
        return entityKeys;
    }
    
    public ExtendedEntityType getExtendedEntityType() {
    
        return extendedEntityType;
    }
    
    public List<String> getKpiAlertTypeKeys() {
    
        return kpiAlertTypeKeys;
    }
    
    public Severity getSeverityMin() {
    
        return severityMin;
    }
    
    public boolean isActivatedOnly() {
    
        return activatedOnly;
    }
    
    public void setActivatedOnly(final boolean activatedOnly) {
    
        this.activatedOnly = activatedOnly;
    }
    
    public void setEntityKeys(final List<String> entityKeys) {
    
        this.entityKeys = entityKeys;
    }
    
    public void setExtendedEntityType(final ExtendedEntityType entityType) {
    
        extendedEntityType = entityType;
    }
    
    public void setKpiAlertTypeKeys(final List<String> kpiAlertTypeKeys) {
    
        this.kpiAlertTypeKeys = kpiAlertTypeKeys;
    }
    
    public void setSeverityMin(final Severity severityMin) {
    
        this.severityMin = severityMin;
    }
    
    @Override
    public String toString() {
    
        return "SearchKpiAlertsDto{" + "extendedEntityType=" + extendedEntityType + ", kpiAlertTypeKeys=" + kpiAlertTypeKeys
                + ", entityKeys=" + entityKeys + ", severityMin=" + severityMin + ", activatedOnly=" + activatedOnly + '}';
    }
    
}
