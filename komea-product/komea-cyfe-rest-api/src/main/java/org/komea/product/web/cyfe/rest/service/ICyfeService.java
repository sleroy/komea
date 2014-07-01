package org.komea.product.web.cyfe.rest.service;

import java.util.List;
import java.util.Map;

import org.komea.product.backend.csv.utils.CSVExport;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.TimeSerie;

public interface ICyfeService {

	/**
	 * Export data to Cyfe format for displaying
	 * a simple kpi value in Cyfe
	 * with a gauge if the parameter 'goal' is not null
	 * @param _kpi
	 * @param _entity
	 * @param _value
	 * @param _goal
	 * @return an object that can be serialized into a CSV file
	 */
	public CSVExport buildValue(Kpi _kpi, IEntity _entity, Double _value, Double _goal);
	
	/**
	 * Export data to Cyfe format for displaying
	 * a table with values from multiples kpis
	 * and multiple entities
	 * @param _entities
	 * @param _resultMap
	 * @return an object that can be serialized into a CSV file
	 */
	public CSVExport buildValues(List<IEntity> _entities, Map<Kpi, KpiResult> _resultMap);
	
	/**
	 * Export data to Cyfe format for displaying a graph
	 * with kpi values over a period of time
	 * @param _kpi
	 * @param _entities
	 * @param _timeSeries
	 * @param _colors
	 * @param _types
	 * @return an object that can be serialized into a CSV file
	 */
	public CSVExport buildSerie(Kpi _kpi, List<IEntity> _entities, List<TimeSerie> _timeSeries, List<String> _colors, List<String> _types);
	
	/**
	 * Export data to Cyfe format for displaying
	 * a piechart or a donut graph with kpi values
	 * @param _kpi
	 * @param _entities
	 * @param _result
	 * @param _colors
	 * @return an object that can be serialized into a CSV file
	 */
	public CSVExport buildPiechart(Kpi _kpi, List<IEntity> _entities, KpiResult _result, List<String> _colors);
	
	/**
	 * Export data to Cyfe format for displaying
	 * a list of events
	 * @param events
	 * @return an object that can be serialized into a CSV file
	 */
	public CSVExport buildEventsTable(List<IEvent> _events);
		
	/**
	 * Export data to Cyfe format for displaying
	 * a cohort table with kpi values in Cyfe
	 * @param _kpi
	 * @param _entities
	 * @param goal
	 * @return
	 */
	public CSVExport buildCohort(Kpi _kpi, List<IEntity> _entities, List<TimeSerie> _timeSeries, Double _goal);
}
