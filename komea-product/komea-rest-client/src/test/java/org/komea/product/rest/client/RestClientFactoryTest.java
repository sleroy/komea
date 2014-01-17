package org.komea.product.rest.client;

import java.net.ConnectException;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.Test;

public class RestClientFactoryTest {

    @Before
    public void setUp() throws Exception {

    }
    //

    @Test
    public void testCreateProvidersAPI() throws ConnectException, URISyntaxException {

        RestClientFactory.INSTANCE.createProvidersAPI("http://artemis:8080/komea");
    }

    @Test
    public void testCreateEventsAPI() throws ConnectException, URISyntaxException {

        RestClientFactory.INSTANCE.createEventsAPI("http://artemis:8080/komea");
    }
}
