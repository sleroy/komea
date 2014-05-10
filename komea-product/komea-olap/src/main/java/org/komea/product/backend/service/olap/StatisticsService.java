/**
 * 
 */

package org.komea.product.backend.service.olap;



import org.apache.commons.lang3.Validate;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.backend.criterias.FindKpiOrFail;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions;
import org.komea.product.database.dao.timeserie.TimeSerieOptions;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
@Service
@Transactional
public class StatisticsService implements IStatisticsAPI
{
    
    
    @Autowired
    private KpiDao           kpiDAO;
    @Autowired
    private IKpiQueryService kpiQueryRegisterService;
    
    @Autowired
    private MeasureDao       measureDao;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#buildGlobalPeriodTimeSeries(org.komea.product.database.dao.timeserie.
     * PeriodTimeSerieOptions)
     */
    @Override
    public TimeSerie buildGlobalPeriodTimeSeries(final PeriodTimeSerieOptions _timeSerieOptions) {
    
    
        return new TimeSerieImpl(measureDao.buildGlobalPeriodTimeSeries(_timeSerieOptions));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#buildPeriodTimeSeries(org.komea.product.database.dao.timeserie.
     * PeriodTimeSerieOptions, org.komea.product.service.dto.EntityKey)
     */
    @Override
    public TimeSerie buildPeriodTimeSeries(
            final PeriodTimeSerieOptions _timeSerieOptions,
            final EntityKey _entityKey) {
    
    
        return new TimeSerieImpl(measureDao.buildPeriodTimeSeries(_timeSerieOptions, _entityKey));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#buildTimeSeries(org.komea.product.database.dao.timeserie.TimeSerieOptions,
     * org.komea.product.service.dto.EntityKey)
     */
    @Override
    public TimeSerie buildTimeSeries(
            final TimeSerieOptions _timeSerieOptions,
            final EntityKey _entityKey) {
    
    
        return new TimeSerieImpl(measureDao.buildTimeSeries(_timeSerieOptions, _entityKey));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiValues(org.komea.product.database.dao.timeserie.TimeSerieOptions,
     * org.komea.product.service.dto.EntityKey)
     */
    @Override
    public Double evaluateKpiValue(final TimeSerieOptions _options, final EntityKey _entityKey) {
    
    
        return measureDao.getKpiValue(_options, _entityKey);
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiOnPeriodValues(org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions
     * , org.komea.product.service.dto.EntityKey)
     */
    @Override
    public Double evaluateKpiValueOnPeriod(
            final PeriodTimeSerieOptions _options,
            final EntityKey _entityKey) {
    
    
        return measureDao.getKpiValueOnPeriod(_options, _entityKey);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiValues(org.komea.product.database.dao.timeserie.TimeSerieOptions)
     */
    @Override
    public KpiResult getKpiValues(final TimeSerieOptions _options) {
    
    
        return new KpiResult(measureDao.getKpiValues(_options));
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiOnPeriodValues(org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions
     * )
     */
    @Override
    public KpiResult getKpiValuesOnPeriod(final PeriodTimeSerieOptions _options) {
    
    
        return new KpiResult(measureDao.getKpiValuesOnPeriod(_options));
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#getTheCurrentKpiValue(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public Double getTheCurrentKpiValue(final KpiKey _kpiKeys) {
    
    
        Validate.isTrue(_kpiKeys.getEntityKey().isEntityReferenceKey());
        return getTheCurrentKpiValues(_kpiKeys.getKpiName())
                .getDoubleValue(_kpiKeys.getEntityKey());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#getTheCurrentKpiValues(java.lang.String)
     */
    @Override
    public KpiResult getTheCurrentKpiValues(final String _kpiName) {
    
    
        final Kpi findKpiOrFail = new FindKpiOrFail(_kpiName, kpiDAO).find();
        return kpiQueryRegisterService.getQueryValueFromKpi(findKpiOrFail);
    }
    
    
}
