package org.komea.product.eventory.rest.controllers;

import org.komea.product.eventory.rest.dto.EventListDto;
import org.komea.product.eventory.storage.api.IEventQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/query")
public class EventListQueryController {

	@Autowired
	private IEventQueryService eventQueryService;

	@RequestMapping(method = RequestMethod.GET, value = "/list/complex/{from}/{to}")
	public EventListDto getComplexEventList() {
		return new EventListDto();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/list/full/{from}/{to}")
	public EventListDto getMessageEventList() {
		return new EventListDto();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/list/simple/{from}/{to}")
	public EventListDto getSimpleEventList() {
		return new EventListDto();

	}

}
