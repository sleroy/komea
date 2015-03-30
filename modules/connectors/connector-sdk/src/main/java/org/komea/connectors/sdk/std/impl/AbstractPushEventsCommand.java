package org.komea.connectors.sdk.std.impl;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;

public abstract class AbstractPushEventsCommand extends AbstractEventoryCommand {

    private final String eventTypeName;

    public AbstractPushEventsCommand(final String _eventTypeName) {
        super();
        this.eventTypeName = _eventTypeName;

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.main.IConnectorCommand#action()
     */
    @Override
    public String action() {

        return "push";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.main.IConnectorCommand#description()
     */
    @Override
    public String description() {
        return "Push events to the server";
    }

    @Override
    public void init() throws Exception {
        //

    }

    /**
     * Read the last launch time
     *
     * @param _eventoryClientAPI
     *
     * @return
     */
    private DateTime readLastLaunchTime(
            final IEventoryClientAPI _eventoryClientAPI) {
        if (hasLastLaunchDate()) {
            return getSince();
        }
        try {
            return _eventoryClientAPI.getLastEvent(this.eventTypeName);
        } catch (final Exception e) {
            throw new IllegalArgumentException("Could not obtain date of last event of type '" + this.eventTypeName
                    + "'. You can set it with option '-s {date}', example : '-s 01/01/2014'", e);
        }
    }

    /**
     * @param _eventoryClientA
     * @throws Exception
     */
    @Override
    protected final void runCommand(final IEventoryClientAPI _eventoryClientAPI)
            throws Exception {
        final DateTime readLastLaunchTime = this
                .readLastLaunchTime(_eventoryClientAPI);
        final DateTime to = DateTime.now();
        this.sendEvents(_eventoryClientAPI, readLastLaunchTime, to);
        FileUtils.write(new File(IEventoryClientAPI.LAST_FETCH_DATE_FILE_NAME), String.valueOf(to.toDate().getTime()));
    }

    /**
     * @param _eventoryClientAPI
     * @throws Exception
     */
    protected abstract void sendEvents(IEventoryClientAPI _eventoryClientAPI,
            final DateTime _from, final DateTime _to) throws Exception;

}
