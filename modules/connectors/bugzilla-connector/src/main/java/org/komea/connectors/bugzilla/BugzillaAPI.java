package org.komea.connectors.bugzilla;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cern.colt.Arrays;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.GetAccessibleProducts;
import com.j2bugzilla.rpc.GetProduct;
import com.j2bugzilla.rpc.LogIn;

/**
 * This class is a proxy above the J2Bugzilla API. You may mock it for your unit
 * tests.
 *
 * @author sleroy
 *
 */
public class BugzillaAPI implements IBugzillaAPI, Closeable {
	/**
	 * Defines the default field for the creation time.
	 */
	public static final String CREATION_TIME = "creation_time";

	private final String serverURL;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BugzillaAPI.class);

	private BugzillaConnector conn;

	public BugzillaAPI(final String _serverURL) {
		super();
		this.serverURL = _serverURL;
	}

	@Override
	public void close() throws IOException {
		// Nothing to do right now
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.bugzilla.IBugzillaAPI#getBugList(java.lang.String)
	 */
	@Override
	public List<Bug> getBugList(final String _productName)
			throws BugzillaException {
		final BugSearch2 search = new BugSearch2(new BugSearch2.SearchQuery(
				BugSearch2.SearchLimiter.PRODUCT, _productName));
		this.conn.executeMethod(search);
		return search.getSearchResults();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.bugzilla.IBugzillaAPI#getCreationTime(com.j2bugzilla
	 * .base.Bug)
	 */
	@Override
	public DateTime getCreationTime(final Bug bug) {
		return new DateTime(bug.getParameterMap().get(CREATION_TIME));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.connectors.bugzilla.IBugzillaAPI#getUpdatedTime(com.j2bugzilla
	 * .base.Bug)
	 */
	@Override
	public DateTime getUpdatedTime(final Bug bug) {
		return new DateTime(bug.getParameterMap().get(CREATION_TIME));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.bugzilla.IBugzillaAPI#initConnection()
	 */
	@Override
	public void initConnection() throws ConnectionException {
		LOGGER.info("Creating bugzilla connector {}", this.serverURL);

		this.conn = new BugzillaConnector();
		this.conn.connectTo(this.serverURL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.bugzilla.IBugzillaAPI#login(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void login(final String _user, final String _password)
			throws BugzillaException {
		LOGGER.info("Authenticate");

		this.conn.executeMethod(new LogIn(_user, _password));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.connectors.bugzilla.IBugzillaAPI#obtainProductList()
	 */
	@Override
	public List<String> obtainProductList() throws BugzillaException {
		final List<String> projects = Lists.newArrayList();
		LOGGER.info("Fetching project list");
		final GetAccessibleProducts getAccessibleProducts = new GetAccessibleProducts();
		this.conn.executeMethod(getAccessibleProducts);
		LOGGER.debug("Obtain product ids {}",
				Arrays.toString(getAccessibleProducts.getProductIDs()));
		for (final int productID : getAccessibleProducts.getProductIDs()) {
			final GetProduct getProductInfo = new GetProduct(productID);
			this.conn.executeMethod(getProductInfo);
			projects.add(getProductInfo.getProduct().getName());
		}
		return projects;
	}

}
