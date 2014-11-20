package org.komea.product.eventory.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class EventListDto {

	private final List<SimpleEventDto> eventDtos = new ArrayList();

	public EventListDto() {
		super();
	}

	public List<SimpleEventDto> getEventDtos() {
		return eventDtos;
	}
}
