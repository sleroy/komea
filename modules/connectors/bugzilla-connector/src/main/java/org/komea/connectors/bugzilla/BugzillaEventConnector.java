package org.komea.connectors.bugzilla;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.joda.time.DateTime;
import org.komea.connectors.bugzilla.proxy.BugzillaPluginException;
import org.komea.connectors.bugzilla.proxy.IBugzillaAPI;
import org.komea.connectors.bugzilla.proxy.impl.BugzillaAPI;
import org.komea.connectors.bugzilla.proxy.impl.BugzillaServerConfiguration;
import org.komea.connectors.sdk.bugtracker.IBugTrackerAPI;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class BugzillaEventConnector implements IBugTrackerAPI {

    /**
     * Provider name
     */
    public static final String PROVIDER_BUG = "bugzilla";

    private static final String BUGZILLA_PRODUCT = "bugzilla_product";

    private static final Logger LOGGER = LoggerFactory.getLogger(BugzillaEventConnector.class);

    private final IEventStorage eventStorage;
    private final IBugzillaAPI bugzillaAPI;
    private final BugzillaServerConfiguration configuration;

    public BugzillaEventConnector(final IBugzillaAPI _bugzillaAPI,
            final IEventStorage _eventStorage,
            final BugzillaServerConfiguration _configuration) {
        this.eventStorage = _eventStorage;
        this.bugzillaAPI = _bugzillaAPI;
        this.configuration = _configuration;

    }

    public BugzillaEventConnector(final IEventStorage _eventStorage,
            final BugzillaServerConfiguration _configuration) {
        this.eventStorage = _eventStorage;
        this.bugzillaAPI = new BugzillaAPI();
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
                LOGGER.info("Processing {}/{}",
                        numberOfBugsProcessed,
                        bugList.size());
            }
            if (this.isRecentlyCreated(bug, this.bugzillaAPI)) {
                final KomeaEvent complexEventDto = this.createBugEvent(bug,
                        EVENT_NEW_BUG,
                        _productName);
                this.eventStorage.storeEvent(complexEventDto);
            }
            if (this.isRecentlyUpdated(bug, this.bugzillaAPI)) {
                final KomeaEvent complexEventDto = this.createBugEvent(bug,
                        EVENT_UPDATED_BUG,
                        _productName);
                this.eventStorage.storeEvent(complexEventDto);
            }
            numberOfBugsProcessed++;
        }
    }

    /**
     * @return Tests if a bug has been recently created (creationTime > since)
     * @param bug the bug
     * @param _bugzillaAPI the bugzilla api
     */
    public boolean isRecentlyCreated(final Bug bug,
            final IBugzillaAPI _bugzillaAPI) {
        final DateTime creationTime = _bugzillaAPI.getCreationTime(bug);
        if (this.configuration.getSince() == null) {
            return !creationTime.isAfter(this.configuration.getTo());
        }
        return creationTime.isAfter(this.configuration.getSince())
                && !creationTime.isAfter(this.configuration.getTo());
    }

    /**
     * @return Tests if a bug has been recently updated (updateTime > since).
     * @param bug the bug
     * @param _bugzillaAPI the bugzilla api
     */
    public boolean isRecentlyUpdated(final Bug bug,
            final IBugzillaAPI _bugzillaAPI) {
        final DateTime updatedTime = _bugzillaAPI.getUpdatedTime(bug);
        if (this.configuration.getSince() == null) {
            return !updatedTime.isAfter(this.configuration.getTo());
        }
        return updatedTime.isAfter(this.configuration.getSince())
                && !updatedTime.isAfter(this.configuration.getTo());
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

    private KomeaEvent createBugEvent(final Bug bug, final String eventName,
            final String _productName) {
        final KomeaEvent complexEventDto = new KomeaEvent();
        complexEventDto.setProvider(PROVIDER_BUG);
        complexEventDto.setEventType(eventName);

        final Map<String, Object> properties = Maps.newHashMap();
        properties.put(BUGZILLA_PRODUCT, _productName);
        for (final Entry<Object, Object> entry : bug.getParameterMap()
                .entrySet()) {
            if (entry.getValue() instanceof Serializable) {
                properties.put(entry.getKey().toString(), entry.getValue());
            }
        }
        complexEventDto.addFields(properties);
        return complexEventDto;
    }

    private void launchBugs() throws ConnectionException, BugzillaException {
        this.bugzillaAPI.initConnection(this.configuration.getServerURL());
        if (this.configuration.hasLogin()) {
            this.bugzillaAPI.login(this.configuration.getUser(),
                    this.configuration.getPassword());
        }
        final List<String> productNames = Lists.newArrayList();
        if (this.configuration.hasProject()) {
            productNames.add(this.configuration.getProject());
        } else {
            productNames.addAll(this.bugzillaAPI.obtainProductList());
        }
        for (final String productName : productNames) {
            this.analysisOfProjectBug(productName, this.bugzillaAPI);
        }
    }
}
