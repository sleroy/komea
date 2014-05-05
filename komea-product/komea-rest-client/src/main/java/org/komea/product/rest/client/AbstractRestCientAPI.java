package org.komea.product.rest.client;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.komea.product.rest.client.api.IRestClientAPI;
import org.komea.product.service.dto.errors.ErrorMessage;
import org.komea.product.service.dto.errors.ErrorMessageConvertor;
import org.komea.product.service.dto.errors.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Komea abstract class to manage http request with the komea server REST API
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 5 févr. 2014
 */
abstract class AbstractRestCientAPI implements IRestClientAPI {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractRestCientAPI.class);
    //
    // private URI serverURI;
    private final ResteasyClient client;
    private final String REST_BASE_URL = "rest";

    private ResteasyWebTarget target;

    public AbstractRestCientAPI() {

        client = new ResteasyClientBuilder().build();
    }

    /**
     * (non-Javadoc)
     *
     * @throws InternalServerException
     * @see
     * org.komea.product.rest.client.api.IRestClientAPI#get(java.lang.String,
     * java.lang.Class)
     */
    @Override
    public <R> R get(final String _url, final Class<R> _returnType)
            throws ConnectException, InternalServerException {

        if (!testConnectionValid()) {
            throw new ConnectException("can't connect to the server");
        }
        final Response response = createRequest(_url).get();
        validateResponse(response);
        final R value = response.readEntity(_returnType);

        response.close(); // clonse connection

        return value;
    }

    /**
     * (non-Javadoc)
     *
     * @throws InternalServerException
     * @see
     * org.komea.product.rest.client.api.IRestClientAPI#get(java.lang.String,
     * javax.ws.rs.core.GenericType)
     */
    @Override
    public <R> R get(final String _url, final GenericType<R> _returnType, final String... _params)
            throws ConnectException, InternalServerException {

        if (!testConnectionValid()) {
            throw new ConnectException("can't connect to the server");
        }
        final Response response = createRequest(_url, _params).get();
        validateResponse(response);
        final R value = response.readEntity(_returnType);

        response.close(); // close connection

        return value;
    }

    public Client getClient() {

        return client;
    }

    public WebTarget getTarget() {

        return target;
    }

    /**
     * (non-Javadoc)
     *
     * @throws InternalServerException
     * @see
     * org.komea.product.rest.client.api.IRestClientAPI#post(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public <T> void post(final String _url, final T _objectToSend)
            throws ConnectException, InternalServerException {

        if (!testConnectionValid()) {
            throw new ConnectException("can't connect to the server");
        }

        final Response response = createRequest(_url).post(Entity.json(_objectToSend));
        validateResponse(response);

        response.close(); // close connection
    }

    /**
     * (non-Javadoc)
     *
     * @throws ConnectException
     * @throws Throwable
     * @see
     * org.komea.product.rest.client.api.IRestClientAPI#post(java.lang.String,
     * java.lang.Object, java.lang.Class)
     */
    @Override
    public <T, R> R post(final String url, final T _objectToSend, final Class<R> _returnType)
            throws InternalServerException, ConnectException {

        if (!testConnectionValid()) {
            throw new ConnectException("can't connect to the server");
        }
        final Response response = createRequest(url).post(Entity.json(_objectToSend));
        validateResponse(response);
        final R value = response.readEntity(_returnType);
        response.close(); // close connection
        return value;

    }

    /**
     * (non-Javadoc)
     *
     * @throws InternalServerException
     * @see
     * org.komea.product.rest.client.api.IRestClientAPI#post(java.lang.String,
     * java.lang.Object, javax.ws.rs.core.GenericType)
     */
    @Override
    public <T, R> R post(final String url, final T _objectToSend, final GenericType<R> _returnType)
            throws ConnectException, InternalServerException {

        if (!testConnectionValid()) {
            throw new ConnectException("can't connect to the server");
        }
        final Response response = createRequest(url).post(Entity.json(_objectToSend));
        validateResponse(response);
        final R value = response.readEntity(_returnType);

        response.close(); // close connection

        return value;
    }

    @Override
    public void setServerBaseURL(final String _serverURL)
            throws URISyntaxException, ConnectException {

        final URI serverURI = new URI(_serverURL);
        target = client.target(serverURI);
        if (!testConnectionValid()) {
            target = null;
            throw new ConnectException(MessageFormat.format("can't connect to the server {}",
                    _serverURL));
        }

    }

    public void setTarget(final ResteasyWebTarget _target) {

        target = _target;
    }

    @Override
    public boolean testConnectionValid() {

        if (target == null) {
            return false;
        }
        final Response response = target.request().get();
        final int status = response.getStatus();

        response.close(); // close connection
        LOGGER.debug("Response received : ", status);
        return status == Response.Status.OK.getStatusCode()
                || status == Response.Status.FOUND.getStatusCode();

    }

    private Builder createRequest(final String _url, final String... _params) {

        final WebTarget path = getTarget().path(REST_BASE_URL).path(_url);
        for (final String param : _params) {
            path.path(param);
        }

        LOGGER.debug("http request = " + path.getUri().toString());

        return path.request();
    }

    private void validateResponse(final Response response) throws InternalServerException {

        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            final ErrorMessage errorMessage = response.readEntity(ErrorMessage.class);
            final InternalServerException errorException
                    = new InternalServerException("Exception happened in Server Side : "
                            + errorMessage.getMessage());

            errorException.initCause(ErrorMessageConvertor.convertToException(errorMessage));
            throw errorException;
        }
    }
}
