/**
 * 
 */

package org.komea.product.backend.service.olap;



import org.apache.commons.lang3.Validate;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.backend.criterias.FindKpiOrFail;
import org.komea.product.backend.criterias.FindKpiPerId;
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

import com.google.common.base.Strings;



/**
 * @author sleroy
 */
@Service
@Transactional
public class StatisticsService implements IStatisticsAPI
{
    
    
    @Autowired
    private KpiDao           kpiDao;
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
    
    
        Validate.notNull(_timeSerieOptions);
        
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
    
    
        Validate.notNull(_timeSerieOptions);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        return new TimeSerieImpl(measureDao.buildPeriodTimeSeries(_timeSerieOptions, _entityKey),
                _entityKey);
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
    
    
        Validate.notNull(_timeSerieOptions);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        
        return new TimeSerieImpl(measureDao.buildTimeSeries(_timeSerieOptions, _entityKey),
                _entityKey);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiValues(org.komea.product.database.dao.timeserie.TimeSerieOptions,
     * org.komea.product.service.dto.EntityKey)
     */
    @Override
    public Double evaluateKpiValue(final TimeSerieOptions _options, final EntityKey _entityKey) {
    
    
        Validate.notNull(_options);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        
        return measureDao.evaluateKpiValue(_options, _entityKey);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiValues(org.komea.product.database.dao.timeserie.TimeSerieOptions)
     */
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
    
    
        Validate.notNull(_options);
        Validate.notNull(_entityKey);
        Validate.isTrue(_entityKey.isEntityReferenceKey());
        return measureDao.evaluateKpiValueOnPeriod(_options, _entityKey);
    }
    
    
    @Override
    public KpiResult evaluateKpiValues(final TimeSerieOptions _options) {
    
    
        Validate.notNull(_options);
        
        final Kpi findKpiPerId = new FindKpiPerId(_options.getKpiID(), kpiDao).find();
        return new KpiResult().fill(measureDao.evaluateKpiValues(_options),
                findKpiPerId.getEntityType());
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IStatisticsAPI#getKpiOnPeriodValues(org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions
     * )
     */
    @Override
    public KpiResult evaluateKpiValuesOnPeriod(final PeriodTimeSerieOptions _options) {
    
    
        Validate.notNull(_options);
        final Kpi findKpiPerId = new FindKpiPerId(_options.getKpiID(), kpiDao).find();
        return new KpiResult().fill(measureDao.evaluateKpiValuesOnPeriod(_options),
                findKpiPerId.getEntityType());
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#getTheCurrentKpiValue(org.komea.product.service.dto.KpiKey)
     */
    @Override
    public Double evaluateTheCurrentKpiValue(final KpiKey _kpiKeys) {
    
    
        Validate.isTrue(_kpiKeys.getEntityKey().isEntityReferenceKey());
        return evaluateTheCurrentKpiValues(_kpiKeys.getKpiName()).getDoubleValue(
                _kpiKeys.getEntityKey());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IStatisticsAPI#getTheCurrentKpiValues(java.lang.String)
     */
    @Override
    public KpiResult evaluateTheCurrentKpiValues(final String _kpiName) {
    
    
        Validate.isTrue(!Strings.isNullOrEmpty(_kpiName));
        
        final Kpi findKpiOrFail = new FindKpiOrFail(_kpiName, kpiDAO).find();
        return kpiQueryRegisterService.getQueryValueFromKpi(findKpiOrFail);
    }
    
    
    public KpiDao getKpiDao() {
    
    
        return kpiDao;
    }
    
    
    public void setKpiDao(final KpiDao _kpiDao) {
    
    
        kpiDao = _kpiDao;
    }
    
    
}
