package org.komea.connectors.sdk.rest;

import java.io.Closeable;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.rmi.ServerException;

import javax.ws.rs.core.GenericType;

/**
 * Komea. interface to connect server rest api ant test the connection
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 15 janv. 2014
 */
public interface IRestClientAPI extends Closeable {

	/**
	 * @param _url
	 * @param _params
	 * @throws ConnectException
	 * @throws ServerException
	 */
	void delete(String _url, String[] _params) throws ConnectException,
	ServerException;

	/**
	 * This method send a a http GET request to the server
	 *
	 * @param _url
	 *            the http relative url
	 * @param _returnType
	 *            the return type of the http response content
	 * @return the http response content
	 * @throws ConnectException
	 *             launch if it can't connect to the server
	 * @throws ServerException
	 *             launch if exception happened in server side
	 */
	<R> R get(String _url, Class<R> _returnType) throws ConnectException,
			ServerException;

	/**
	 * This method send a a http GET request to the server
	 *
	 * @param _url
	 *            the http relative url
	 * @param _returnType
	 *            the return type of the http response content useful for
	 *            element list (new GenericType<List<<Person>>(){} for example)
	 * @param _params
	 *            the url parameters mist
	 * @return the http response content
	 * @throws ConnectException
	 *             launch if it can't connect to the server
	 * @throws ServerException
	 *             launch if exception happened in server side
	 */
	<R> R get(String _url, Class<R> _returnType, String... _params)
			throws ConnectException, ServerException;

	/**
	 * This method send a a http GET request to the server
	 *
	 * @param _url
	 *            the http relative url
	 * @param _returnType
	 *            the return type of the http response content useful for
	 *            element list (new GenericType<List<<Person>>(){} for example)
	 * @param _params
	 *            the url parameters mist
	 * @return the http response content
	 * @throws ConnectException
	 *             launch if it can't connect to the server
	 * @throws ServerException
	 *             launch if exception happened in server side
	 */
	<R> R get(String _url, GenericType<R> _returnType, String... _params)
			throws ConnectException, ServerException;

	/**
	 * This method send a a http GET request to the server
	 *
	 * @param _url
	 *            the http relative url
	 * @param _params
	 *            the url parameters mist
	 * @throws ConnectException
	 *             launch if it can't connect to the server
	 * @throws ServerException
	 *             launch if exception happened in server side
	 */
	<T> void get(String _url, String... params) throws ConnectException,
			ServerException;

	/**
	 * This method send a a http POST request to the server
	 *
	 * @param _url
	 *            the http relative url
	 * @param _objectToSend
	 *            the object to send to the server
	 * @throws ConnectException
	 *             ConnectException launch if it can't connect to the server
	 * @throws ServerException
	 *             launch if exception happened in server side
	 */
	<T> void post(String _url, T _objectToSend) throws ConnectException,
			ServerException;

	/**
	 * This method send a a http POST request to the server
	 *
	 * @param _url
	 *            the http relative url
	 * @param _objectToSend
	 *            he object to send to the server
	 * @param _returnType
	 *            the return type of the http response content
	 * @return the http response content
	 * @throws ConnectException
	 *             launch if it can't connect to the server
	 * @throws ServerException
	 *             launch if exception happened in server side
	 */
	<T, R> R post(String _url, T _objectToSend, Class<R> _returnType)
			throws ConnectException, ServerException;

	/**
	 * This method send a a http POST request to the server
	 *
	 * @param _url
	 *            the http relative url
	 * @param _objectToSend
	 *            he object to send to the server
	 * @param _returnType
	 *            the return type of the http response content useful for
	 *            element list (new GenericType<List<<Person>>(){} for example)
	 * @param _params
	 *            the url parameters mist
	 * @return the http response content
	 * @throws ConnectException
	 *             launch if it can't connect to the server
	 * @throws ServerException
	 * @throws ServerException
	 *             launch if exception happened in server side
	 */
	<T, R> R post(String _url, T _objectToSend, GenericType<R> _returnType)
			throws ConnectException, ServerException;

	/**
	 * This method set the server base url (http://localhost:8080/komea for
	 * example
	 *
	 * @param _serverURL
	 *            the server base url
	 * @throws URISyntaxException
	 *             launch if the url is not a valid syntax url
	 * @throws ConnectException
	 *             launch if we can't connect to the server
	 */
	void setServerBaseURL(final String _serverURL) throws URISyntaxException,
			ConnectException;

	/**
	 * This method test if the connection id valid
	 *
	 * @return true if the connection is valid, false otherwise
	 */
	boolean testConnectionValid();

}
