
package org.komea.providers.jenkins;



import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
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

import jenkins.model.JenkinsLocationConfiguration;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IEventsAPI;



public class KomeaNotifier extends Notifier implements Serializable
{
    
    
    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher>
    {
        
        
        private String serverUrl;
        
        
        
        public DescriptorImpl() {
        
        
            load();
        }
        
        
        @Override
        public boolean configure(final StaplerRequest req, final JSONObject formData)
                throws FormException {
        
        
            serverUrl = formData.getString("serverUrl");
            save();
            return super.configure(req, formData);
        }
        
        
        public FormValidation doCheckServerUrl(@QueryParameter
        final String value) {
        
        
            if (!value.isEmpty()) {
                try {
                    final URL url = new URL(value);
                    if (getResponseCode(url) != 200) { return FormValidation
                            .warning("Url not accessible."); }
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
        
    }
    
    
    
    private String         branch     = "";
    private String         projectKey = null;
    
    
    private final Provider provider;
    
    
    
    @DataBoundConstructor
    public KomeaNotifier(final String projectKey, final String branch) {
    
    
        this.projectKey = projectKey;
        if (this.projectKey != null && this.projectKey.trim().isEmpty()) {
            this.projectKey = null;
        }
        this.branch = branch;
        final JenkinsLocationConfiguration globalConfig = new JenkinsLocationConfiguration();
        provider = KomeaComputerListener.getProvider(globalConfig.getUrl());
    }
    
    
    public String getBranch() {
    
    
        return branch;
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
    
    
        if (getServerUrl() == null) { return true; }
        if (projectKey == null) {
            projectKey = build.getProject().getName();
        }
        final Result result = build.getResult();
        final int buildNumber = build.getNumber();
        final long start = build.getTime().getTime();
        final long end = new Date().getTime();
        pushEvents(listener, createEndEvent(end, buildNumber),
                createDurationEvent(start, end, buildNumber),
                createResultEvent(end, buildNumber, result));
        return true;
    }
    
    
    @Override
    public boolean prebuild(final AbstractBuild<?, ?> build, final BuildListener listener) {
    
    
        if (projectKey == null) {
            projectKey = build.getProject().getName();
        }
        final int buildNumber = build.getNumber();
        final long buildDate = build.getTime().getTime();
        pushEvents(listener, createStartEvent(buildDate, buildNumber));
        return true;
    }
    
    
    private EventSimpleDto createDurationEvent(
            final long start,
            final long end,
            final int buildNumber) {
    
    
        final long duration = end - start;
        final String message = "Build of " + projectKey + " done in : " + duration + "ms";
        final Map<String, String> properties = new HashMap<String, String>(3);
        properties.put("start", String.valueOf(start));
        properties.put("end", String.valueOf(end));
        properties.put("duration", String.valueOf(duration));
        properties.put("project", projectKey);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.EVENT_BUILD_DURATION.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(null);
        event.setValue(duration);
        return event;
    }
    
    
    private EventSimpleDto createEndEvent(final long end, final int buildNumber) {
    
    
        final String message = "Build of " + projectKey + " ended.";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(end));
        properties.put("project", projectKey);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.EVENT_BUILD_ENDED.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(null);
        event.setValue(event.getDate().getTime());
        return event;
    }
    
    
    private EventSimpleDto createResultEvent(
            final long end,
            final int buildNumber,
            final Result result) {
    
    
        final String message = "Build of " + projectKey + " ended.";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(end));
        properties.put("project", projectKey);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("result", result.toString());
        if (Result.ABORTED.equals(result)) {
        } else if (Result.FAILURE.equals(result)) {
        } else if (Result.NOT_BUILT.equals(result)) {
        } else if (Result.UNSTABLE.equals(result)) {
        } else {
        }
        // eventDto.getEventType().setSeverity(severity);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.EVENT_BUILD_RESULT.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(null);
        event.setValue(result.ordinal);
        return event;
    }
    
    
    private EventSimpleDto createStartEvent(final long start, final int buildNumber) {
    
    
        final String message = "Build of " + projectKey + " started.";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.EVENT_BUILD_STARTED.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(provider.getUrl());
        event.setValue(event.getDate().getTime());
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
    
    
        final String serverUrl = getServerUrl();
        if (serverUrl == null) { return; }
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            final IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI(serverUrl);
            for (final EventSimpleDto event : events) {
                eventsAPI.pushEvent(event);
            }
        } catch (final Exception ex) {
            listener.error(ex.getMessage(), ex);
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }
    
}
