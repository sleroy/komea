package org.komea.product.eventory.storage.service;

import org.komea.product.eventory.database.dao.EventStorageDao;
import org.komea.product.eventory.database.model.EventStorageCriteria;
import org.komea.product.eventory.storage.api.IEventQueryService;
import org.komea.product.eventory.storage.api.IFieldPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(isolation = Isolation.DEFAULT, readOnly = true, propagation = Propagation.REQUIRED)
public class EventQueryService implements IEventQueryService {

	@Autowired
	private EventStorageDao eventStorageDao;

	@Override
	public int countWithFieldPredicate(final IFieldPredicate fieldPredicate) {

		return this.eventStorageDao.countByField(fieldPredicate);
	}

	@Override
	public int getNumberEvents() {

		return this.eventStorageDao.countByCriteria(new EventStorageCriteria());
	}

}
