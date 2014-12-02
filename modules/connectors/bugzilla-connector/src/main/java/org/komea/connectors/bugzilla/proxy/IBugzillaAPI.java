package org.komea.connectors.bugzilla.proxy;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.base.Product;
import com.j2bugzilla.rpc.GetLegalValues.Fields;

/**
 * This interface defines a proxy above the J2Bugzilla API to obtain the main
 * informations required to send events to Komea.
 *
 * @author sleroy
 *
 */
public interface IBugzillaAPI {

	public List<Bug> getBugList(String _productName) throws BugzillaException;

	/**
	 * Returns the date bug has been created
	 *
	 * @param bug
	 *            the bug
	 */
	public DateTime getCreationTime(Bug bug);

	/**
	 * Returns the list of values for a field.
	 *
	 * @param _field
	 * @return
	 * @throws BugzillaException
	 */
	public Set<String> getLegalValues(Fields _field) throws BugzillaException;

	/**
	 * Returns the date bug has been updated.
	 *
	 * @param bug
	 *            the bug
	 */
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

	/**
	 * Login on the bugzilla server
	 *
	 * @param _user
	 *            the user
	 * @param _password
	 *            the plain password
	 * @throws BugzillaException
	 */
	public void login(String _user, String _password) throws BugzillaException;

	/**
	 * Obtain the product list as strings
	 *
	 * @throws BugzillaException
	 */
	public List<String> obtainProductList() throws BugzillaException;

	Product getProductDefinition(String _productName) throws BugzillaException;

	/**
	 * Returns the product description list.
	 *
	 * @return
	 * @throws BugzillaException
	 */
	List<Product> getProducts() throws BugzillaException;

}