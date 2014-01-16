
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.komea.product.rest.client.api.IRestClientAPI;

abstract class AbstractRestCientAPI implements IRestClientAPI
{
    
    // private URI serverURI;
    private final Client client;
    private WebTarget    target;
    
    public AbstractRestCientAPI() {
    
        client = ClientBuilder.newClient();
    }
    
    @Override
    public void setServerBaseURL(final String _serverURL) throws URISyntaxException, ConnectException {
    
        URI serverURI = new URI(_serverURL);
        target = client.target(serverURI);
        if (!testConnectionValid()) {
            target = null;
            throw new ConnectException(MessageFormat.format("can't connect to the server {}", _serverURL));
        }
        
    }
    
    @Override
    public boolean testConnectionValid() {
    
        return target != null && target.request().get().getStatus() == 200;
        
    }
    
    public WebTarget getTarget() {
    
        return target;
    }
    
    public void setTarget(final WebTarget _target) {
    
        target = _target;
    }
    
    public Client getClient() {
    
        return client;
    }
    
    //
}
