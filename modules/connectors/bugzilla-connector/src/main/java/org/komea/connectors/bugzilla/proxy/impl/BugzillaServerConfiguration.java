package org.komea.connectors.bugzilla.proxy.impl;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

public class BugzillaServerConfiguration {
	private String	 serverURL	= null;
	private String	 user	   = "";
	private String	 password	= "";
	private String	 project	= "";

	private DateTime	since;

	public String getPassword() {
		return this.password;
	}

	public String getProject() {
		return this.project;
	}

	public String getServerURL() {
		return this.serverURL;
	}

	public DateTime getSince() {
		return this.since;
	}

	public String getUser() {
		return this.user;
	}

	@JsonIgnore
	public boolean hasLogin() {
		return !Strings.isNullOrEmpty(this.getUser());
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setProject(final String _project) {
		this.project = _project;
	}

	public void setServerURL(final String serverURL) {
		this.serverURL = serverURL;
	}

	public void setSince(final DateTime since) {
		this.since = since;
	}

	public void setUser(final String user) {
		this.user = user;
	}
}
