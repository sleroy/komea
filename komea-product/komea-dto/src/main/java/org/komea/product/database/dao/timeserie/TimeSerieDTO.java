/**
 *
 */

package org.komea.product.database.dao.timeserie;


import java.util.List;

import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiStringKey;

import com.google.common.collect.Lists;

/**
 * contain historical values for a kpi on an entity
 * 
 * @author jguidoux
 */
public class TimeSerieDTO {
    
    private final List<TimeCoordinateDTO> coordinates;
    private Kpi                           kpi;
    private BaseEntityDto                 entity;
    
    public TimeSerieDTO() {
    
        coordinates = Lists.newArrayList();
    }
    
    public TimeSerieDTO(final List<TimeCoordinateDTO> _buildGlobalPeriodTimeSeries) {
    
        super();
        
        coordinates = _buildGlobalPeriodTimeSeries;
        
    }
    
    public TimeSerieDTO(final List<TimeCoordinateDTO> _buildPeriodTimeSeries, final KpiStringKey _kpiKey) {
    
        coordinates = _buildPeriodTimeSeries;
    }
    
    public List<TimeCoordinateDTO> getCoordinates() {
    
        return coordinates;
    }
    
    public BaseEntityDto getEntity() {
    
        return entity;
    }
    
    public Kpi getKpi() {
    
        return kpi;
    }
    
    public void setEntity(final BaseEntityDto _entity) {
    
        entity = _entity;
    }
    
    public void setKpi(final Kpi _kpi) {
    
        kpi = _kpi;
    }
    
}
