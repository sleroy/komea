package org.komea.product.web.cyfe.rest.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.komea.product.backend.csv.utils.CSVExport;
import org.komea.product.backend.csv.utils.ColumnTable;
import org.komea.product.backend.csv.utils.IColumnTable;
import org.komea.product.backend.csv.utils.ILineBuilder;
import org.komea.product.backend.csv.utils.MixedTable;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.TimeCoordinate;
import org.komea.product.model.timeserie.TimeSerie;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
public class CyfeService implements ICyfeService {

	
	@Override
	public CSVExport buildValue(Kpi _kpi, IEntity _entity,
			Double _value, Double _goal) {
		
		IColumnTable table = new ColumnTable();
		
		if (_goal == null) {
			table.setColumnHeaders(_entity.getDisplayName());
			table.newLine().addNewCell(_value.toString());
		}else{
			table.setColumnHeaders(_entity.getDisplayName(), "Target");
			table.newLine().addNewCells(_value.toString(), _goal.toString());
		}
		
		return (CSVExport) table;
		
	}
	
	@Override
	public CSVExport buildValues(List<IEntity> _entities, Map<Kpi, KpiResult> _resultMap) {
		
		MixedTable table = new MixedTable();
		
		List<String> headers = Lists.newArrayList();
		headers.add("Label");
		headers.addAll(Lists.transform(_entities, new Function<IEntity, String>() {
			@Override
			public String apply(IEntity _entity) {
				return _entity.getDisplayName();
			}		
		}));
		
		table.setColumnHeaders(headers);
		
		List<Kpi> kpis = new ArrayList<Kpi>(_resultMap.keySet());
		Collections.sort(kpis, new Comparator<Kpi>() {
			@Override
			public int compare(Kpi kpi1, Kpi kpi2) {
				return kpi2.getDisplayName().compareTo(kpi1.getDisplayName());
			}
		});
		
		for (Kpi kpi : kpis) {
			ILineBuilder line = table.newLine(kpi.getDisplayName());
			KpiResult result = _resultMap.get(kpi);
			for (IEntity e : _entities) {
				Number value = result.getMap().get(e.getEntityKey());
				if (value != null) 
					line.addNewCell(value.toString());
				else
					line.addNewCell("");
			}
		}
		
		return table;
		
	}

	@Override
	public CSVExport buildSerie(Kpi _kpi, List<IEntity> _entities,
			List<TimeSerie> _timeSeries, List<String> _colors,
			List<String> _types) {
		
		MixedTable table = new MixedTable();
		
		table.setColumnHeaders("Date");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		TimeSerie timeSerie =  getTimeSerieOf(_entities.get(0), _timeSeries);

		for(TimeCoordinate c : timeSerie.getCoordinates()) {
			table.newLine(formatter.format(c.getDate().toDate()));
		}
		
		for(IEntity entity : _entities) {
			
			timeSerie =  getTimeSerieOf(entity, _timeSeries);
			List<String> values = Lists.transform(timeSerie.getCoordinates(), new Function<TimeCoordinate, String>() {
				@Override
				public String apply(TimeCoordinate c) {
					return c.getValue().toString();
				}		
			});
					
			table.newColumn(entity.getDisplayName(), values);
			
		}	
		
		if (_colors != null && !_colors.isEmpty()) {			
			table.newLine("Color").addNewCells(_colors);
		}
		
		if (_types != null && !_types.isEmpty()) {
			table.newLine("Type").addNewCells(_types);	
		}
		
		return table;
		
	}
	
	private TimeSerie getTimeSerieOf(IEntity _entity, List<TimeSerie> _timeSeries) {
		
		for(TimeSerie t : _timeSeries) {
			if (t.getEntityKey().equals(_entity.getEntityKey())) {
				return t;
			}
		}		
		return null;
		
	}

	@Override
	public CSVExport buildEventsTable(List<IEvent> events) {
		
		ColumnTable table = new ColumnTable();
		table.setColumnHeaders("Event", "Message", "Time");

		for(IEvent e : events) {		
			table.newLine().addNewCell(e.getEventType().getDisplayName()).
							addNewCell(e.getMessage()).
							addNewCell(this.getDateDiffAsString(new DateTime(e.getDate()), DateTime.now()));
		}
		
		return table;
	
	}
	
	private String getDateDiffAsString(DateTime _fromDate, DateTime _toDate) {
		
		StringBuilder s = new StringBuilder();
		
		int days = Days.daysBetween(_fromDate, _toDate).getDays();
		
		if (days > 0) {
			
			s.append(days).append(days > 1 ? " days " : " day ");
			
		}else{
			
			int hours = Hours.hoursBetween(_fromDate, _toDate).getHours();
			
			if (hours > 0) {	
				
				s.append(hours).append("h ");
				
				_fromDate = _fromDate.plusHours(hours);
				
			}

			int min = Minutes.minutesBetween(_fromDate, _toDate).getMinutes();
			
			if (min > 0) {
				
				s.append(min).append("min ");
				
				_fromDate = _fromDate.plusMinutes(min);
				
			}		
			
			int sec = Seconds.secondsBetween(_fromDate, _toDate).getSeconds();
			
			if (sec > 0) {
				s.append(sec).append("s ");
			}
			
		}
		
		return s.append("ago").toString();
		
	}

	@Override
	public CSVExport buildPiechart(Kpi _kpi, List<IEntity> _entities,
			KpiResult _result, List<String> _colors) {
		
		MixedTable table = new MixedTable();
		List<String> headers = Lists.transform(_entities, new Function<IEntity, String>() {
			@Override
			public String apply(IEntity e) {
				return e.getDisplayName();
			}		
		});
		table.setColumnHeaders(headers);
		
		Double value;
		ILineBuilder line = table.newLine();
		
		for(IEntity e : _entities) {		
			if (_result.getMap().containsKey(e.getEntityKey())) {			
				value = (Double) _result.getMap().get(e.getEntityKey());						
			}else{
				value = 0.00;
			}		
			line.addNewCell(value.toString());		
		}
		
		if (_colors != null && !_colors.isEmpty()) {
			table.newLine("Color").addNewCells(_colors);
		}
		
		return table;
	}

	@Override
	public CSVExport buildCohort(Kpi _kpi, List<IEntity> _entities, List<TimeSerie> _timeSeries, Double _goal) {
		
		MixedTable table = new MixedTable();
		
		table.setColumnHeaders("Date", "Target");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		TimeSerie timeSerie =  getTimeSerieOf(_entities.get(0), _timeSeries);

		for(TimeCoordinate c : timeSerie.getCoordinates()) {
			table.newLine(formatter.format(c.getDate().toDate())).addNewCell(_goal.toString());
		}	
		
		for(IEntity entity : _entities) {
			
			timeSerie =  getTimeSerieOf(entity, _timeSeries);		
			List<String> values = Lists.transform(timeSerie.getCoordinates(), new Function<TimeCoordinate, String>() {
				@Override
				public String apply(TimeCoordinate c) {
					return c.getValue().toString();
				}		
			}); 		
			table.newColumn(entity.getDisplayName(), values);
			
		}	
		
		return table;
		
	}

}
