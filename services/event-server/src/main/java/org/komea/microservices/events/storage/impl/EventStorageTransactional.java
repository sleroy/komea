package org.komea.microservices.events.storage.impl;

import org.komea.event.storage.impl.EventStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EventStorageTransactional extends EventStorage {
	@Autowired
	public EventStorageTransactional(final IOrientSessionFactory _sessionFactory) {
		super(_sessionFactory);

	}

}
