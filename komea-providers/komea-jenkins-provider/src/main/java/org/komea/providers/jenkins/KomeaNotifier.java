package org.komea.providers.jenkins;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Cause;
import hudson.model.Cause.UserIdCause;
import hudson.model.Result;
import hudson.scm.ChangeLogSet;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.triggers.SCMTrigger.SCMTriggerCause;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.BuildIndustrialization;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IEventsAPI;

public class KomeaNotifier extends Notifier implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(KomeaNotifier.class.getName());

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

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
                    if (getResponseCode(url) != 200) {
                        return FormValidation.warning("Url not accessible.");
                    }
                } catch (final MalformedURLException ex) {
                    return FormValidation.error("Url not valid : " + getCauseMessage(ex));
                } catch (final IOException ex) {
                    return FormValidation.error("Url not valid : " + getCauseMessage(ex));
                }
            }
            return FormValidation.ok();
        }

        public FormValidation doTestConnection(@QueryParameter("serverUrl") final String serverUrl)
                throws IOException, ServletException {
            try {
                final ProviderDto provider = KomeaComputerListener.getProviderDto();
                KomeaComputerListener.registerProvider(serverUrl, provider);
                return FormValidation.ok("Success : Jenkins provider registered in Komea.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                return FormValidation.error(getCauseMessage(e));
            }
        }

        public FormValidation doTestEvents(@QueryParameter("projectKey") final String projectKey)
                throws IOException, ServletException {
            try {
                final String komeaUrl = getKomeaUrl();
                if (komeaUrl == null) {
                    return FormValidation.error("Komea server url is not set. Please set it in the page "
                            + KomeaComputerListener.getJenkinsUrl() + "/configure");
                }
                String key = projectKey;
                if (key.trim().isEmpty()) {
                    final AbstractProject project
                            = Stapler.getCurrentRequest().findAncestorObject(AbstractProject.class);
                    key = project.getName();
                }
                final EventSimpleDto event = getTestEvent(key);
                pushEvents(komeaUrl, Arrays.asList(event));
                return FormValidation.ok("Success : Jenkins event send at "
                        + event.getDate().toGMTString() + " for project " + key);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                return FormValidation.error(getCauseMessage(e));
            }
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
                items.add(EventsBuilder.enumNameToDisplayName(indus.name()), indus.name());
            }
            return items;
        }

    }

    private String branch = "";

    private String projectKey = null;

    private String industrialization = BuildIndustrialization.UNKNOWN.name();

    @DataBoundConstructor
    public KomeaNotifier(final String projectKey, final String branch, final String industrialization) {

        this.projectKey = projectKey;
        if (this.projectKey != null && this.projectKey.trim().isEmpty()) {
            this.projectKey = null;
        }
        this.industrialization = industrialization;
        this.branch = branch;
    }

//            LOGGER.log(Level.WARNING, "CONFIGURATION CHANGES : "
//                    + (req.getUserPrincipal() == null ? "" : req.getUserPrincipal().getName()) + " - "
//                    + req.getRequestURIWithQueryString());
//            if (req.getUserPrincipal() != null) {
//            pushEvents(null, Arrays.asList(createJobConfigurationChanged(
//                    req.get, "", req.getUserPrincipal().getName(), pro, "")));
//            }
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

    private boolean buildFixed(final AbstractBuild<?, ?> build) {
        return Result.SUCCESS.equals(build.getResult())
                && build.getPreviousBuild() != null
                && !Result.SUCCESS.equals(build.getPreviousBuild().getResult());
    }

    private boolean buildBroken(final AbstractBuild<?, ?> build) {
//        return !Result.SUCCESS.equals(build.getResult())
//                && build.getPreviousBuild() != null
//                && Result.SUCCESS.equals(build.getPreviousBuild().getResult());
        return !Result.SUCCESS.equals(build.getResult());
    }

    public static String getCauseMessage(final Throwable ex) {
        final StringBuilder stringBuilder = new StringBuilder(50);
        Throwable exception = ex;
        while (exception != null) {
            stringBuilder.append(exception.getMessage()).append("\n");
            exception = exception.getCause();
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private static EventSimpleDto getTestEvent(final String projectKey) {
        final Provider provider = KomeaComputerListener.getProvider();
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.JENKINS_TEST_KEY);
        event.setMessage("Event of project " + projectKey
                + " of Jenkins provider with url=" + provider.getUrl());
        event.setProject(projectKey);
        event.setProvider(provider.getKey());
        event.setUrl(provider.getUrl());
        event.setValue(200);
        return event;
    }

    @Override
    public boolean perform(
            final AbstractBuild<?, ?> build,
            final Launcher launcher,
            final BuildListener listener) throws InterruptedException, IOException {
        try {
            final String komeaUrl = getKomeaUrl();
            if (komeaUrl == null || !build.equals(build.getRootBuild())) {
                return true;
            }
            final String jenkinsProjectName = build.getProject().getName();
            if (projectKey == null) {
                projectKey = jenkinsProjectName;
            }
            LOGGER.log(Level.FINE, "Build of project {0} performed", projectKey);
            final Result result = build.getResult();
            final int buildNumber = build.getNumber();
            final long start = build.getTime().getTime();
            final long end = new Date().getTime();
            final Provider provider = KomeaComputerListener.getProvider();
            final List<EventSimpleDto> events = new ArrayList<EventSimpleDto>(1);
            final EventSimpleDto resultEvent = EventsBuilder.createResultEvent(start, end,
                    buildNumber, result, jenkinsProjectName, provider.getUrl(), projectKey, branch);
            if (resultEvent != null) {
                events.add(resultEvent);
            }
            final Map<String, Integer> commiters = getCommiters(build);
            for (final String commiter : commiters.keySet()) {
                if (buildBroken(build)) {
                    events.add(EventsBuilder.createBuildBroken(start, buildNumber,
                            jenkinsProjectName, provider.getUrl(), commiter, projectKey, branch));
                } else if (buildFixed(build)) {
                    events.add(EventsBuilder.createBuildFixed(start, buildNumber,
                            jenkinsProjectName, provider.getUrl(), commiter, projectKey, branch));
                }
            }
            pushEvents(komeaUrl, events);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace(listener.getLogger());
        }
        return true;
    }

    private boolean isStartedByUserCommits(final AbstractBuild<?, ?> build) {
        for (Cause cause : build.getCauses()) {
            if (cause.getClass().equals(SCMTriggerCause.class)) {
                return true;
            }
        }
        return false;
    }

    private String getStartedByUser(final AbstractBuild<?, ?> build) {
        for (Cause cause : build.getCauses()) {
            if (cause.getClass().equals(Cause.UserIdCause.class)) {
                final UserIdCause userIdCause = (UserIdCause) cause;
                final String user = userIdCause.getUserId();
                return user;
            }
        }
        return null;
    }

    private Map<String, Integer> getCommiters(final AbstractBuild<?, ?> build) {
        final Iterator<? extends ChangeLogSet.Entry> iterator = build.getChangeSet().iterator();
        final Map<String, Integer> commiters = new HashMap<String, Integer>(0);
        while (iterator.hasNext()) {
            final ChangeLogSet.Entry entry = iterator.next();
            final String commiter = entry.getAuthor().getId();
            if (!commiters.containsKey(commiter)) {
                commiters.put(commiter, 0);
            }
            commiters.put(commiter, commiters.get(commiter) + 1);
        }
        return commiters;
    }

    @Override
    public boolean prebuild(final AbstractBuild<?, ?> build, final BuildListener listener) {
        try {
            final String komeaUrl = getKomeaUrl();
            if (komeaUrl == null || !build.equals(build.getRootBuild())) {
                return true;
            }
            final String jenkinsProjectName = build.getProject().getName();
            if (projectKey == null) {
                projectKey = jenkinsProjectName;
            }
            LOGGER.log(Level.FINE, "Prebuild of project {0}", projectKey);
            final Provider provider = KomeaComputerListener.getProvider();
            final int buildNumber = build.getNumber();
            final long buildDate = build.getTime().getTime();

            final List<EventSimpleDto> events = new ArrayList<EventSimpleDto>(2);
            events.add(EventsBuilder.createStartEvent(buildDate, buildNumber,
                    jenkinsProjectName, provider.getUrl(), projectKey, branch));
            events.add(EventsBuilder.createIndustrializationEvent(buildDate, buildNumber,
                    jenkinsProjectName, provider.getUrl(), projectKey, branch, industrialization));
            final Map<String, Integer> commiters = getCommiters(build);
            for (final String commiter : commiters.keySet()) {
                events.add(EventsBuilder.createCodeChangedEvent(buildDate, buildNumber, jenkinsProjectName,
                        provider.getUrl(), commiters.get(commiter), commiter, projectKey, branch));
            }
            if (isStartedByUserCommits(build)) {
                for (final String commiter : commiters.keySet()) {
                    events.add(EventsBuilder.createStartedByUser(buildDate, buildNumber,
                            jenkinsProjectName, provider.getUrl(), commiter, projectKey, branch));
                }
            }
            final String user = getStartedByUser(build);
            if (user != null) {
                events.add(EventsBuilder.createStartedByUser(buildDate, buildNumber,
                        jenkinsProjectName, provider.getUrl(), user, projectKey, branch));
            }
            pushEvents(komeaUrl, events);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace(listener.getLogger());
        }
        return true;
    }

    private static String getKomeaUrl() {
        final DescriptorImpl komeaDescriptor
                = Jenkins.getInstance().getDescriptorByType(DescriptorImpl.class);
        String serverUrl = komeaDescriptor.getServerUrl();
        if (serverUrl != null && serverUrl.trim().isEmpty()) {
            serverUrl = null;
        }
        return serverUrl;
    }

    private static void pushEvents(final String komeaUrl, final List<EventSimpleDto> events) throws Exception {
        if (events.isEmpty()) {
            return;
        }
        LOGGER.log(Level.FINE, "Push {0} events", events.size());
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(KomeaNotifier.class.getClassLoader());
            final IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI(komeaUrl);
            for (int i = 0; i < events.size(); i++) {
                final EventSimpleDto event = events.get(i);
                if (i == 0) {
                    try {
                        eventsAPI.pushEvent(event);
                    } catch (Exception ex) {
                        final ProviderDto providerDto = KomeaComputerListener.getProviderDto();
                        KomeaComputerListener.registerProvider(komeaUrl, providerDto);
                        eventsAPI.pushEvent(event);
                    }
                } else {
                    eventsAPI.pushEvent(event);
                }
            }
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }

}
