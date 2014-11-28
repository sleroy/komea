package org.komea.core.model.impl;

import java.util.Iterator;

import org.apache.commons.beanutils.BeanMap;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;

/**
 * Helper class to store pojos as Komea entities.
 *
 * @author sleroy
 *
 * @param <T>
 */
public class KomeaEntityFiller<T> implements IKomeaEntityFiller<T> {

	private final OKomeaModelFactory oKomeaModelFactory;
	private final IEntityType entityType;

	public KomeaEntityFiller(final OKomeaModelFactory _oKomeaModelFactory,
			final IEntityType _entityType) {
		this.oKomeaModelFactory = _oKomeaModelFactory;
		this.entityType = _entityType;

	}

	@Override
	public void put(final T _entity) {
		final BeanMap beanMap = new BeanMap(_entity);
		final IKomeaEntity newInstance = this.oKomeaModelFactory
				.newInstance(this.entityType);
		final Iterator<?> iterator = beanMap.entryIterator();
		while (iterator.hasNext()) {
			final Object field = iterator.next();
			newInstance.add(iterator.next().toString(), beanMap.get(field));
		}

	}

}
