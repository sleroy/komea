package org.komea.connectors.bugzilla;

import java.util.List;

import org.joda.time.DateTime;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;

/**
 * This interface defines a proxy above the J2Bugzilla API to obtain the main
 * informations required to send events to Komea.
 *
 * @author sleroy
 *
 */
public interface IBugzillaAPI {

	public List<Bug> getBugList(String _productName) throws BugzillaException;

	public DateTime getCreationTime(Bug bug);

	public DateTime getUpdatedTime(Bug bug);

	/**
	 * Obtains a new bugzilla connection
	 * 
	 * @param _serverURL
	 *            the server url.
	 * 
	 * @throws ConnectionException
	 */
	public void initConnection(String _serverURL) throws ConnectionException;

	public void login(String _user, String _password) throws BugzillaException;

	public List<String> obtainProductList() throws BugzillaException;

}