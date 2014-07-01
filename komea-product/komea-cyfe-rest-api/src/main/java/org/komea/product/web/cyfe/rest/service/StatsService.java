package org.komea.product.web.cyfe.rest.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.google.common.collect.Sets;


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

    private PeriodCriteria buildPeriod(TimeScale _timescale, Date _date) {
    	
    	PeriodCriteria period = new PeriodCriteria();
    	Calendar calendar = Calendar.getInstance();
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
    
    private KpiResult removeUnnecessaryEntities(KpiResult _result, List<IEntity> _desiredEntities) {
    	
    	Set<EntityKey> entityKeys = new HashSet<EntityKey>();
		for(IEntity entity : _desiredEntities) {
			entityKeys.add(entity.getEntityKey());
		}
		
		Set<EntityKey> uselessKeys = Sets.newHashSet(_result.getMap().keySet());
		uselessKeys.removeAll(entityKeys);
		
		for(EntityKey key : uselessKeys) {
			_result.getMap().remove(key);
		}
		
		return _result;
    	
    }
    
    @Override
    public Double evaluateKpiValue(Kpi _kpi, IEntity _entity, TimeScale _timescale) {

        TimeSerieOptions timeSerieOptions = new TimeSerieOptions(_kpi);
        timeSerieOptions.setTimeScale(_timescale != null ? _timescale : TimeScale.PER_DAY);

        return statisticsAPI.evaluateKpiValue(timeSerieOptions, _entity.getEntityKey());

    }

    @Override
    public Double evaluateKpiValueWithDate(Kpi _kpi, IEntity _entity,
            TimeScale _timescale, Date _date) {

    	PeriodCriteria period = this.buildPeriod(_timescale, _date);
    	
        PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(_kpi);
        options.setTimeScale(_timescale != null ? _timescale : TimeScale.PER_DAY);
        options.setFromPeriod(new DateTime(period.getStartDate()));
        options.setToPeriod(new DateTime(period.getEndDate()));       

        return statisticsAPI.evaluateKpiValueOnPeriod(options, _entity.getEntityKey());

    }
    
	@Override
	public KpiResult evaluateKpiValues(Kpi _kpi,
			List<IEntity> _entities, TimeScale _timescale) {
		
		TimeSerieOptions options = new TimeSerieOptions(_kpi);
        options.setTimeScale(_timescale != null ? _timescale : TimeScale.PER_DAY);
        
		KpiResult result = statisticsAPI.evaluateKpiValues(options);

		return this.removeUnnecessaryEntities(result, _entities);
	
	}
	
	@Override
	public KpiResult evaluateKpiValues(Kpi _kpi,
			List<IEntity> _entities, TimeScale _timescale, Date _date) {
		
		PeriodCriteria period = this.buildPeriod(_timescale, _date);
		
		PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(_kpi);
		options.setTimeScale(_timescale != null ? _timescale : TimeScale.PER_DAY);
		options.setFromPeriod(new DateTime(period.getStartDate()));
        options.setToPeriod(new DateTime(period.getEndDate()));    
        
        KpiResult result = statisticsAPI.evaluateKpiValuesOnPeriod(options);
        
        return this.removeUnnecessaryEntities(result, _entities);
		
	}

	@Override
	public Map<Kpi, KpiResult> evaluateKpiValues(List<Kpi> _kpis,
			List<IEntity> _entities, TimeScale _timescale, Date _date) {
		
		KpiResult result;
		Map<Kpi, KpiResult> resultMap = Maps.newHashMap();
		
		for (Kpi kpi : _kpis) {		
			
			if (_date == null) {
				result = evaluateKpiValues(kpi, _entities, _timescale);
			}else{
				result = evaluateKpiValues(kpi, _entities, _timescale, _date);
			}
			resultMap.put(kpi, result);
			
		}
		
		return resultMap;
		
	}

	@Override
	public TimeSerie buildTimeSerie(Kpi _kpi, IEntity _entity,
			TimeScale _timeScale, Date _since) {
		
		TimeSerie ts = null;
		
		if (_since == null) {
			
			TimeSerieOptions options = new TimeSerieOptions(_kpi);
			options.setTimeScale(_timeScale != null ? _timeScale : TimeScale.PER_DAY);
			
			ts = statisticsAPI.buildTimeSeries(options, _entity.getEntityKey());
		
		}else{
			
			PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(_kpi);
			options.setTimeScale(_timeScale != null ? _timeScale : TimeScale.PER_DAY);
			options.setFromPeriod(new DateTime(_since));
			options.setToPeriod(DateTime.now());
			
			ts = statisticsAPI.buildPeriodTimeSeries(options, _entity.getEntityKey());
			
		}
		
		return ts;
	}

	@Override
	public List<TimeSerie> buildTimeSeries(Kpi _kpi, List<IEntity> _entities,
			TimeScale _timeScale, Date _since) {
		
		List<TimeSerie> result = Lists.newArrayList();
		
		for(IEntity entity : _entities) {
			TimeSerie ts = buildTimeSerie(_kpi, entity, _timeScale, _since);
			if (ts != null) {
				result.add(ts);
			}
		}
		
		return result;
		
	}

	@Override
	public List<IEvent> findEvents(SearchEventDto _search) {
		
		List<IEvent> globalActivity = eventService.getGlobalActivity();
		return eventsFilter.filterEvents(_search, globalActivity);
		
	}

}
