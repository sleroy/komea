package org.komea.product.rest.client;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import org.komea.product.rest.client.api.IRestClientAPI;

abstract class AbstractRestCientAPI implements IRestClientAPI {

    // private URI serverURI;
    private final ResteasyClient client;
    private ResteasyWebTarget target;

    public AbstractRestCientAPI() {

        client = new ResteasyClientBuilder().build();
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

    public void setTarget(final ResteasyWebTarget _target) {

        target = _target;
    }

    public Client getClient() {

        return client;
    }

    //
}
