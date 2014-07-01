package org.komea.product.web.cyfe.rest.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.utils.EventsFilter;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerie;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.PeriodCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class StatsService implements IStatsService {

	@Autowired
	private IKPIService kpiService;

	@Autowired
	private IEntityService entityService;

	@Autowired
	private IStatisticsAPI statisticsAPI;

	@Autowired
	private IEventViewerService eventService;

	@Autowired
	private EventsFilter eventsFilter;

	private PeriodCriteria buildPeriod(final TimeScale _timescale,
			final Date _date) {

		final PeriodCriteria period = new PeriodCriteria();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(_date);

		switch (_timescale) {

		case PER_MONTH:
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			period.setStartDate(calendar.getTime());
			calendar.add(Calendar.MONTH, 1);
			period.setEndDate(calendar.getTime());
			break;

		case PER_YEAR:
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			period.setStartDate(calendar.getTime());
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			period.setEndDate(calendar.getTime());
			break;

		default:
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			period.setStartDate(calendar.getTime());
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			period.setEndDate(calendar.getTime());
			break;

		}

		return period;

	}

	@Override
	public TimeSerie buildTimeSerie(final Kpi _kpi, final IEntity _entity,
			final TimeScale _timeScale, final Date _since) {

		TimeSerie ts = null;

		if (_since == null) {

			final TimeSerieOptions options = new TimeSerieOptions(_kpi);
			options.setTimeScale(_timeScale != null ? _timeScale
					: TimeScale.PER_DAY);

			ts = this.statisticsAPI.buildTimeSeries(options,
					_entity.getEntityKey());

		} else {

			final PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(
					_kpi);
			options.setTimeScale(_timeScale != null ? _timeScale
					: TimeScale.PER_DAY);
			options.setFromPeriod(new DateTime(_since));
			options.setToPeriod(DateTime.now());

			ts = this.statisticsAPI.buildPeriodTimeSeries(options,
					_entity.getEntityKey());

		}

		return ts;
	}

	@Override
	public List<TimeSerie> buildTimeSeries(final Kpi _kpi,
			final List<IEntity> _entities, final TimeScale _timeScale,
			final Date _since) {

		final List<TimeSerie> result = Lists.newArrayList();

		for (final IEntity entity : _entities) {
			final TimeSerie ts = buildTimeSerie(_kpi, entity, _timeScale,
					_since);
			if (ts != null) {
				result.add(ts);
			}
		}

		return result;

	}

	@Override
	public Double evaluateKpiValue(final Kpi _kpi, final IEntity _entity,
			final TimeScale _timescale) {

		final TimeSerieOptions timeSerieOptions = new TimeSerieOptions(_kpi);
		timeSerieOptions.setTimeScale(_timescale != null ? _timescale
				: TimeScale.PER_DAY);

		return this.statisticsAPI.evaluateKpiValue(timeSerieOptions,
				_entity.getEntityKey());

	}

	@Override
	public KpiResult evaluateKpiValues(final Kpi _kpi,
			final List<IEntity> _entities, final TimeScale _timescale) {

		final TimeSerieOptions options = new TimeSerieOptions(_kpi);
		options.setTimeScale(_timescale != null ? _timescale
				: TimeScale.PER_DAY);

		final KpiResult result = this.statisticsAPI.evaluateKpiValues(options);

		return removeUnnecessaryEntities(result, _entities);

	}

	@Override
	public KpiResult evaluateKpiValues(final Kpi _kpi,
			final List<IEntity> _entities, final TimeScale _timescale,
			final Date _date) {

		final PeriodCriteria period = buildPeriod(_timescale, _date);

		final PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(_kpi);
		options.setTimeScale(_timescale != null ? _timescale
				: TimeScale.PER_DAY);
		options.setFromPeriod(new DateTime(period.getStartDate()));
		options.setToPeriod(new DateTime(period.getEndDate()));

		final KpiResult result = this.statisticsAPI
				.evaluateKpiValuesOnPeriod(options);

		return removeUnnecessaryEntities(result, _entities);

	}

	@Override
	public Map<Kpi, KpiResult> evaluateKpiValues(final List<Kpi> _kpis,
			final List<IEntity> _entities, final TimeScale _timescale,
			final Date _date) {

		KpiResult result;
		final Map<Kpi, KpiResult> resultMap = Maps.newHashMap();

		for (final Kpi kpi : _kpis) {

			if (_date == null) {
				result = evaluateKpiValues(kpi, _entities, _timescale);
			} else {
				result = evaluateKpiValues(kpi, _entities, _timescale, _date);
			}
			resultMap.put(kpi, result);

		}

		return resultMap;

	}

	@Override
	public Double evaluateKpiValueWithDate(final Kpi _kpi,
			final IEntity _entity, final TimeScale _timescale, final Date _date) {

		final PeriodCriteria period = buildPeriod(_timescale, _date);

		final PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(_kpi);
		options.setTimeScale(_timescale != null ? _timescale
				: TimeScale.PER_DAY);
		options.setFromPeriod(new DateTime(period.getStartDate()));
		options.setToPeriod(new DateTime(period.getEndDate()));

		return this.statisticsAPI.evaluateKpiValueOnPeriod(options,
				_entity.getEntityKey());

	}

	@Override
	public List<IEvent> findEvents(final SearchEventDto _search) {

		final List<IEvent> globalActivity = this.eventService
				.getGlobalActivity();
		return this.eventsFilter.filterEvents(_search, globalActivity);

	}

	private KpiResult removeUnnecessaryEntities(final KpiResult _result,
			final List<IEntity> _desiredEntities) {
		if (_result == null) {
			return null;
		}
		final Map<EntityKey, Number> desiredMap = Maps.newHashMap();
		for (final IEntity entity : _desiredEntities) {
			final EntityKey key = entity.getEntityKey();
			final Number value = _result.getMap().get(key);
			if (value != null) {
				desiredMap.put(entity.getEntityKey(), value);
			}
		}
		return new KpiResult(desiredMap);

	}

}
