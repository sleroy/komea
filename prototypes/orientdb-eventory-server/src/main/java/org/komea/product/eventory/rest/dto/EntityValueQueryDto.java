package org.komea.product.eventory.rest.dto;

import java.util.List;

import com.google.common.collect.Lists;

public class EntityValueQueryDto {

	private List<EntityValueDto> entityValueDtos = Lists.newArrayList();

	public EntityValueQueryDto() {
		super();
	}

	public EntityValueQueryDto(final List<EntityValueDto> EntityValueDtos) {
		super();
		this.entityValueDtos = EntityValueDtos;
	}

	public List<EntityValueDto> getEntityValueDtos() {
		return this.entityValueDtos;
	}

	public void setEntityValueDtos(final List<EntityValueDto> _EntityValueDtos) {
		this.entityValueDtos = _EntityValueDtos;
	}

}
