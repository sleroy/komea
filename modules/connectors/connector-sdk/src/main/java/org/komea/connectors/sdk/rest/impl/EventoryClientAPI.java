package org.komea.connectors.sdk.rest.impl;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.ServerException;
import javax.ws.rs.client.WebTarget;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.komea.core.exceptions.KomeaRuntimeException;
import org.komea.event.storage.IEventStorage;

public class EventoryClientAPI extends AbstractClientAPI implements
        IEventoryClientAPI {

    public EventoryClientAPI() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#countEvents(java
     * .lang.String)
     */
    @Override
    public Integer countEvents(final String _eventType)
            throws ConnectException, ServerException {

        return this.get("/database/count", Integer.class, _eventType);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#getEventStorage()
     */
    @Override
    public IEventStorage getEventStorage() {

        return new EventStorageRestAPI(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#getLastEvent(java
     * .lang.String)
     */
    @Override
    public DateTime getLastEvent(final String _eventTypeName) {
        final File file = new File(LAST_FETCH_DATE_FILE_NAME);
        final String path = file.getAbsolutePath();
        if (!file.exists()) {
            throw new KomeaRuntimeException("File " + path + " does not exist");
        }
        if (!file.isFile()) {
            throw new KomeaRuntimeException(path + " is not a file");
        }
        try {
            final String string = FileUtils.readFileToString(file).trim();
            if (string.matches("\\d+")) {
                return new DateTime(Long.valueOf(string));
            } else {
                throw new KomeaRuntimeException("File " + path + " has invalid date value (expected timestamp) : " + string);
            }
        } catch (IOException ex) {
            throw new KomeaRuntimeException("Could not read file " + path, ex);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#testConnexion()
     */
    @Override
    public void testConnexion() throws ConnectException, ServerException {
        LOGGER.info(this.get("/hello", String.class));

    }

    @Override
    protected WebTarget prefixPath(final WebTarget _target) {

        return _target;
    }
}
