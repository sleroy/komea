package org.komea.connectors.bugzilla;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.event.model.api.IComplexEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.api.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;

/**
 * This class implements a simple bugzilla connector that fetchs the list of
 * products from the bugtracker and generates two kind of events. It detects :
 * <ul>
 * <li>The newly created bugs</li>
 * <li>The bugs recently updated</li>
 * </ul>
 *
 * @author sleroy
 *
 */
public class BugzillaDataConnector {

	private static final String EVENT_NEWBUG = "newbug";
	private static final String EVENT_UPDATED = "bugupdated";
	private static final String BUG = "bug";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BugzillaDataConnector.class);

	private final IEventStorage eventStorage;
	private final IBugzillaAPI bugzillaAPI;
	private final BugzillaServerConfiguration configuration;

	public BugzillaDataConnector(final IBugzillaAPI _bugzillaAPI,
			final IEventStorage _eventStorage,
			final BugzillaServerConfiguration _configuration) {
		this.eventStorage = _eventStorage;
		this.bugzillaAPI = _bugzillaAPI;
		this.configuration = _configuration;

	}

	public void analysisOfProjectBug(final String _productName,
			final IBugzillaAPI _bugzillaAPI) throws BugzillaException {
		LOGGER.info("Fetching bugs");
		final List<Bug> bugList = _bugzillaAPI.getBugList(_productName);
		LOGGER.info("Bugs found {}", bugList.size());
		int numberOfBugsProcessed = 0;
		for (final Bug bug : bugList) {
			LOGGER.debug("Processing bug {}", bug.getSummary());
			if (numberOfBugsProcessed % 100 == 0) {
				LOGGER.info("Processing {}/{}", numberOfBugsProcessed,
						bugList.size());
			}
			if (this.isRecentlyCreated(bug, this.bugzillaAPI)) {
				final IComplexEvent complexEventDto = this.createBugEvent(bug,
						EVENT_NEWBUG);
				this.eventStorage.storeComplexEvent(complexEventDto);
			}
			if (this.isRecentlyUpdated(bug, this.bugzillaAPI)) {
				final IComplexEvent complexEventDto = this.createBugEvent(bug,
						EVENT_UPDATED);
				this.eventStorage.storeComplexEvent(complexEventDto);
			}
			numberOfBugsProcessed++;
		}
	}

	/**
	 * @return Tests if a bug has been recently created (creationTime > since)
	 * @param bug
	 *            the bug
	 * @param _bugzillaAPI
	 *            the bugzilla api
	 */
	public boolean isRecentlyCreated(final Bug bug,
			final IBugzillaAPI _bugzillaAPI) {
		if (this.configuration.getSince() == null) {
			return true;
		}
		return _bugzillaAPI.getCreationTime(bug).isAfter(
				this.configuration.getSince());
	}

	/**
	 * @return Tests if a bug has been recently updated (updateTime > since).
	 * @param bug
	 *            the bug
	 * @param _bugzillaAPI
	 *            the bugzilla api
	 */

	public boolean isRecentlyUpdated(final Bug bug,
			final IBugzillaAPI _bugzillaAPI) {
		if (this.configuration.getSince() == null) {
			return true;
		}
		return _bugzillaAPI.getUpdatedTime(bug).isAfter(
				this.configuration.getSince());
	}

	/**
	 * Example :
	 * <ul>
	 * <li>https://issues.apache.org/bugzilla/</li>
	 * <li>https://bugs.eclipse.org/bugs/</li>
	 */
	public void launch() {
		try {
			this.launchBugs();
		} catch (final Exception ex) {
			throw new BugzillaPluginException(ex);
		} finally {
			LOGGER.info("Closing connection");

		}

	}

	private IComplexEvent createBugEvent(final Bug bug, final String eventName) {
		final ComplexEvent complexEventDto = new ComplexEvent();
		complexEventDto.setProvider(BUG);
		complexEventDto.setEventKey(eventName);
		final Map<String, Serializable> properties = Maps.newHashMap();
		for (final Entry<Object, Object> entry : bug.getParameterMap()
				.entrySet()) {
			if (entry.getValue() instanceof Serializable) {
				properties.put(entry.toString(),
						(Serializable) entry.getValue());
			}
		}
		complexEventDto.setProperties(properties);
		return complexEventDto;
	}

	private boolean hasLogin() {
		return !Strings.isNullOrEmpty(this.configuration.getUser());
	}

	private void launchBugs() throws ConnectionException, BugzillaException {
		this.bugzillaAPI.initConnection();
		if (this.hasLogin()) {
			this.bugzillaAPI.login(this.configuration.getUser(),
					this.configuration.getPassword());
		}
		final List<String> productNames = Lists.newArrayList();
		if (this.configuration.getProject().isEmpty()) {
			productNames.addAll(this.bugzillaAPI.obtainProductList());
		} else {
			productNames.add(this.configuration.getProject());
		}
		for (final String productName : productNames) {
			this.analysisOfProjectBug(productName, this.bugzillaAPI);
		}
	}
}
