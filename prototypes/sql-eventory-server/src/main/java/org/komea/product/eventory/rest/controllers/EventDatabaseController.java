package org.komea.product.eventory.rest.controllers;

import org.komea.product.eventory.storage.api.IEventQueryService;
import org.komea.product.eventory.storage.api.IFieldPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/database")
public class EventDatabaseController {

	@Autowired
	private IEventQueryService eventQueryService;

	@RequestMapping(method = RequestMethod.GET, value = "/count")
	public int countElements() {
		return this.eventQueryService.getNumberEvents();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/field_count/{fieldName}/{fieldValue}")
	public int countWithFieldPredicate(@PathVariable final String fieldName,
			@PathVariable final String fieldValue) {
		return this.eventQueryService
				.countWithFieldPredicate(new IFieldPredicate() {

					@Override
					public String getFieldName() {

						return fieldName;
					}

					@Override
					public String getFieldValue() {

						return fieldValue;
					}
				});

	}
}
