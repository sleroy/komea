package org.komea.modules.rest.client;

import java.util.Map;
import org.komea.event.queries.executor.EventsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class QueriesRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueriesRestClient.class.getName());
    private static final String BASE_REST_URL = "/rest/queries";

    private RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public QueriesRestClient(final String host) {
        this.baseUrl = host + BASE_REST_URL;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Number> execute(final EventsQuery eventsQuery) {
        LOGGER.info("QueriesRestClient - execute : " + eventsQuery);
        return post("execute", eventsQuery, Map.class);
    }

    private <T> T post(final String methodName, final Object object, final Class<T> clazz) {
        final String url = getUrl(methodName);
        return restTemplate.postForObject(url, object, clazz);
    }

    private String getUrl(final String methodName) {
        return baseUrl + "/" + methodName;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
