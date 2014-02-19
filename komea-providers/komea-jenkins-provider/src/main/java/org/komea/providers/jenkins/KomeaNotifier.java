package org.komea.providers.jenkins;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IEventsAPI;

public class KomeaNotifier extends Notifier implements Serializable {

    private String projectKey = null;
    private String branch = "";

    @DataBoundConstructor
    public KomeaNotifier(final String projectKey, final String branch) {

        this.projectKey = projectKey;
        if (this.projectKey != null && this.projectKey.trim().isEmpty()) {
            this.projectKey = null;
        }
        this.branch = branch;
    }

    private String getServerUrl() {
        String serverUrl = getDescriptor().getServerUrl();
        if (serverUrl != null && serverUrl.trim().isEmpty()) {
            serverUrl = null;
        }
        return serverUrl;
    }

    public String getProjectKey() {

        return projectKey;
    }

    public String getBranch() {

        return branch;
    }

    @Override
    public boolean needsToRunAfterFinalized() {

        return true;
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener)
            throws InterruptedException, IOException {
        if (getServerUrl() == null) {
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
        final EventSimpleDto event = createResultEvent(start, end, buildNumber,
                result, jenkinsProjectName, provider.getUrl());
        if (event != null) {
            pushEvents(listener, event);
        }
        return true;
    }

    private void pushEvents(final BuildListener listener, final EventSimpleDto... events) {
        final String serverUrl = getServerUrl();
        if (serverUrl == null) {
            return;
        }
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            final IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI(serverUrl);
            for (final EventSimpleDto event : events) {
                eventsAPI.pushEvent(event);
            }
        } catch (Exception ex) {
            ex.printStackTrace(listener.getLogger());
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }

    @Override
    public boolean prebuild(final AbstractBuild<?, ?> build, final BuildListener listener) {
        final String jenkinsProjectName = build.getProject().getName();
        if (projectKey == null) {
            projectKey = jenkinsProjectName;
        }
        final Provider provider = KomeaComputerListener.getProvider();
        final int buildNumber = build.getNumber();
        final long buildDate = build.getTime().getTime();
        pushEvents(listener, createStartEvent(
                buildDate, buildNumber, jenkinsProjectName, provider.getUrl()));
        return true;
    }

    private EventSimpleDto createStartEvent(final long start, final int buildNumber,
            final String jenkinsProjectName, final String providerUrl) {
        final String message = "Jenkins build started for project " + projectKey;
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
        event.setValue(event.getDate().getTime());
        return event;
    }

    private EventSimpleDto createResultEvent(final long start, final long end,
            final int buildNumber, final Result result, final String jenkinsProjectName,
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

    @Override
    public BuildStepMonitor getRequiredMonitorService() {

        return BuildStepMonitor.NONE;
    }

    @Override
    public DescriptorImpl getDescriptor() {

        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        public DescriptorImpl() {

            load();
        }

        @Override
        public String getDisplayName() {

            return "Komea Notifier";
        }

        private String serverUrl;

        private int getResponseCode(final URL u) throws IOException {

            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("HEAD");
            return huc.getResponseCode();
        }

        public FormValidation doCheckServerUrl(@QueryParameter final String value) throws IOException, ServletException {

            if (!value.isEmpty()) {
                try {
                    final URL url = new URL(value);
                    if (getResponseCode(url) != 200) {
                        return FormValidation.warning("Url not accessible.");
                    }
                } catch (MalformedURLException ex) {
                    return FormValidation.error("Url not valid : " + ex.getMessage());
                } catch (IOException ex) {
                    return FormValidation.error("Url not valid : " + ex.getMessage());
                }
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(final Class<? extends AbstractProject> aClass) {

            return true;
        }

        @Override
        public boolean configure(final StaplerRequest req, final JSONObject formData) throws FormException {

            serverUrl = formData.getString("serverUrl");
            save();
            return super.configure(req, formData);
        }

        public String getServerUrl() {

            return serverUrl;
        }

    }

}
