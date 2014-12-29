package org.komea.event.storage.impl;

import org.apache.commons.lang.Validate;
import org.komea.event.storage.IEventTypeSchemaUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.metadata.schema.OClass.INDEX_TYPE;

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
	    
		if (!this.documentTx.getOrCreateDB().getMetadata().getSchema().existsClass(_eventType)) {
			OClass eventType = this.documentTx.getOrCreateDB().getMetadata().getSchema().createClass(_eventType);
			OProperty property = eventType.createProperty("date", OType.DATE);
			property.createIndex(INDEX_TYPE.NOTUNIQUE);
		
		} else {
			LOGGER.trace("Document class is already existing {}", _eventType);
		}
	}
}
