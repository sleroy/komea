
package org.komea.product.database.dto;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.service.dto.KpiKey;



/**
 * DTO To hold tendancy.
 * 
 * @author sleroy
 */
public class KpiTendancyDto implements Serializable
{
    
    
    private final KpiKey                     kpiKey;
    
    
    private final List<TendancyLineValueDto> lineValueDtos = new ArrayList<TendancyLineValueDto>();
    
    
    
    /**
     * @param _previousValue
     * @param _actualValue
     * @param _kpiKey
     */
    public KpiTendancyDto(final KpiKey _kpiKey) {
    
    
        super();
        
        kpiKey = _kpiKey;
    }
    
    
    /**
     * Add the tendancy
     * 
     * @param _entity
     *            the entity
     * @param _realValue
     *            the real value
     * @param _lastValue
     *            the last value.
     */
    public void addTendancy(final IEntity _entity, final Double _realValue, final Double _lastValue) {
    
    
        lineValueDtos.add(new TendancyLineValueDto(_entity, _realValue, _lastValue));
        
    }
    
    
    /**
     * Returns the kpi key.
     * 
     * @return the kpi key.
     */
    public KpiKey getKpiKey() {
    
    
        return kpiKey;
    }
    
    
    /**
     * Returns the entity
     * 
     * @param _entity
     *            the entity.
     * @return the last value.
     */
    public Double getLastValue(final IEntity _entity) {
    
    
        for (final TendancyLineValueDto valueDto : lineValueDtos) {
            if (_entity.equals(valueDto.getEntity())) { return valueDto.getPastValue(); }
        }
        
        return null;
    }
    
    
    /**
     * Returns the line values.
     * 
     * @return the line values.
     */
    public List<TendancyLineValueDto> getLineValueDtos() {
    
    
        return lineValueDtos;
    }
    
    
    /**
     * Returns the real value of an entity.
     * 
     * @param _entity
     *            the entity.
     * @return the real value.
     */
    public Double getRealValue(final IEntity _entity) {
    
    
        for (final TendancyLineValueDto valueDto : lineValueDtos) {
            if (_entity.getEntityKey().equals(valueDto.getEntity().getEntityKey())) { return valueDto
                    .getRealValue(); }
        }
        
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "KpiTendancyDto [kpiKey=" + kpiKey + ", lineValueDtos=" + lineValueDtos + "]";
    }
    
}
