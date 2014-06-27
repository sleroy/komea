/**
 *
 */

package org.komea.product.model.timeserie;



import java.util.List;

import org.komea.product.service.dto.EntityKey;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
public class TimeSerieImpl implements TimeSerie
{


    private final List<TimeCoordinate> buildGlobalPeriodTimeSeries;
    private EntityKey                  entityKey = new EntityKey();
    private String                     kpiName;
    
    
    
    public TimeSerieImpl() {


        super();
        buildGlobalPeriodTimeSeries = Lists.newArrayList();
    }


    /**
     * @param _buildGlobalPeriodTimeSeries
     */
    public TimeSerieImpl(final List<TimeCoordinate> _buildGlobalPeriodTimeSeries) {


        super();

        buildGlobalPeriodTimeSeries = _buildGlobalPeriodTimeSeries;

    }


    /**
     * @param _buildPeriodTimeSeries
     * @param _entityKey
     */
    public TimeSerieImpl(
            final List<TimeCoordinate> _buildPeriodTimeSeries,
            final EntityKey _entityKey,
            final String _kpiName) {


        buildGlobalPeriodTimeSeries = _buildPeriodTimeSeries;
        entityKey = _entityKey;
        kpiName = _kpiName;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.TimeSerie#getCoordinates()
     */
    @Override
    public List<TimeCoordinate> getCoordinates() {


        return buildGlobalPeriodTimeSeries;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.TimeSerie#getEntityKey()
     */
    @Override
    public EntityKey getEntityKey() {


        return entityKey;
    }


    public String getKpiName() {


        return kpiName;
    }
    
    
    public void setKpiName(final String _kpiName) {


        kpiName = _kpiName;
    }
    
    
    @Override
    public String toString() {


        return "TimeSerieImpl{"
                + "buildGlobalPeriodTimeSeries=" + buildGlobalPeriodTimeSeries + ", entityKey="
                + entityKey + '}';
    }

}
