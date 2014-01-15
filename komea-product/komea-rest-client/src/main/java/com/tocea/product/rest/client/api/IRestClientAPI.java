
package com.tocea.product.rest.client.api;


import java.net.ConnectException;
import java.net.URISyntaxException;

/**
 * Komea.
 * interface to connect server rest api ant test the connection
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 15 janv. 2014
 */
public interface IRestClientAPI
{
    
    /**
     * This method set the server base url (http://localhost:8080/komea for example
     * 
     * @param _serverURL
     *            the server base url
     * @throws URISyntaxException
     *             launch if the url is not a valid syntax url
     * @throws ConnectException
     *             launch if we can't connect to the server
     */
    void setServerBaseURL(final String _serverURL) throws URISyntaxException, ConnectException;
    
    /**
     * This method test if the connection id valid
     * 
     * @return true if the connection is valid, false otherwise
     */
    boolean testConnectionValid();
}
