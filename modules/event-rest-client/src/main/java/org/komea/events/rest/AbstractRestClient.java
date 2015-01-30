package org.komea.events.rest;

import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestClient {

    private RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public AbstractRestClient(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected void delete(final String methodName) {
        final String url = getUrl(methodName);
        restTemplate.delete(url);
    }

    protected <T> T post(final String methodName, final Object object, final Class<T> clazz) {
        final String url = getUrl(methodName);
        return restTemplate.postForObject(url, object, clazz);
    }

    protected <T> T get(final String methodName, final Class<T> clazz) {
        final String url = getUrl(methodName);
        return restTemplate.getForObject(url, clazz);
    }

    private String getUrl(final String methodName) {
        return baseUrl + "/" + methodName;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
