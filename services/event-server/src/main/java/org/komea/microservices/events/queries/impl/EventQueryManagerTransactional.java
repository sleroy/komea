package org.komea.microservices.events.queries.impl;

import org.komea.event.query.impl.EventQueryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EventQueryManagerTransactional extends EventQueryManager {

	@Autowired
	public EventQueryManagerTransactional(final IOrientSessionFactory _configuration) {
		super(_configuration);

	}

}
