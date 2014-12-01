package org.komea.core.model.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.utils.PojoToMap;

/**
 * Helper class to store pojos as Komea entities.
 *
 * @author sleroy
 *
 * @param <T>
 */
public class KomeaEntityFiller<T> implements IKomeaEntityFiller<T> {

	private final OKomeaModelFactory	oKomeaModelFactory;
	private final IEntityType	     entityType;

	public KomeaEntityFiller(final OKomeaModelFactory _oKomeaModelFactory, final IEntityType _entityType) {
		this.oKomeaModelFactory = _oKomeaModelFactory;
		this.entityType = _entityType;

	}

	@Override
	public void put(final T _entity) {
		final Map<String, Object> beanMap = new PojoToMap().convertPojoInMap(_entity);
		final IKomeaEntity newInstance = this.oKomeaModelFactory.newInstance(this.entityType);
		final Iterator<Entry<String, Object>> it = beanMap.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, Object> entry = it.next();
			newInstance.set(entry.getKey().toString(), entry.getValue());
		}
	}

}
