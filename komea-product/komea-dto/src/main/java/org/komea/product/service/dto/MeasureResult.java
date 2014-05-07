
package org.komea.product.service.dto;


import java.util.Date;
import java.util.List;

import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;

import com.google.common.collect.Lists;

public class MeasureResult {
    
    //
    
    private final List<HistoricalValue> historicalValues;
    
    private ExtendedEntityType          entityType;
    
    private BaseEntityDto               entity;
    
    private Kpi                         kpi;
    
    public MeasureResult() {
    
        historicalValues = Lists.newArrayList();
    }
    
    public boolean addHistoricalValue(final HistoricalValue _e) {
    
        return historicalValues.add(_e);
    }
    
    public boolean addHistoricalValue(final Double _value, final Date _date) {
    
        return historicalValues.add(new HistoricalValue(_value, _date));
    }
    
    public BaseEntityDto getEntity() {
    
        return entity;
    }
    
    public ExtendedEntityType getEntityType() {
    
        return entityType;
    }
    
    public List<HistoricalValue> getHistoricalValues() {
    
        return historicalValues;
    }
    
    public Kpi getKpi() {
    
        return kpi;
    }
    
    public void setEntity(final BaseEntityDto _entity) {
    
        entity = _entity;
    }
    
    public void setEntityType(final ExtendedEntityType _entityType) {
    
        entityType = _entityType;
    }
    
    public void setKpi(final Kpi _kpi) {
    
        kpi = _kpi;
    }
    
}
