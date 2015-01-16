package org.komea.microservices.events.storage.impl;

import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.impl.EventStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EventStorageTransactional extends EventStorage {

	private static final String DEFAULT_TABLE_NAME = "events";

	@Autowired
	public EventStorageTransactional(final IEventDBFactory _dbFactory) {
		super(_dbFactory);

	}

}
