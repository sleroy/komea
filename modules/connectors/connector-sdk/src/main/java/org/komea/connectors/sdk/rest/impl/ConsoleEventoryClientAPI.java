/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.rmi.ServerException;
import javax.ws.rs.core.GenericType;
import org.joda.time.DateTime;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Eventory client api.
 *
 * @author sleroy
 *
 */
public class ConsoleEventoryClientAPI implements IEventoryClientAPI {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConsoleEventoryClientAPI.class);

    /*
     * (non-Javadoc)
     *
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        LOGGER.info("Closing connection");
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
        LOGGER.info("Counting events for {}", _eventType);
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.IRestClientAPI#delete(java.lang.String,
     * java.lang.String[])
     */
    @Override
    public void delete(final String _url, final String[] _params)
            throws ConnectException, ServerException {
        LOGGER.info("DELETE>> {} with params {} expecting type {}", _url,
                _params);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.IRestClientAPI#deleteURL(java.lang.String
     * [])
     */
    @Override
    public void deleteURL(final String[] _params) throws ConnectException,
            ServerException {
        LOGGER.info("DELETE>> {} ", _params);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#get(java.lang.Class,
     * java.lang.String[])
     */
    @Override
    public <R> R get(final Class<R> _returnType, final String... _params)
            throws ConnectException, ServerException {
        LOGGER.info("GET>> with params {} expecting type {}", _params,
                _returnType);
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#get(java.lang.String,
     * java.lang.Class)
     */
    @Override
    public <R> R get(final String _url, final Class<R> _returnType)
            throws ConnectException, ServerException {
        LOGGER.info("GET>> {} expecting type {}", _url, _returnType.getName());
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#get(java.lang.String,
     * java.lang.Class, java.lang.String[])
     */
    @Override
    public <R> R get(final String _url, final Class<R> _returnType,
            final String... _params) throws ConnectException, ServerException {
        LOGGER.info("GET>> {} with params {} expecting type {}", _url, _params,
                _returnType.getName());
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#get(java.lang.String,
     * javax.ws.rs.core.GenericType, java.lang.String[])
     */
    @Override
    public <R> R get(final String _url, final GenericType<R> _returnType,
            final String... _params) throws ConnectException, ServerException {
        LOGGER.info("GET>> {} with params {} expecting type {}", _url, _params,
                _returnType);
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#get(java.lang.String,
     * java.lang.String[])
     */
    @Override
    public void get(final String _url, final String... _params)
            throws ConnectException, ServerException {
        LOGGER.info("GET>> {} with params {} expecting type {}", _url, _params);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#getEventStorage()
     */
    @Override
    public IEventStorage getEventStorage() {

        return new ConsoleEventStorage();
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
        LOGGER.info("GET >> LAST_EVENT  {}", _eventTypeName);
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#post(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public <T> void post(final String _url, final T _objectToSend)
            throws ConnectException, ServerException {
        try {
            LOGGER.info("POST>> {} with {}", _url,
                    new ObjectMapper().writeValueAsString(_objectToSend));
        } catch (final JsonProcessingException e) {

            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#post(java.lang.String,
     * java.lang.Object, java.lang.Class)
     */
    @Override
    public <T, R> R post(final String _url, final T _objectToSend,
            final Class<R> _returnType) throws ConnectException,
            ServerException {
        try {
            LOGGER.info("POST>> {} with {} expecting type {}", _url,
                    new ObjectMapper().writeValueAsString(_objectToSend),
                    _returnType.getName());
        } catch (final JsonProcessingException e) {

            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#post(java.lang.String,
     * java.lang.Object, javax.ws.rs.core.GenericType)
     */
    @Override
    public <T, R> R post(final String _url, final T _objectToSend,
            final GenericType<R> _returnType) throws ConnectException,
            ServerException {
        try {
            LOGGER.info("POST>> {} with {} expecting type {}", _url,
                    new ObjectMapper().writeValueAsString(_objectToSend),
                    _returnType);
        } catch (final JsonProcessingException e) {

            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.IRestClientAPI#postURL(java.lang.Object,
     * java.lang.String[])
     */
    @Override
    public <T> void postURL(final T _objectToSend, final String... _params)
            throws ConnectException, ServerException {
        try {
            LOGGER.info("POST>> {} with {} ", _params,
                    new ObjectMapper().writeValueAsString(_objectToSend));
        } catch (final JsonProcessingException e) {

            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.IRestClientAPI#setServerBaseURL(java.lang
     * .String)
     */
    @Override
    public void setServerBaseURL(final String _serverURL)
            throws URISyntaxException, ConnectException {
        LOGGER.info("CONNECT >>  {}", _serverURL);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.sdk.rest.IRestClientAPI#testConnectionValid()
     */
    @Override
    public boolean testConnectionValid() {
        LOGGER.info("Testing Connection = TRUE");
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.sdk.rest.impl.IEventoryClientAPI#testConnexion()
     */
    @Override
    public void testConnexion() throws ConnectException, ServerException {
        LOGGER.info("testConnexion");

    }

    @Override
    public <T, R> R post(T _objectToSend, Class<R> _returnType, String... params) throws ConnectException, ServerException {
        try {
            LOGGER.info("POST>> {} with {} expecting type {}", params,
                    new ObjectMapper().writeValueAsString(_objectToSend), _returnType);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
