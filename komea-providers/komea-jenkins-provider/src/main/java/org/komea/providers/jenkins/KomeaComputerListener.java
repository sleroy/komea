package org.komea.providers.jenkins;

import hudson.Extension;
import hudson.model.Computer;
import hudson.model.TaskListener;
import hudson.slaves.ComputerListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jenkins.model.Jenkins;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EventCategory;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IProvidersAPI;

@Extension
public class KomeaComputerListener extends ComputerListener implements Serializable {

    public static final String EVENT_BUILD_STARTED_KEY = "JENKINS_BUILD_STARTED";
    public static final EventType EVENT_BUILD_STARTED = createEventType(
            EVENT_BUILD_STARTED_KEY,
            "Jenkins build started",
            "", Severity.INFO);
    public static final String EVENT_BUILD_ENDED_KEY = "JENKINS_BUILD_ENDED";
    public static final EventType EVENT_BUILD_ENDED = createEventType(
            EVENT_BUILD_ENDED_KEY,
            "Jenkins build ended", "",
            Severity.INFO);
    public static final String EVENT_BUILD_DURATION_KEY = "JENKINS_BUILD_DURATION";
    public static final EventType EVENT_BUILD_DURATION = createEventType(
            EVENT_BUILD_DURATION_KEY,
            "Jenkins build duration",
            "", Severity.INFO);
    public static final String EVENT_BUILD_RESULT_KEY = "JENKINS_BUILD_RESULT";
    public static final EventType EVENT_BUILD_RESULT = createEventType(
            EVENT_BUILD_RESULT_KEY,
            "Jenkins build result",
            "", Severity.INFO);
    public static final List<EventType> EVENT_TYPES = Arrays.asList(
            EVENT_BUILD_STARTED,
            EVENT_BUILD_ENDED,
            EVENT_BUILD_DURATION,
            EVENT_BUILD_RESULT);

    public static EventType createEventType(
            final String key,
            final String name,
            final String description,
            final Severity severity) {

        final EventType eventType = new EventType();
        eventType.setCategory(EventCategory.BUILD.name());
        eventType.setDescription(description);
        eventType.setEnabled(true);
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setEventKey(key);
        eventType.setName(name);
        eventType.setSeverity(severity);
        return eventType;
    }

    public static Provider getProvider(final String serverUrl) {

        final Provider provider = new Provider();
        provider.setProviderType(ProviderType.CI_BUILD);
        provider.setName("Jenkins");
        provider.setUrl(serverUrl);
        provider.setIcon(serverUrl + "/static/komea/jenkins_logo.png");
        return provider;
    }

    @Override
    public void onOnline(final Computer c, final TaskListener listener)
            throws IOException, InterruptedException {

        final Jenkins jenkins = Jenkins.getInstance();
        final KomeaNotifier.DescriptorImpl descriptor
                = jenkins.getDescriptorByType(KomeaNotifier.DescriptorImpl.class);
        final String serverUrlProperty = descriptor.getServerUrl();
        if (serverUrlProperty == null || serverUrlProperty.trim().isEmpty()) {
            return;
        }
        final ProviderDto providerDto = new ProviderDto();

        final Provider provider = getProvider(c.getUrl());
        providerDto.setProvider(provider);

        final List<EventType> eventTypes = new ArrayList<EventType>(EVENT_TYPES);
        providerDto.setEventTypes(eventTypes);

        registerProvider(serverUrlProperty, providerDto, listener);
    }

    private void registerProvider(final String serverUrl, final ProviderDto provider,
            final TaskListener listener) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            final IProvidersAPI providersAPI = RestClientFactory.INSTANCE.createProvidersAPI(serverUrl);
            providersAPI.registerProvider(provider);
        } catch (Exception ex) {
            listener.error(ex.getMessage(), ex);
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }
}
