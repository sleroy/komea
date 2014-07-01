package org.komea.product.backend.utils;

import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.google.common.collect.Lists;




//@Component("conversionService")
public class KomeaConversionService extends ConversionServiceFactoryBean  {


    public KomeaConversionService() {
        super();
        DefaultConversionService conversionservice = (DefaultConversionService)  super.getObject();
        conversionservice.addConverter(new StringToKpiConvertor());
    }
	
	/*@Autowired
	private DefaultFormattingConversionService factory;
	*/
	
	/*@Autowired
	private GenericWebApplicationContext context;
	
	public KomeaConversionService() {
		super();
		//addConverter(new StringToKpiConvertor());
		//addConverter(String.class, Kpi.class, new StringToKpiConvertor());
		addConverterFactory(new StringToKpiConvertorFactory());
	}*/
	
	/*
	@PostConstruct
	public void init() {
		factory.addConverter(String.class, Kpi.class, (org.springframework.core.convert.converter.Converter<?, ?>) new StringToKpiConvertor());
	}*/

}
