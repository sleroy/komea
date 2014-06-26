package org.komea.product.web.cyfe.rest.service;

import java.util.List;
import java.util.Map;

import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;

public interface ICyfeService {

	public List<String[]> buildValue(String _kpiKey, String _entityKey, Double value);
	
	public List<String[]> buildValues(Map<Kpi, KpiResult> values);
	
}
