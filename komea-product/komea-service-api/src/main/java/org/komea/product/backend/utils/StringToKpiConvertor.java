package org.komea.product.backend.utils;

import java.util.List;

import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToKpiConvertor implements Converter<String, Kpi> {

	@Autowired
	private IKPIService service;

	@Override
	public Kpi convert(String _kpiKey) {
		return service.selectByKeyOrFail(_kpiKey);
	}
	
	public List<Kpi> convert(List<String> _kpiKeys) {
		List<Kpi> result = service.selectByKeys(_kpiKeys);
		if (result.size() != _kpiKeys.size())
			throw new KPINotFoundException(null, null);
		return result;
	}

}