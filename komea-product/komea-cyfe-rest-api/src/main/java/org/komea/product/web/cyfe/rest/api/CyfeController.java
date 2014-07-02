package org.komea.product.web.cyfe.rest.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.komea.product.backend.csv.utils.CSVExport;
import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.service.IKpiGoalService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.utils.StringToEntityConvertor;
import org.komea.product.backend.utils.StringToKpiConvertor;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerie;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.web.cyfe.rest.service.ICyfeService;
import org.komea.product.web.cyfe.rest.service.IStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;


@Controller
@RequestMapping(value = "/cyfe")
public class CyfeController {

	@Autowired
	private IStatsService service;
	
	@Autowired
	private IEntityService entityService;
	
	@Autowired
	private IKpiGoalService goalService;
	
	@Autowired
	private ICyfeService cyfeService;
	
	@Autowired
	private StringToKpiConvertor kpiConverter;
	
	@Autowired
	private StringToEntityConvertor entityConverter;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CyfeController.class);

	
	private List<IEntity> getSelectedEntitiesOrAll(final EntityType _type, final List<String> _entityKeys) {
		
		List<IEntity> entities = Lists.newArrayList();		
		if (_entityKeys != null && !_entityKeys.isEmpty()) {
			entities = entityConverter.convert(_type, _entityKeys);	
		}else{
			entities = entityService.getEntitiesByEntityType(_type);
		}
		return entities;	
		
	}
	
	private Double getKpiGoal(final Kpi _kpi) {
		KpiKey key = KpiKey.ofKpi(_kpi);
		List<KpiGoal> goals = goalService.findKpiGoals(key);		
		if (goals != null && !goals.isEmpty()) {
			for(KpiGoal goal : goals) {
				if (goal.getEntityID() == null) {
					return goal.getValue();
				}
			}
		}
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/value/{kpiKey}/{entityKey}", produces = "text/csv;charset=UTF-8")
	@ResponseBody
	public CSVExport getValue(@PathVariable(value="kpiKey") final String _kpiKey, 
			@PathVariable(value="entityKey") final String _entityKey,
			@RequestParam(value="timescale", required=false) final TimeScale _timescale,
			@RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") final DateTime _date,
			@RequestParam(value="goal", required=false) Double _goal,
			final HttpServletResponse _response) throws IOException {
		
		Kpi kpi = kpiConverter.convert(_kpiKey);
		IEntity entity = entityConverter.convert(kpi.getEntityType(), _entityKey); 
		
		LOGGER.debug("cyfe/value called with params : {}, {}", kpi, _entityKey);	
		
		if (_goal == null) {
			_goal = getKpiGoal(kpi);
		}
		
		Double kpiValue;
		
		if (_date == null) {
			kpiValue = service.evaluateKpiValue(kpi, entity, _timescale);
		}else{
			kpiValue = service.evaluateKpiValueWithDate(kpi, entity, _timescale, _date.toDate());
		}     
		
		return cyfeService.buildValue(kpi, entity, kpiValue, _goal);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/values", produces = "text/csv; charset=UTF-8")
	@ResponseBody
	public CSVExport getValues(@RequestParam(value="kpiKeys") final List<String> _kpiKeys, 
			@RequestParam(value="entityKeys", required=false) final List<String> _entityKeys,
			@RequestParam(value="timescale", required=false) final TimeScale _timescale,
			@RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") final DateTime _date) {
		
		LOGGER.debug("cyfe/values called with params : {}, {}", _kpiKeys, _entityKeys);
		
		List<Kpi> kpis = kpiConverter.convert(_kpiKeys);
		List<IEntity> entities = getSelectedEntitiesOrAll(kpis.get(0).getEntityType(), _entityKeys);
		Map<Kpi, KpiResult> results = service.evaluateKpiValues(kpis, entities, _timescale, _date != null ? _date.toDate() : null);
		
		return cyfeService.buildValues(entities, results);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/serie/{kpiKey}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public CSVExport getSerie(@PathVariable(value="kpiKey") final String _kpiKey,
			@RequestParam(value="entityKeys", required=false) final List<String> _entityKeys,
			@RequestParam(value="timescale", required=false) final TimeScale _timeScale,
			@RequestParam(value="since", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") final DateTime _since,
			@RequestParam(value="colors", required=false) final List<String> _colors,
			@RequestParam(value="types", required=false) final List<String> _types) {
		
		Kpi kpi = kpiConverter.convert(_kpiKey);
		List<IEntity> entities = getSelectedEntitiesOrAll(kpi.getEntityType(), _entityKeys);
		List<TimeSerie> timeSeries = service.buildTimeSeries(kpi, entities, _timeScale, _since != null ? _since.toDate() : null);
		
		return cyfeService.buildSerie(kpi, entities, timeSeries, _colors, _types);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pie/{kpiKey}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public CSVExport getPie(@PathVariable(value="kpiKey") final String _kpiKey,
			@RequestParam(value="entityKeys", required=false) final List<String> _entityKeys,
			@RequestParam(value="timescale", required=false) final TimeScale _timescale,
			@RequestParam(value="colors", required=false) final List<String> _colors) {
		
		Kpi kpi = kpiConverter.convert(_kpiKey);
		List<IEntity> entities = getSelectedEntitiesOrAll(kpi.getEntityType(), _entityKeys);
		
		KpiResult result = service.evaluateKpiValues(kpi, entities, _timescale);
		
		return cyfeService.buildPiechart(kpi, entities, result, _colors);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/events/{entityType}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public CSVExport getEvents(@PathVariable(value="entityType") final ExtendedEntityType _entityType,
			@RequestParam(value="severityMin") final Severity _severityMin,
			@RequestParam(value="sizeMax") final Integer _sizeMax,
			@RequestParam(value="eventKeys") final List<String> _eventKeys,
			@RequestParam(value="entityKeys", required=false) final List<String> _entityKeys) {

		SearchEventDto search = new SearchEventDto();
		search.setEntityType(_entityType);
		search.setSeverityMin(_severityMin);
		search.setMaxEvents(_sizeMax);
		search.setEntityKeys(_entityKeys);
		search.setEventTypeKeys(_eventKeys);
		
		List<IEvent> events = service.findEvents(search);
		return cyfeService.buildEventsTable(events);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cohort/{kpiKey}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public CSVExport getCohort(@PathVariable(value="kpiKey") final String _kpiKey,
			@RequestParam(value="entityKeys", required=false) final List<String> _entityKeys,
			@RequestParam(value="timescale", required=false) final TimeScale _timeScale,
			@RequestParam(value="since", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") final DateTime _since,
			@RequestParam(value="goal", required=false) Double _goal) {
		
		Kpi kpi = kpiConverter.convert(_kpiKey);
		List<IEntity> entities = getSelectedEntitiesOrAll(kpi.getEntityType(), _entityKeys);
		
		if (_goal == null) {
			_goal = getKpiGoal(kpi);
		}
		
		List<TimeSerie> timeSeries = service.buildTimeSeries(kpi, entities, _timeScale, _since != null ? _since.toDate() : null);
		
		return cyfeService.buildCohort(kpi, entities, timeSeries, _goal);
		
	}
	
	@ExceptionHandler({ KPINotFoundException.class, EntityNotFoundException.class, KPINotFoundRuntimeException.class })
    @ResponseBody
    public void handleNotFoundException(final Throwable _ex, final HttpServletResponse _response) {
		
		LOGGER.error(_ex.getMessage(), _ex);
		_response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
	}
	
	@ExceptionHandler({ MissingServletRequestParameterException.class, TypeMismatchException.class })
    @ResponseBody
    public void handleBadRequest(final Throwable _ex, final HttpServletResponse _response) {
		
		LOGGER.error(_ex.getMessage(), _ex);
		_response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
	}
	
	@ExceptionHandler
    @ResponseBody
    public void handleException(final Throwable _ex, final HttpServletResponse _response) {
		
		LOGGER.error(_ex.getMessage(), _ex);
		_response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}
	
}
