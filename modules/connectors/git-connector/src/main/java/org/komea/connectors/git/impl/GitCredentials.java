/**
 *
 */
package org.komea.connectors.git.impl;

import org.eclipse.jgit.transport.CredentialsProvider;

/**
 * This class initializes the credentials for the git repository.
 * @author sleroy
 *
 */
public class GitCredentials {

	private final String	login;
	private final String	password;

	/**
	 * @param _login
	 * @param _password
	 */
	public GitCredentials(final String _login, final String _password) {
		this.login = _login;
		this.password = _password;

	}

	/**
	 * @return
	 */
	public CredentialsProvider init() {
		// TODO Auto-generated method stub
		return null;
	}

}
