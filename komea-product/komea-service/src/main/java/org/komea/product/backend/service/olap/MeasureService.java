package org.komea.product.backend.service.olap;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeSerieConvertor;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.PeriodCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasureService implements IMeasureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasureService.class.getName());

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IKPIService kpiService;

    @Autowired
    private IStatisticsAPI statService;

    @Override
    public Double currentMeasure(final Kpi kpi, final IEntity entity) {

        Validate.notNull(kpi);
        Validate.notNull(entity);
        final HistoryKey historyKey = HistoryKey.of(kpi, entity);
        return statService.evaluateTheCurrentKpiValue(historyKey);
    }

    @Override
    public Double currentMeasure(final KpiStringKey _kpiKey) {

        final Kpi kpi = kpiService.selectByKey(_kpiKey.getKpiName());
        if (kpi == null) {
            throw new KPINotFoundRuntimeException(_kpiKey.getKpiName());
        }
        final IEntity entity = entityService.findEntityByEntityStringKey(_kpiKey.getEntityKey());
        if (entity == null) {
            throw new EntityNotFoundException(_kpiKey.getEntityKey());
        }
        return currentMeasure(kpi, entity);
    }

    @Override
    public TimeSerieDTO findHistoricalMeasure(final KpiStringKey _kpiKey, final PeriodTimeSerieOptions _period) {

        Validate.notNull(_kpiKey);
        Validate.notNull(_kpiKey.getKpiName());
        Validate.notNull(_kpiKey.getEntityKey());
        Validate.notNull(_kpiKey.getEntityKey().getKey());
        Validate.notNull(_kpiKey.getEntityKey().getEntityType());
        Validate.notNull(_period);

        final Kpi kpi = kpiService.selectByKey(_kpiKey.getKpiName());
        if (kpi == null) {
            throw new KPINotFoundRuntimeException(_kpiKey.getKpiName());
        }
        _period.setKpiID(kpi.getId());
        final IEntity entity = entityService.findEntityByEntityStringKey(_kpiKey.getEntityKey());
        if (entity == null) {
            throw new EntityNotFoundException(_kpiKey.getEntityKey());
        }
        return findHistoricalMeasure(kpi, entity, _period);

    }

    @Override
    public List<TimeSerieDTO> findMultipleHistoricalMeasure(final KpiStringKeyList _kpiKeyList, final PeriodCriteria _period) {

        Validate.notNull(_kpiKeyList);
        Validate.notNull(_kpiKeyList.getEntityKeys());
        Validate.notNull(_kpiKeyList.getKpiKeys());
        Validate.notNull(_kpiKeyList.getEntityType());
        Validate.notNull(_period);

        final List<TimeSerieDTO> series = Lists.newArrayList();
        final PeriodTimeSerieOptions options = new PeriodTimeSerieOptions();
        options.setFromPeriod(new DateTime(_period.getStartDate()));
        options.setToPeriod(new DateTime(_period.getEndDate()));
        options.pickBestGranularity();
        final List<String> kpiKeys = new ArrayList<String>(_kpiKeyList.getKpiKeys());
        final List<String> entityKeys = new ArrayList<String>(_kpiKeyList.getEntityKeys());
        final EntityType entityType = _kpiKeyList.getEntityType();
        final Collection<Kpi> kpis = kpiKeys.isEmpty()
                ? kpiService.getAllKpisOfEntityType(entityType)
                : kpiService.selectByKeys(kpiKeys);
        final Collection<IEntity> entities = entityKeys.isEmpty()
                ? entityService.getEntitiesByEntityType(entityType)
                : entityService.getEntitiesByKey(entityType, entityKeys);
        for (final IEntity entity : entities) {
            for (final Kpi kpi : kpis) {
                options.setKpi(kpi);
                final TimeSerieDTO timeSerieDTO = findHistoricalMeasure(kpi, entity, options);
                series.add(timeSerieDTO);
            }
        }
        return series;
    }

    private TimeSerieDTO findHistoricalMeasure(final Kpi _kpi, final IEntity _entity, final PeriodTimeSerieOptions _period) {

        if (_period.getKpiID() == null) {
            _period.setKpiID(_kpi.getId());
        }
        if (_period.getGroupFormula() == null) {
            _period.setGroupFormula(_kpi.getGroupFormula());
        }

        final EntityKey entityKey = EntityKey.of(_entity);

        final TimeSerie timeSeries = statService.buildPeriodTimeSeries(_period, entityKey);
        return TimeSerieConvertor.build(timeSeries.getCoordinates(), _kpi, BaseEntityDto.newFromEntity(_entity));

    }
}
