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
import jenkins.model.JenkinsLocationConfiguration;
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

    public static final EventType BUILD_STARTED = createEventType(
            "build_started", "Jenkins build started",
            "", Severity.INFO);
    public static final EventType BUILD_INDUSTRIALIZATION = createEventType(
            "build_industrialization", "Jenkins build industrialization",
            "", Severity.INFO);
    public static final EventType BUILD_COMPLETE = createEventType(
            "build_complete", "Jenkins build complete", "",
            Severity.INFO);
    public static final EventType BUILD_FAILED = createEventType(
            "build_failed", "Jenkins build failed",
            "", Severity.BLOCKER);
    public static final EventType BUILD_UNSTABLE = createEventType(
            "build_unstable", "Jenkins build unstable",
            "", Severity.CRITICAL);
    public static final EventType BUILD_INTERRUPTED = createEventType(
            "build_interrupted", "Jenkins build interrupted",
            "", Severity.MAJOR);
    public static final List<EventType> EVENT_TYPES = Arrays.asList(
            BUILD_STARTED, BUILD_INDUSTRIALIZATION, BUILD_COMPLETE,
            BUILD_FAILED, BUILD_INTERRUPTED, BUILD_UNSTABLE);

    public static EventType createEventType(final String key, final String name,
            final String description, final Severity severity) {
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

    public static String getProjectUrl(final String projectName, final int buildNumber) {
        return getJenkinsUrl() + "/job/" + projectName + "/" + buildNumber;
    }

    public static Provider getProvider() {
        final String jenkinsUrl = getJenkinsUrl();
        final Provider provider = new Provider();
        provider.setProviderType(ProviderType.CI_BUILD);
        provider.setName("Jenkins");
        provider.setUrl(jenkinsUrl);
        provider.setIcon(jenkinsUrl + Jenkins.RESOURCE_PATH
                + "/plugin/komea-jenkins-provider/images/jenkins_icon.png");
        return provider;
    }

    private static String getJenkinsUrl() {
        final JenkinsLocationConfiguration globalConfig = new JenkinsLocationConfiguration();
        String url = globalConfig.getUrl();
        if (url != null) {
            url = url.trim();
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            if (url.isEmpty()) {
                url = null;
            }
        }
        return url;
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

        final Provider provider = getProvider();
        providerDto.setProvider(provider);

        final List<EventType> eventTypes = new ArrayList<EventType>(EVENT_TYPES);
        providerDto.setEventTypes(eventTypes);

        registerProvider(serverUrlProperty, providerDto, listener);
    }

    public static void registerProvider(final String serverUrl, final ProviderDto provider,
            final TaskListener listener) {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(KomeaComputerListener.class.getClassLoader());
            final IProvidersAPI providersAPI = RestClientFactory.INSTANCE.createProvidersAPI(serverUrl);
            providersAPI.registerProvider(provider);
        } catch (Exception ex) {
            ex.printStackTrace(listener.getLogger());
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }
}
