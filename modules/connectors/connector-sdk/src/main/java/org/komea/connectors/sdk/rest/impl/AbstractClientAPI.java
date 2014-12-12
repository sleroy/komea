package org.komea.connectors.sdk.rest.impl;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.ServerException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.komea.connectors.sdk.rest.IRestClientAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractClientAPI implements IRestClientAPI {

	protected static final Logger	LOGGER	= LoggerFactory.getLogger(AbstractClientAPI.class);
	protected final ResteasyClient	client;
	private ResteasyWebTarget	   target;

	public AbstractClientAPI() {
		super();
		this.client = new ResteasyClientBuilder().build();

	}

	@Override
	public void close() {
		this.client.close();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @throws ServerException
	 * @see org.komea.connectors.sdk.rest.product.rest.client.api.IRestClientAPI#get(java.lang.String,
	 *      java.lang.Class)
	 */
	@Override
	public <R> R get(final String _url, final Class<R> _returnType) throws ConnectException, ServerException {

		final Response response = this.createRequest(_url).get();
		this.validateResponse(response);
		final R value = response.readEntity(_returnType);

		response.close(); // clonse connection

		return value;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @throws ServerException
	 * @see org.komea.connectors.sdk.rest.product.rest.client.api.IRestClientAPI#get(java.lang.String,
	 *      java.lang.Class)
	 */
	@Override
	public <R> R get(final String _url, final Class<R> _returnType, final String... params) throws ConnectException,
	ServerException {

		final Response response = this.createRequest(_url, params).get();
		this.validateResponse(response);
		final R value = response.readEntity(_returnType);

		response.close(); // clonse connection

		return value;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @throws ServerException
	 * @see org.komea.connectors.sdk.rest.product.rest.client.api.IRestClientAPI#get(java.lang.String,
	 *      javax.ws.rs.core.GenericType)
	 */
	@Override
	public <R> R get(final String _url, final GenericType<R> _returnType, final String... _params)
	        throws ConnectException, ServerException {

		final Response response = this.createRequest(_url, _params).get();
		this.validateResponse(response);
		final R value = response.readEntity(_returnType);

		response.close(); // close connection

		return value;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @throws ServerException
	 * @see org.komea.connectors.sdk.rest.product.rest.client.api.IRestClientAPI#get(java.lang.String,
	 *      java.lang.Class)
	 */
	@Override
	public void get(final String _url, final String... params) throws ConnectException, ServerException {

		final Response response = this.createRequest(_url, params).get();
		this.validateResponse(response);

		response.close(); // clonse connection

	}

	public Client getClient() {

		return this.client;
	}

	public WebTarget getTarget() {

		return this.target;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @throws ServerException
	 * @see org.komea.connectors.sdk.rest.product.rest.client.api.IRestClientAPI#post(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public <T> void post(final String _url, final T _objectToSend) throws ConnectException, ServerException {

		final Response response = this.createRequest(_url).post(Entity.json(_objectToSend));
		this.validateResponse(response);

		response.close(); // close connection
	}

	/**
	 * (non-Javadoc)
	 *
	 * @throws ConnectException
	 * @throws Throwable
	 * @see org.komea.connectors.sdk.rest.product.rest.client.api.IRestClientAPI#post(java.lang.String,
	 *      java.lang.Object, java.lang.Class)
	 */
	@Override
	public <T, R> R post(final String url, final T _objectToSend, final Class<R> _returnType) throws ServerException,
	        ConnectException {

		final Response response = this.createRequest(url).post(Entity.json(_objectToSend));
		this.validateResponse(response);
		final R value = response.readEntity(_returnType);
		response.close(); // close connection
		return value;

	}

	/**
	 * (non-Javadoc)
	 *
	 * @throws ServerException
	 * @see org.komea.connectors.sdk.rest.product.rest.client.api.IRestClientAPI#post(java.lang.String,
	 *      java.lang.Object, javax.ws.rs.core.GenericType)
	 */
	@Override
	public <T, R> R post(final String url, final T _objectToSend, final GenericType<R> _returnType)
	        throws ConnectException, ServerException {

		final Response response = this.createRequest(url).post(Entity.json(_objectToSend));
		this.validateResponse(response);
		final R value = response.readEntity(_returnType);

		response.close(); // close connection

		return value;
	}

	@Override
	public void setServerBaseURL(final String _serverURL) throws URISyntaxException, ConnectException {

		final URI serverURI = new URI(_serverURL);
		this.target = this.client.target(serverURI);

	}

	public void setTarget(final ResteasyWebTarget _target) {

		this.target = _target;
	}

	@Override
	public boolean testConnectionValid() {

		if (this.target == null) { return false; }
		final Response response = this.target.request().get();
		final int status = response.getStatus();

		response.close(); // close connection
		LOGGER.debug("Response received : ", status);
		return status == Response.Status.OK.getStatusCode() || status == Response.Status.FOUND.getStatusCode();

	}

	private Builder createRequest(final String _url, final String... _params) {

		WebTarget path = this.prefixPath(this.getTarget()).path(_url);
		for (final String param : _params) {
			path = path.path(param);
		}

		LOGGER.debug("http request = " + path.getUri().toString());

		return path.request();
	}

	private void validateResponse(final Response response) throws ServerException {

		if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			final String errorMessage = response.readEntity(String.class);
			final ServerException errorException = new ServerException("Exception happened in Server Side : "
					+ errorMessage);

			throw errorException;
		}
	}

	protected abstract WebTarget prefixPath(WebTarget _target);
}