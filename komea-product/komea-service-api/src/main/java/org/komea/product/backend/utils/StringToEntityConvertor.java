package org.komea.product.backend.utils;

import java.util.List;

import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityStringKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEntityConvertor  implements Converter<String, IEntity>  {

	@Autowired
	private IEntityService service;
	
	public IEntity convert(EntityType _type, String _key) {
		return service.getEntityOrFail(EntityStringKey.of(_type, _key));
	}
	
	public List<IEntity> convert(EntityType _type, List<String> _keys) {
		List<IEntity> entities = service.getEntitiesByKey(_type, _keys);
		if (entities.size() != _keys.size()) {
			throw new EntityNotFoundException("", _type);
		}
		return entities;
	}

	@Override
	public IEntity convert(String source) {
		return null;
	}
	
}
