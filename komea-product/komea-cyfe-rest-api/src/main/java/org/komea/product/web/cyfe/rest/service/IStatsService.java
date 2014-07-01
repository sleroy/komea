package org.komea.product.web.cyfe.rest.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.komea.product.database.alert.IEvent;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerie;

public interface IStatsService {
	
	/**
	 * Retrieve the current value of a kpi
	 * @param _kpi
	 * @param _entity
	 * @param _timescale
	 * @return
	 */
	public Double evaluateKpiValue(Kpi _kpi, IEntity _entity, TimeScale _timescale);
	
	/**
	 * Retrieve the value of a kpi
	 * from a specified date and timescale
	 * @param _kpi
	 * @param _entity
	 * @param _timescale
	 * @param _date
	 * @return
	 */
	public Double evaluateKpiValueWithDate(Kpi _kpi, IEntity _entity, TimeScale _timescale, Date _date);
	
	/**
	 * Retrieve a list of the current values of a kpi
	 * from multiple entities
	 * @param _kpi
	 * @param _entities
	 * @param _timescale
	 * @return
	 */
	public KpiResult evaluateKpiValues(Kpi _kpi, List<IEntity> _entities, TimeScale _timescale);
	
	/**
	 * Retrieve a list of values of a kpi
	 * from multiple entities
	 * and a specified date and timescale
	 * @param _kpi
	 * @param _entities
	 * @param _timescale
	 * @param _date
	 * @return
	 */
	public KpiResult evaluateKpiValues(Kpi _kpi, List<IEntity> _entities, TimeScale _timescale, Date _date);
	
	/**
	 * Retrieve multiple kpis values
	 * @param _kpis
	 * @param _entities
	 * @param _timescale
	 * @param _date
	 * @return
	 */
	public Map<Kpi, KpiResult> evaluateKpiValues(List<Kpi> _kpis, List<IEntity> _entities, TimeScale _timescale, Date _date);
	
	/**
	 * Build a period time serie 
	 * from the values of a kpi
	 * @param _kpi
	 * @param _entity
	 * @param _timeScale
	 * @param _since
	 * @return
	 */
	public TimeSerie buildTimeSerie(Kpi _kpi, IEntity _entity, TimeScale _timeScale, Date _since);
	
	/**
	 * Build multiple period time series
	 * @param _kpi
	 * @param _entities
	 * @param _timeScale
	 * @param _since
	 * @return
	 */
	public List<TimeSerie> buildTimeSeries(Kpi _kpi, List<IEntity> _entities, TimeScale _timeScale, Date _since);	
	
	/**
	 * Find a list of events
	 * @param _search
	 * @return
	 */
	public List<IEvent> findEvents(SearchEventDto _search);
	
}
