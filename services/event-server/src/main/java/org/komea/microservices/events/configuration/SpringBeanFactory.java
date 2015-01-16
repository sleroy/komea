package org.komea.microservices.events.configuration;

import javax.sql.DataSource;

import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.mysql.impl.EventDBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EventStorageSettings.class)
public class SpringBeanFactory {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EventStorageSettings connection;

	@Bean()
	public IEventDBFactory newEventDbFactory() {
		return new EventDBFactory(dataSource);

	}
}
