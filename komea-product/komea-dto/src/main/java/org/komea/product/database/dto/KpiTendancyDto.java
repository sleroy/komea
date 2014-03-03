
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
     * @param _previousValue
     *            the last value.
     */
    public void addTendancy(
            final IEntity _entity,
            final Double _realValue,
            final Double _previousValue) {
    
    
        lineValueDtos.add(new TendancyLineValueDto(_entity, _realValue, _previousValue));
        
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
     * Returns the line values.
     * 
     * @return the line values.
     */
    public List<TendancyLineValueDto> getLineValueDtos() {
    
    
        return lineValueDtos;
    }
    
    
    /**
     * Returns the entity
     * 
     * @param _entity
     *            the entity.
     * @return the last value.
     */
    public Double getPreviousValue(final IEntity _entity) {
    
    
        for (final TendancyLineValueDto valueDto : lineValueDtos) {
            if (_entity.getEntityKey().equals(valueDto.getEntity().getEntityKey())) { return valueDto
                    .getPastValue(); }
        }
        
        return null;
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
