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
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.komea.product.database.dto.EventDto;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IEventsAPI;

public class KomeaNotifier extends Notifier implements Serializable {

    private String projectKey = null;
    private String branch = "";
    private final Provider provider;

    @DataBoundConstructor
    public KomeaNotifier(String projectKey, String branch) {
        this.projectKey = projectKey;
        if (this.projectKey != null && this.projectKey.trim().isEmpty()) {
            this.projectKey = null;
        }
        this.branch = branch;
        final String jenkinsUrl = Jenkins.getInstance().getRootUrl();
        this.provider = KomeaComputerListener.getProvider(jenkinsUrl);
    }

    private String getServerUrl() {
        String serverUrl = this.getDescriptor().getServerUrl();
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
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
            BuildListener listener) throws InterruptedException, IOException {
        try {
            if (getServerUrl() == null) {
                return true;
            }
            if (projectKey == null) {
                projectKey = build.getProject().getName();
            }
            final Result result = build.getResult();
            final int buildNumber = build.getNumber();
            final long start = build.getTime().getTime();
            final long end = new Date().getTime();
            final String serverUrl = getServerUrl();
            final IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI(serverUrl);
            eventsAPI.pushEvent(createEndEvent(end, buildNumber));
            eventsAPI.pushEvent(createDurationEvent(start, end, buildNumber));
            eventsAPI.pushEvent(createResultEvent(end, buildNumber, result));
        } catch (Exception ex) {
            ex.printStackTrace(listener.getLogger());
        }
        return true;
    }

    @Override
    public boolean prebuild(AbstractBuild<?, ?> build, BuildListener listener) {
        try {
            final String serverUrl = getServerUrl();
            if (serverUrl == null) {
                return true;
            }
            if (projectKey == null) {
                projectKey = build.getProject().getName();
            }
            final int buildNumber = build.getNumber();
            final long buildDate = build.getTime().getTime();
            final IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI(serverUrl);
            eventsAPI.pushEvent(createStartEvent(buildDate, buildNumber));
        } catch (Exception ex) {
            ex.printStackTrace(listener.getLogger());
        }
        return true;
    }

    private EventDto createStartEvent(final long start, final int buildNumber) {
        final String message = "Build of " + projectKey + " started.";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        return new EventDto(KomeaComputerListener.EVENT_BUILD_STARTED, provider,
                message, properties, projectKey, new Date());
    }

    private EventDto createDurationEvent(final long start, final long end, final int buildNumber) {
        final long duration = end - start;
        final String message = "Build of " + projectKey + " done in : " + duration + "ms";
        final Map<String, String> properties = new HashMap<String, String>(3);
        properties.put("start", String.valueOf(start));
        properties.put("end", String.valueOf(end));
        properties.put("duration", String.valueOf(duration));
        properties.put("project", projectKey);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        return new EventDto(KomeaComputerListener.EVENT_BUILD_DURATION, provider,
                message, properties, projectKey, new Date());
    }

    private EventDto createResultEvent(final long end, final int buildNumber, final Result result) {
        final String message = "Build of " + projectKey + " ended.";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(end));
        properties.put("project", projectKey);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("result", result.toString());
        final Severity severity;
        if (Result.ABORTED.equals(result)) {
            severity = Severity.CRITICAL;
        } else if (Result.FAILURE.equals(result)) {
            severity = Severity.BLOCKER;
        } else if (Result.NOT_BUILT.equals(result)) {
            severity = Severity.CRITICAL;
        } else if (Result.UNSTABLE.equals(result)) {
            severity = Severity.CRITICAL;
        } else {
            severity = Severity.INFO;
        }
        final EventDto eventDto = new EventDto(KomeaComputerListener.EVENT_BUILD_RESULT,
                provider, message, properties, projectKey, new Date());
        eventDto.getEventType().setSeverity(severity);
        return eventDto;
    }

    private EventDto createEndEvent(final long end, final int buildNumber) {
        final String message = "Build of " + projectKey + " ended.";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(end));
        properties.put("project", projectKey);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        return new EventDto(KomeaComputerListener.EVENT_BUILD_ENDED, provider,
                message, properties, projectKey, new Date());
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

        private int getResponseCode(URL u) throws IOException {
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("HEAD");
            return huc.getResponseCode();
        }

        public FormValidation doCheckServerUrl(@QueryParameter String value)
                throws IOException, ServletException {
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

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            serverUrl = formData.getString("serverUrl");
            save();
            return super.configure(req, formData);
        }

        public String getServerUrl() {
            return serverUrl;
        }

    }

}
