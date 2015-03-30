package org.komea.product.eventory.rest.controllers;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.komea.product.eventory.rest.dto.ComplexEventDto;
import org.komea.product.eventory.rest.dto.SimpleEventDto;
import org.komea.product.eventory.storage.api.IEventStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/storage")
public class StorageController {

	@Autowired
	private IEventStorageService eventStorageService;

	@RequestMapping(method = RequestMethod.GET, value = "/push/{provider}/{eventName}/{value}")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushGetEvent(@PathVariable final String provider,
			@NotEmpty @PathVariable final String eventName,
			@NotNull @PathVariable final Double value) {

		final SimpleEventDto simpleEventDto = new SimpleEventDto();

		simpleEventDto.setEventKey(eventName);
		simpleEventDto.setValue(value);
		simpleEventDto.setProvider(provider);

		this.eventStorageService.store(simpleEventDto);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/push_complex")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushPostComplexEvent(
			@RequestBody final ComplexEventDto simpleEventDto) {
		this.eventStorageService.store(simpleEventDto);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/push")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushPostEvent(@RequestBody final SimpleEventDto simpleEventDto) {
		this.eventStorageService.store(simpleEventDto);

	}
}
