package org.komea.orientdb.session.impl;

import java.text.MessageFormat;

public class RemoteDatabaseConfiguration extends DatabaseConfiguration {

	private static final int DEFAULT_PORT = 2424;

	public RemoteDatabaseConfiguration(final String _url, final int port,
			final String _databaseName) {
		this.setUsername("admin");
		this.setPassword("admin");

		this.setUrl(this.buildURL(_url, port, _databaseName));
	}

	public RemoteDatabaseConfiguration(final String _url,
			final String _databaseName) {
		this(_url, DEFAULT_PORT, _databaseName);
	}

	private String buildURL(final String _url, final int _port,
			final String _databaseName) {
		// remote:<server>:[<port>]/db-name
		return MessageFormat.format("remote:{0}/{2}", _url, _port,
				_databaseName);
	}

}
