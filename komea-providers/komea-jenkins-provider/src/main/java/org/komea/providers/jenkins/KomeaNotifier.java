package org.komea.providers.jenkins;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Cause;
import hudson.model.Result;
import hudson.model.User;
import hudson.scm.ChangeLogSet;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.BuildIndustrialization;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IEventsAPI;

public class KomeaNotifier extends Notifier implements Serializable {

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        private static final Logger LOGGER = Logger.getLogger(DescriptorImpl.class.getName());
        private String serverUrl;

        public DescriptorImpl() {

            load();
        }

        @Override
        public boolean configure(final StaplerRequest req, final JSONObject formData)
                throws FormException {
            LOGGER.log(Level.WARNING, "CONFIGURATION CHANGES : "
                    + (req.getUserPrincipal() == null ? "" : req.getUserPrincipal().getName()) + " - "
                    + req.getRequestURIWithQueryString());

            serverUrl = formData.getString("serverUrl");
            save();
            return super.configure(req, formData);
        }

        public FormValidation doCheckServerUrl(@QueryParameter
                final String value) {

            if (!value.isEmpty()) {
                try {
                    final URL url = new URL(value);
                    if (getResponseCode(url) != 200) {
                        return FormValidation
                                .warning("Url not accessible.");
                    }
                } catch (final MalformedURLException ex) {
                    return FormValidation.error("Url not valid : " + ex.getMessage());
                } catch (final IOException ex) {
                    return FormValidation.error("Url not valid : " + ex.getMessage());
                }
            }
            return FormValidation.ok();
        }

        @Override
        public String getDisplayName() {

            return "Komea Notifier";
        }

        public String getServerUrl() {

            return serverUrl;
        }

        @Override
        public boolean isApplicable(final Class<? extends AbstractProject> aClass) {

            return true;
        }

        private int getResponseCode(final URL u) throws IOException {

            final HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("HEAD");
            return huc.getResponseCode();
        }

        public ListBoxModel doFillIndustrializationItems() {
            ListBoxModel items = new ListBoxModel();
            for (final BuildIndustrialization indus : BuildIndustrialization.values()) {
                items.add(enumNameToDisplayName(indus.name()), indus.name());
            }
            return items;
        }

    }

    private String branch = "";

    private String projectKey = null;

    private String industrialization = BuildIndustrialization.UNKNOWN.name();

    private static String enumNameToDisplayName(final String enumName) {
        return enumName.charAt(0) + enumName.substring(1).replace("_", " ").toLowerCase();
    }

    @DataBoundConstructor
    public KomeaNotifier(final String projectKey, final String branch, final String industrialization) {

        this.projectKey = projectKey;
        if (this.projectKey != null && this.projectKey.trim().isEmpty()) {
            this.projectKey = null;
        }
        this.industrialization = industrialization;
        this.branch = branch;
    }

    public String getBranch() {

        return branch;
    }

    public String getIndustrialization() {
        return industrialization;
    }

    @Override
    public DescriptorImpl getDescriptor() {

        return (DescriptorImpl) super.getDescriptor();
    }

    public String getProjectKey() {

        return projectKey;
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {

        return BuildStepMonitor.NONE;
    }

    @Override
    public boolean needsToRunAfterFinalized() {

        return true;
    }

    @Override
    public boolean perform(
            final AbstractBuild<?, ?> build,
            final Launcher launcher,
            final BuildListener listener) throws InterruptedException, IOException {

        if (getServerUrl() == null || !build.equals(build.getRootBuild())) {
            return true;
        }
        final String jenkinsProjectName = build.getProject().getName();
        if (projectKey == null) {
            projectKey = jenkinsProjectName;
        }
        final Result result = build.getResult();
        final int buildNumber = build.getNumber();
        final long start = build.getTime().getTime();
        final long end = new Date().getTime();
        final Provider provider = KomeaComputerListener.getProvider();
        final EventSimpleDto event = createResultEvent(start, end,
                buildNumber, result, jenkinsProjectName, provider.getUrl());
        if (event != null) {
            pushEvents(listener, event);
        }
        return true;
    }

    @Override
    public boolean prebuild(final AbstractBuild<?, ?> build, final BuildListener listener) {
        if (getServerUrl() == null || !build.equals(build.getRootBuild())) {
            return true;
        }
        final String jenkinsProjectName = build.getProject().getName();
        if (projectKey == null) {
            projectKey = jenkinsProjectName;
        }
        final Provider provider = KomeaComputerListener.getProvider();
        final int buildNumber = build.getNumber();
        final long buildDate = build.getTime().getTime();
        for (Cause cause : build.getCauses()) {
            listener.getLogger().println("CAUSE : " + cause.getShortDescription() + " - " + cause.getClass().getName());
        }
        Iterator<? extends ChangeLogSet.Entry> iterator = build.getChangeSet().iterator();
        while (iterator.hasNext()) {
            ChangeLogSet.Entry entry = iterator.next();
            listener.getLogger().println("CHANGE : " + entry.getAuthor().getDisplayName() + " - " + entry.getMsg());
        }
        for (final User user : build.getCulprits()) {
            listener.getLogger().println("USER : " + user.getId() + " - " + user.getDisplayName() + " - " + user.getFullName());
        }
        pushEvents(listener, createStartEvent(buildDate, buildNumber, jenkinsProjectName, provider.getUrl()),
                createIndustrializationEvent(buildDate, buildNumber, jenkinsProjectName, provider.getUrl()));
        return true;
    }

    private EventSimpleDto createResultEvent(
            final long start,
            final long end,
            final int buildNumber,
            final Result result,
            final String jenkinsProjectName,
            final String providerUrl) {

        final long duration = end - start;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(end));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("duration", String.valueOf(duration));
        properties.put("result", result.toString());
        final EventSimpleDto event = new EventSimpleDto();
        if (Result.ABORTED.equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_INTERRUPTED.getEventKey());
            event.setMessage("Jenkins build interrupted for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if (Result.FAILURE.equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_FAILED.getEventKey());
            event.setMessage("Jenkins build failed for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if (Result.SUCCESS.equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_COMPLETE.getEventKey());
            event.setMessage("Jenkins build performed for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if (Result.UNSTABLE.equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_UNSTABLE.getEventKey());
            event.setMessage("Jenkins build is unstable for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else {
            return null;
        }
        event.setDate(new Date());
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(KomeaComputerListener.getProjectUrl(jenkinsProjectName, buildNumber));
        event.setValue(result.ordinal);
        return event;
    }

    private EventSimpleDto createIndustrializationEvent(
            final long start,
            final int buildNumber,
            final String jenkinsProjectName,
            final String providerUrl) {
        final BuildIndustrialization indus = BuildIndustrialization.valueOf(industrialization);
        final String message = "Jenkins build industrialization for project " + jenkinsProjectName
                + " is " + enumNameToDisplayName(indus.name());
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("industrialization", industrialization);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.BUILD_INDUSTRIALIZATION.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(KomeaComputerListener.getProjectUrl(jenkinsProjectName, buildNumber));
        event.setValue(indus.ordinal());
        return event;
    }

    private EventSimpleDto createStartEvent(
            final long start,
            final int buildNumber,
            final String jenkinsProjectName,
            final String providerUrl) {

        final String message = "Jenkins build started for project " + jenkinsProjectName;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.BUILD_STARTED.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(KomeaComputerListener.getProjectUrl(jenkinsProjectName, buildNumber));
        event.setValue(start);
        return event;
    }

    private String getServerUrl() {

        String serverUrl = getDescriptor().getServerUrl();
        if (serverUrl != null && serverUrl.trim().isEmpty()) {
            serverUrl = null;
        }
        return serverUrl;
    }

    private void pushEvents(final BuildListener listener, final EventSimpleDto... events) {

        final String komeaUrl = getServerUrl();
        if (komeaUrl == null) {
            return;
        }
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            final IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI(komeaUrl);
            for (int i = 0; i < events.length; i++) {
                final EventSimpleDto event = events[i];
                if (i == 0) {
                    try {
                        eventsAPI.pushEvent(event);
                    } catch (Exception ex) {
                        final ProviderDto providerDto = new ProviderDto();
                        final Provider provider = KomeaComputerListener.getProvider();
                        providerDto.setProvider(provider);
                        providerDto.setEventTypes(KomeaComputerListener.EVENT_TYPES);
                        KomeaComputerListener.registerProvider(komeaUrl, providerDto, listener);
                        eventsAPI.pushEvent(event);
                    }
                } else {
                    eventsAPI.pushEvent(event);
                }
            }
        } catch (final Exception ex) {
            ex.printStackTrace(listener.getLogger());
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }

}
