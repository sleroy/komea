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
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    private static final Logger LOGGER = Logger.getLogger(KomeaNotifier.class.getName());

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
        return !Result.SUCCESS.equals(build.getResult())
                && build.getPreviousBuild() != null
                && Result.SUCCESS.equals(build.getPreviousBuild().getResult());
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
        final List<EventSimpleDto> events = new ArrayList<EventSimpleDto>(1);
        final EventSimpleDto resultEvent = EventsBuilder.createResultEvent(start, end,
                buildNumber, result, jenkinsProjectName, provider.getUrl(), projectKey, branch);
        if (resultEvent != null) {
            events.add(resultEvent);
        }
        final String user = getStartedByUser(build);
        if (user != null) {
            events.add(EventsBuilder.createStartedByUser(start, buildNumber,
                    jenkinsProjectName, projectKey, user, projectKey, branch));
        }
        if (buildBroken(build)) {
            events.add(EventsBuilder.createBuildBroken(start, buildNumber,
                    jenkinsProjectName, projectKey, user, projectKey, branch));
        } else if (buildFixed(build)) {
            events.add(EventsBuilder.createBuildFixed(start, buildNumber,
                    jenkinsProjectName, projectKey, user, projectKey, branch));
        }
        pushEvents(listener, events);
        return true;
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
        pushEvents(listener, events);
        return true;
    }

    private String getServerUrl() {

        String serverUrl = getDescriptor().getServerUrl();
        if (serverUrl != null && serverUrl.trim().isEmpty()) {
            serverUrl = null;
        }
        return serverUrl;
    }

    private void pushEvents(final BuildListener listener, final List<EventSimpleDto> events) {

        final String komeaUrl = getServerUrl();
        if (komeaUrl == null) {
            return;
        }
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
            if (listener == null) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            } else {
                ex.printStackTrace(listener.getLogger());
            }
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }

}
