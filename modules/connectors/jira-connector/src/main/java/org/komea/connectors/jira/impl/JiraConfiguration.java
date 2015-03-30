package org.komea.connectors.jira.impl;

import org.komea.connectors.jira.IJiraConfiguration;

public class JiraConfiguration implements IJiraConfiguration {

	/**
	 *
	 */
	public static final String SELECTED_FIELDS = "id,key,created,issuetype,priority,assignee,reporter,description,project,status,versions,resolution,issuetype,status,assignee,resolution,project,created,updated,creator";
	private String url;
	private String login;
	private String password;

	private String selectedFields = SELECTED_FIELDS;

	public JiraConfiguration() {
		super();
	}

	public JiraConfiguration(final String url) {

		this(url, null, null);
	}

	public JiraConfiguration(final String url, final String login,
			final String pass) {

		this.url = url;
		this.login = login;
		password = pass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.jira.impl.IJiraConfiguration#getLogin()
	 */
	@Override
	public String getLogin() {

		return login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.jira.impl.IJiraConfiguration#getPassword()
	 */
	@Override
	public String getPassword() {

		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.jira.impl.IJiraConfiguration#getSelectedFields()
	 */
	@Override
	public String getSelectedFields() {
		return selectedFields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.jira.impl.IJiraConfiguration#getUrl()
	 */
	@Override
	public String getUrl() {

		return url;
	}

	public void setLogin(final String _login) {
		login = _login;
	}

	public void setPassword(final String _password) {
		password = _password;
	}

	public void setSelectedFields(final String _selectedFields) {
		selectedFields = _selectedFields;
	}

	public void setUrl(final String _url) {
		url = _url;
	}

}
