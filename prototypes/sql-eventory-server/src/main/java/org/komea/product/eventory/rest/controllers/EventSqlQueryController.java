package org.komea.product.eventory.rest.controllers;

import java.util.List;

import org.komea.product.eventory.database.dao.EventStorageDao;
import org.komea.product.eventory.database.model.AggregationFormula;
import org.komea.product.eventory.database.model.EntityValue;
import org.komea.product.eventory.rest.dto.EntityValueDto;
import org.komea.product.eventory.rest.dto.EntityValueQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/query")
public class EventSqlQueryController {

	@Autowired
	private EventStorageDao eventStorageDao;

	@RequestMapping(method = RequestMethod.POST, value = "/sql")
	public EntityValueQueryDto executeSQLQuery(
			@RequestBody final AggregationFormula sqlQuery) {
		final List<EntityValue> aggregateOnPeriod = this.eventStorageDao
				.aggregateOnPeriod(sqlQuery);
		new EntityValueQueryDto();
		final List<EntityValueDto> dtos = Lists.newArrayList();
		for (final EntityValue value : aggregateOnPeriod) {
			dtos.add(new EntityValueDto(value));
		}
		return new EntityValueQueryDto(dtos);

	}

}
