package org.komea.event.storage.impl;

import org.apache.commons.lang.Validate;
import org.komea.event.storage.IEventTypeSchemaUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;

public class EventTypeSchemaUpdater implements IEventTypeSchemaUpdater {

	private final IOrientSessionFactory	documentTx;

	private static final Logger	        LOGGER	= LoggerFactory.getLogger(EventTypeSchemaUpdater.class);

	public EventTypeSchemaUpdater(final IOrientSessionFactory _factory) {
		super();
		Validate.notNull(_factory);
		this.documentTx = _factory;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.event.storage.impl.IEventTypeSchemaUpdater#updateSchemaWithEvent
	 * (java.lang.String)
	 */
	@Override
	public void updateSchemaWithEvent(final String _eventType) {
		if (!this.documentTx.getOrCreateDatabaseSession().getMetadata().getSchema().existsClass(_eventType)) {
			this.documentTx.getOrCreateDatabaseSession().getMetadata().getSchema().createClass(_eventType);
		} else {
			LOGGER.trace("Document class is already existing {}", _eventType);
		}
	}
}
