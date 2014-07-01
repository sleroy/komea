package org.komea.product.backend.utils;

import org.komea.product.database.model.Kpi;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToKpiConvertorFactory implements ConverterFactory<String, Kpi> {
	
	@Override
	public <T extends Kpi> Converter<String, T> getConverter(Class<T> targetType) {
		return (Converter<String, T>) new StringToKpiConvertor();
	}	
	
}
