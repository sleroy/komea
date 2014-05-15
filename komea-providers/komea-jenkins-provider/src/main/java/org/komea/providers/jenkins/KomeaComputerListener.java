package org.komea.providers.jenkins;

import hudson.Extension;
import hudson.model.Computer;
import hudson.model.TaskListener;
import hudson.slaves.ComputerListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jenkins.model.Jenkins;
import jenkins.model.JenkinsLocationConfiguration;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IProvidersAPI;

@Extension
public class KomeaComputerListener extends ComputerListener implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(KomeaComputerListener.class.getName());
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
    public static final EventType BUILD_CODE_CHANGED = createEventType(
            "build_code_changed", "Jenkins build with code changes",
            "", Severity.INFO, EntityType.PERSON);
    public static final EventType BUILD_FIXED = createEventType(
            "build_fixed", "Jenkins build fixed",
            "", Severity.INFO, EntityType.PERSON);
    public static final EventType BUILD_BROKEN = createEventType(
            "build_broken", "Jenkins build broken",
            "", Severity.CRITICAL, EntityType.PERSON);
    public static final EventType JOB_CONFIGURATION_CHANGED = createEventType(
            "job_configuration_changed", "Jenkins job configuration changed",
            "", Severity.CRITICAL);
    public static final EventType BUILD_STARTED_BY_USER = createEventType(
            "build_started_by_user", "Jenkins build started by user",
            "", Severity.INFO, EntityType.PERSON);
    public static final String JENKINS_TEST_KEY = "jenkins_test";
    public static final EventType JENKINS_TEST = createEventType(
            JENKINS_TEST_KEY, "Event to test Jenkins",
            "", Severity.INFO, EntityType.PROJECT);
    public static final List<EventType> EVENT_TYPES = Arrays.asList(
            BUILD_STARTED, BUILD_INDUSTRIALIZATION, BUILD_COMPLETE,
            BUILD_FAILED, BUILD_INTERRUPTED, BUILD_UNSTABLE,
            BUILD_CODE_CHANGED, BUILD_BROKEN, BUILD_FIXED, JOB_CONFIGURATION_CHANGED,
            BUILD_STARTED_BY_USER, JENKINS_TEST);

    public static EventType createEventType(final String key, final String name,
            final String description, final Severity severity, final EntityType entityType) {
        final EventType eventType = new EventType();
        eventType.setProviderType(ProviderType.CI_BUILD);
        eventType.setDescription(description.isEmpty() ? name : description);
        eventType.setEnabled(true);
        eventType.setEntityType(entityType);
        eventType.setEventKey(key);
        eventType.setName(name);
        eventType.setSeverity(severity);
        return eventType;
    }

    public static EventType createEventType(final String key, final String name,
            final String description, final Severity severity) {
        return createEventType(key, name, description, severity, EntityType.PROJECT);
    }

    public static String getProjectConfigurationUrl(final String projectName) {
        return getJenkinsUrl() + "/job/" + projectName + "/configure";
    }

    public static String getProjectBuildUrl(final String projectName, final int buildNumber) {
        return getJenkinsUrl() + "/job/" + projectName + "/" + buildNumber;
    }

    public static ProviderDto getProviderDto() {
        return getProviderDto(getJenkinsUrl());
    }

    private static ProviderDto getProviderDto(final String jenkinsUrl) {
        final ProviderDto providerDto = new ProviderDto();
        final Provider provider = getProvider(jenkinsUrl);
        providerDto.setProvider(provider);
        providerDto.setEventTypes(KomeaComputerListener.EVENT_TYPES);
        return providerDto;
    }

    public static Provider getProvider() {
        return getProvider(getJenkinsUrl());
    }

    private static Provider getProvider(final String jenkinsUrl) {
        final Provider provider = new Provider();
        provider.setProviderType(ProviderType.CI_BUILD);
        provider.setName("Jenkins");
        provider.setUrl(jenkinsUrl);
        provider.setIcon(jenkinsUrl + Jenkins.RESOURCE_PATH
                + "/plugin/komea-jenkins-provider/images/jenkins_icon.png");
        return provider;
    }

    public static String getJenkinsUrl() {
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
        try {
            final Jenkins jenkins = Jenkins.getInstance();
            final KomeaNotifier.DescriptorImpl descriptor
                    = jenkins.getDescriptorByType(KomeaNotifier.DescriptorImpl.class);
            final String serverUrlProperty = descriptor.getServerUrl();
            LOGGER.log(Level.FINE, "onOnline - Komea server url : {0}", serverUrlProperty);
            if (serverUrlProperty == null || serverUrlProperty.trim().isEmpty()) {
                return;
            }
            final ProviderDto providerDto = getProviderDto();
            registerProvider(serverUrlProperty, providerDto);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace(listener.getLogger());
        }
    }

    public static void registerProvider(final String serverUrl, final ProviderDto provider) throws Exception {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(KomeaComputerListener.class.getClassLoader());
            final IProvidersAPI providersAPI = RestClientFactory.INSTANCE.createProvidersAPI(serverUrl);
            LOGGER.log(Level.FINE, "Register Provider : {0} ({1})",
                    new Object[]{provider.getProvider().getName(), provider.getProvider().getUrl()});
            providersAPI.registerProvider(provider);
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }
}
