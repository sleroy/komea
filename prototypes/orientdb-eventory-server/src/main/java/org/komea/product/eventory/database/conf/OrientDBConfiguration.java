package org.komea.product.eventory.database.conf;

import org.komea.product.eventory.database.session.OrientDocumentDatabaseFactory;
import org.komea.product.eventory.database.session.OrientGraphDatabaseFactory;
import org.komea.product.eventory.database.session.api.IDocumentSessionFactory;
import org.komea.product.eventory.database.session.api.IGraphSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrientDBConfiguration {

	@Value("${komea.orientdb.url}")
	private String orientDBUrl;
	@Value("${komea.orientdb.username}")
	private String orientDBUserName;
	@Value("${komea.orientdb.password}")
	private String orientDBPassword;
	@Value("${komea.orientdb.pool.minsize}")
	private Integer orientPoolmin;
	@Value("${komea.orientdb.pool.minsize}")
	private Integer orientPoolmax;

	@Bean
	public IDocumentSessionFactory orientDBDocumentFactory() {
		final OrientDocumentDatabaseFactory factory = new OrientDocumentDatabaseFactory();

		factory.setUrl(this.orientDBUrl);
		factory.setUsername(this.orientDBUserName);
		factory.setPassword(this.orientDBPassword);
		factory.setMinPoolSize(this.orientPoolmin);
		factory.setMaxPoolSize(this.orientPoolmax);
		return factory;
	}

	@Bean
	public IGraphSessionFactory orientDBGraphFactory() {
		final OrientGraphDatabaseFactory factory = new OrientGraphDatabaseFactory();

		factory.setUrl(this.orientDBUrl);
		factory.setUsername(this.orientDBUserName);
		factory.setPassword(this.orientDBPassword);
		factory.setMinPoolSize(this.orientPoolmin);
		factory.setMaxPoolSize(this.orientPoolmax);
		return factory;
	}

}