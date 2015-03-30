package org.komea.product.eventory.rest.controllers;

import org.komea.product.eventory.dao.api.IEventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/database")
public class DatabaseController {

	@Autowired
	private IEventDao eventQueryService;

	@RequestMapping(method = RequestMethod.GET, value = "/count/{className}")
	public long countElements(@PathVariable final String className) {
		return this.eventQueryService.countEvents(className);

	}

}
