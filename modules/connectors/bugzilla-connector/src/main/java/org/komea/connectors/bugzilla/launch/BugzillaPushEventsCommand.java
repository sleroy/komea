package org.komea.connectors.bugzilla.launch;

import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import org.joda.time.DateTime;
import org.kohsuke.args4j.Option;
import org.komea.connectors.bugzilla.BugzillaEventConnector;
import org.komea.connectors.bugzilla.proxy.impl.BugzillaServerConfiguration;
import org.komea.connectors.sdk.bugtracker.IBugTrackerAPI;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;
import org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand;
import org.komea.event.storage.IEventStorage;

/**
 * Git push event
 *
 * @author sleroy
 *
 */
public class BugzillaPushEventsCommand extends AbstractPushEventsCommand {

    @Option(name = "-bzUrl", usage = "Url of Bugzilla", required = true)
    protected String bzUrl;

    @Option(name = "-bzUser", usage = "User of Bugzilla", required = true)
    protected String bzUser;

    @Option(name = "-bzPassword", usage = "Password of Bugzilla", required = true)
    protected String bzPassword;

    @Option(name = "-bzProduct", usage = "Product of Bugzilla")
    protected String bzProduct;

    public BugzillaPushEventsCommand() {
        super(IBugTrackerAPI.EVENT_NEW_BUG);
    }

    public String getBzUrl() {
        return bzUrl;
    }

    public void setBzUrl(String bzUrl) {
        this.bzUrl = bzUrl;
    }

    public String getBzUser() {
        return bzUser;
    }

    public void setBzUser(String bzUser) {
        this.bzUser = bzUser;
    }

    public String getBzPassword() {
        return bzPassword;
    }

    public void setBzPassword(String bzPassword) {
        this.bzPassword = bzPassword;
    }

    public String getBzProduct() {
        return bzProduct;
    }

    public void setBzProduct(String bzProduct) {
        this.bzProduct = bzProduct;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.main.IConnectorCommand#init()
     */
    @Override
    public void init() {
        //

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand#sendEvents
     * (org.komea.connectors.sdk.rest.impl.EventoryClientAPI)
     */
    @Override
    protected void sendEvents(final IEventoryClientAPI _eventoryClientAPI,
            final DateTime _from, final DateTime _to) throws ConnectionException, BugzillaException {
        final BugzillaServerConfiguration configuration = new BugzillaServerConfiguration();
        configuration.setServerURL(bzUrl);
        configuration.setUser(bzUser);
        configuration.setPassword(bzPassword);
        configuration.setProject(bzProduct);
        configuration.setSince(_from);
        configuration.setTo(_to);

        final IEventStorage eventStorage = _eventoryClientAPI.getEventStorage();
        eventStorage.declareEventType(IBugTrackerAPI.EVENT_NEW_BUG);
        eventStorage.declareEventType(IBugTrackerAPI.EVENT_UPDATED_BUG);

        final BugzillaEventConnector bugzillaEventConnector
                = new BugzillaEventConnector(eventStorage, configuration);
        bugzillaEventConnector.launch();
    }
}
