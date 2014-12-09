package org.komea.microservices.events.configuration;

import org.komea.event.storage.IEventTypeSchemaUpdater;
import org.komea.event.storage.impl.EventTypeSchemaUpdater;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;

@Configuration
public class ApplicationBeanFactory {

	/**
	 * Initializes a bean with Event Query manager.
	 *
	 * @param _factory
	 * @return
	 */
	@ConditionalOnMissingBean(EventTypeSchemaUpdater.class)
	@Bean
	public IEventTypeSchemaUpdater eventTypeSchemaUpdater(final IOrientSessionFactory _factory) {
		return new EventTypeSchemaUpdater(_factory);

	}
}
