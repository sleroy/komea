package org.komea.event.queries.factory;

import java.io.File;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public enum DbType {

	H2_FILE("h2:file", ";MODE=MYSQL;", null, "sa", "") {
		@Override
		public String getDefaultUrl() {
			return new File(FileUtils.getTempDirectory(), getRandomName())
					.getAbsolutePath();
		}
	}

	,
	H2_MEM("h2:mem", ";MODE=MYSQL;", null, "sa", "") {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.komea.event.queries.factory.DbType#getDefaultUrl()
		 */
		@Override
		public String getDefaultUrl() {
			return getRandomName();
		}
	}

	,
	MYSQL("mysql", "", "//localhost/events", "root", "root");

	private static String getRandomName() {
		return "events_" + new Random().nextInt(80000) + "_"
				+ new Date().getTime();
	}

	private static String getRandomTempPath() {
		return new File(FileUtils.getTempDirectory(), getRandomName())
				.getAbsolutePath();
	}

	/**
	 *
	 */

	private final String type;

	private final String extraOptions;

	private final String defaultUrl;

	private final String defaultUser;

	private final String defaultPassword;

	private DbType(final String type, final String extraOptions,
			final String defaultUrl, final String defaultUser,
			final String defaultPassword) {
		this.type = type;
		this.extraOptions = extraOptions;
		this.defaultUrl = defaultUrl;
		this.defaultUser = defaultUser;
		this.defaultPassword = defaultPassword;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public String getDefaultUrl() {

		return defaultUrl;
	}

	public String getDefaultUser() {
		return defaultUser;
	}

	public String getExtraOptions() {
		return extraOptions;
	}

	public String getType() {
		return type;
	}

}
