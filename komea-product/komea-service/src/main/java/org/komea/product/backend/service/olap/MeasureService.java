
package org.komea.product.backend.service.olap;



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
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeCoordinate;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerie;
import org.komea.product.model.timeserie.TimeSerieConvertor;
import org.komea.product.model.timeserie.TimeSerieImpl;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.model.timeserie.table.dto.PointDTO;
import org.komea.product.model.timeserie.table.dto.TimeSerieOptionsDTO;
import org.komea.product.model.timeserie.table.dto.TimeSerieSimpleDTO;
import org.komea.product.model.timeserie.table.dto.TimeSerieTableDTO;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.EntityStringKey;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.PeriodCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;



@Service
public class MeasureService implements IMeasureService
{


    private static final Logger LOGGER = LoggerFactory.getLogger(MeasureService.class.getName());

    @Autowired
    private IEntityService      entityService;

    @Autowired
    private IKPIService         kpiService;

    @Autowired
    private IStatisticsAPI      statService;



    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IMeasureService#buildTimeLineForEntity(java.lang.String, java.lang.String,
     * org.komea.product.model.timeserie.table.dto.TimeSerieOptionsDTO)
     */
    @Override
    public TimeSerieTableDTO buildTimeLineForEntity(
            final String _entityType,
            final String _entityKey,
            final TimeSerieOptionsDTO _timeSerieOptionsDTO) {


        final EntityType entityType = EntityType.valueOf(_entityType);
        final IEntity entity =
                entityService.findEntityByEntityStringKey(EntityStringKey
                        .of(entityType, _entityKey));
        if (entity == null) {

            return new TimeSerieTableDTO();
        }
        List<Kpi> kpis = Lists.newArrayList();
        if (!_timeSerieOptionsDTO.getKpiNames().isEmpty()) {
            kpis = kpiService.selectByKeys(_timeSerieOptionsDTO.getKpiNames());
        } else {
            kpis = kpiService.getAllKpisOfEntityType(entityType);
        }
        final TimeSerieTableDTO timeSerieTableDTO = new TimeSerieTableDTO();

        CollectionUtil.convertAll(kpis, new Converter<Kpi, String>()
                {


            @Override
            public String convert(final Kpi _source) {


                return _source.getName();
            }

                });
        final ArrayList<TimeSerieSimpleDTO> coordinates = new ArrayList<TimeSerieSimpleDTO>();
        for (final Kpi kpi : kpis) {
            TimeSerieImpl timeSerie;
            if (_timeSerieOptionsDTO.hasValidPeriod()) {
                final PeriodTimeSerieOptions periodTimeSerieOptions =
                        new PeriodTimeSerieOptions(kpi);
                periodTimeSerieOptions.setFromPeriod(new DateTime(_timeSerieOptionsDTO
                        .getBeginDate()));
                periodTimeSerieOptions.setToPeriod(new DateTime(_timeSerieOptionsDTO.getEndDate()));
                periodTimeSerieOptions.pickBestGranularity();
                timeSerie =
                        (TimeSerieImpl) statService.buildPeriodTimeSeries(periodTimeSerieOptions,
                                entity.getEntityKey());

            } else {

                final TimeSerieOptions timeSerieOptions = new TimeSerieOptions(kpi);
                timeSerieOptions.setTimeScale(TimeScale.PER_MONTH);
                timeSerie =
                        (TimeSerieImpl) statService.buildTimeSeries(timeSerieOptions,
                                entity.getEntityKey());
            }
            coordinates.add(convertTimeSerieIntoDTO(timeSerie));

        }
        timeSerieTableDTO.setItems(coordinates);
        return timeSerieTableDTO;


    }


    @Override
    public Double currentMeasure(final Kpi kpi, final IEntity entity) {


        Validate.notNull(kpi);
        Validate.notNull(entity);
        final HistoryKey historyKey = HistoryKey.of(kpi, entity);
        final Double currentKpiValue = statService.returnsTheLastValue(historyKey);
        LOGGER.debug("return statService.evaluateTheCurrentKpiValue({})={}", historyKey,
                currentKpiValue);
        return currentKpiValue;
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


    @Deprecated
    @Override
    public TimeSerieDTO findHistoricalMeasure(
            final KpiStringKey _kpiKey,
            final PeriodTimeSerieOptions _period) {


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

        final TimeSerieDTO findHistoricalMeasure = findHistoricalMeasure(kpi, entity, _period);
        LOGGER.debug("findHistoricalMeasure {} {} {}={}", kpi, entity, _period,
                findHistoricalMeasure);
        return findHistoricalMeasure;

    }


    @Override
    public List<TimeSerieDTO> findMultipleHistoricalMeasure(
            final KpiStringKeyList _kpiKeyList,
            final PeriodCriteria _period) {


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

        final ExtendedEntityType entityType = _kpiKeyList.getEntityType();
        final Collection<Kpi> kpis = obtainListOfKpis(kpiKeys, entityType);
        final Collection<IEntity> entities = obtainListOfEntities(entityKeys, entityType);
        for (final IEntity entity : entities) {
            
            for (final Kpi kpi : kpis) {
                LOGGER.trace("Entity {} and Kpi {} : build serie", entity, kpi);
                try {
                    options.setKpi(kpi);
                    final TimeSerieDTO timeSerieDTO = findHistoricalMeasure(kpi, entity, options);
                    LOGGER.trace("TimeSerie for kpi={} entity={} : ---> \n\t{}", kpi, entity,
                            timeSerieDTO);
                    series.add(timeSerieDTO);
                } catch (final Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                } finally {
                    LOGGER.trace("Series stored before return {}", series.size());
                }
            }
        }
        LOGGER.debug("findMultipleHistoricalMeasure=\n\tkpiKeys={}\n\tperiod={}\n\tresult={}",
                _kpiKeyList, _period, series);
        return series;
    }


    /**
     * Converts coordinate into points DTO
     *
     * @param _coordinates
     *            the coordinates.
     * @return
     */
    private List<PointDTO> convertCoordinates(final List<TimeCoordinate> _coordinates) {


        return CollectionUtil.convertAll(_coordinates, new TimeCoordinateToPointDTO());
    }


    private TimeSerieSimpleDTO convertTimeSerieIntoDTO(final TimeSerieImpl _timeSerie) {


        final TimeSerieSimpleDTO timeSerieSimpleDTO = new TimeSerieSimpleDTO();
        timeSerieSimpleDTO.setCoordinates(convertCoordinates(_timeSerie.getCoordinates()));
        timeSerieSimpleDTO.setEntityKey(_timeSerie.getEntityKey());
        timeSerieSimpleDTO.setKpiName(_timeSerie.getKpiName());
        return timeSerieSimpleDTO;
    }


    private TimeSerieDTO findHistoricalMeasure(
            final Kpi _kpi,
            final IEntity _entity,
            final PeriodTimeSerieOptions _period) {


        if (_period.getKpiID() == null) {
            _period.setKpiID(_kpi.getId());
        }
        if (_period.getGroupFormula() == null) {
            _period.setGroupFormula(_kpi.getGroupFormula());
        }

        final EntityKey entityKey = EntityKey.of(_entity);

        final TimeSerie timeSeries = statService.buildPeriodTimeSeries(_period, entityKey);
        LOGGER.debug("findHistoricalMeasure kpi= {}, entity= {}, period= {}", _kpi.getKey(),
                _entity.getKey(), _period);
        final TimeSerieDTO timeSerieDTO =
                TimeSerieConvertor.build(timeSeries.getCoordinates(), _kpi,
                        BaseEntityDto.newFromEntity(_entity));
        LOGGER.debug("findHistoricalMeasure result : ", timeSerieDTO);
        return timeSerieDTO;

    }


    private Collection<IEntity> obtainListOfEntities(
            final List<String> entityKeys,
            final ExtendedEntityType entityType) {


        final Collection<IEntity> entities;
        if (entityKeys.isEmpty()) {
            entities = entityService.getEntitiesByEntityType(entityType.getKpiType());
        } else {
            entities = entityService.getEntitiesByKey(entityType, entityKeys);
        }
        return entities;
    }


    private Collection<Kpi> obtainListOfKpis(
            final List<String> kpiKeys,
            final ExtendedEntityType entityType) {


        final Collection<Kpi> kpis;
        if (kpiKeys.isEmpty()) {
            kpis = kpiService.getAllKpisOfEntityType(entityType.getKpiType());
        } else {
            kpis = kpiService.selectByKeys(kpiKeys);
        }
        return kpis;
    }
}
