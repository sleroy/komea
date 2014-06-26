package org.komea.product.web.cyfe.rest.service;

import java.util.Date;

import org.komea.product.model.timeserie.TimeScale;

public interface IStatsService {

	public Double evaluateKpiValue(String _kpiKey, String _entityKey, TimeScale _timescale);
	
	public Double evaluateKpiValueWithDate(String _kpiKey, String _entityKey, TimeScale _timescale, Date _date);
	
	
}
