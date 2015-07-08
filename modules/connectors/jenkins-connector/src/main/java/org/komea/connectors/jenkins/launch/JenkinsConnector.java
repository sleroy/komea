package org.komea.connectors.jenkins.launch;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.komea.product.database.model.Measure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsConnector.class.getName());

    public static JenkinsConnector create(final String host) throws URISyntaxException {
        final JenkinsConnector jenkinsConnector = new JenkinsConnector();
        jenkinsConnector.init(host);
        return jenkinsConnector;
    }

    private JenkinsServer jenkins;

    private JenkinsConnector() {
    }

    public void init(final String host) throws URISyntaxException {
        jenkins = new JenkinsServer(new URI(host));
    }

    public List<JobWithDetails> getJobs(final List<String> jobKeys) throws IOException {
        final Map<String, Job> jobs = jenkins.getJobs();
        final List<JobWithDetails> jobsWithDetails = new ArrayList<>(jobs.size());
        for (final Job job : jobs.values()) {
            if (jobKeys.isEmpty() || jobKeys.contains(job.getName())) {
                JobWithDetails jobWithDetails = job.details();
                jobsWithDetails.add(jobWithDetails);
            }
        }
        return jobsWithDetails;
    }

    public List<Measure> getMeasures(final JobWithDetails job, final Integer entityId) throws IOException {
        final List<Measure> measures = new ArrayList<>();
        final List<Build> builds = job.getBuilds();
        addMeasure(JenkinsKpis.JOBS, entityId, DateTime.now().toDate(), 1, measures);
        if (builds.isEmpty()) {
            addMeasure(JenkinsKpis.JOBS_NOT_EXECUTED, entityId, DateTime.now().toDate(), 1, measures);
            return measures;
        }
        addMeasure(JenkinsKpis.JOBS_EXECUTED, entityId, DateTime.now().toDate(), 1, measures);
        final List<BuildWithDetails> buildsWithDetails = new ArrayList<>(builds.size());
        for (final Build build : builds) {
            final BuildWithDetails buildWithDetails = build.details();
            buildsWithDetails.add(buildWithDetails);
        }
        Collections.sort(buildsWithDetails, new Comparator<BuildWithDetails>() {

            @Override
            public int compare(BuildWithDetails o1, BuildWithDetails o2) {
                return Long.compare(o1.getTimestamp(), o2.getTimestamp());
            }
        });
        final Iterator<BuildWithDetails> iterator = buildsWithDetails.iterator();
        DateTime date = toDayDate(buildsWithDetails.get(0).getTimestamp());
        BuildWithDetails next = iterator.next();
        BuildResult lastResult = null;
        DateTime lastFail = null;
        while (date.isBefore(DateTime.now())) {
            int failedBuilds = 0;
            int interruptedBuilds = 0;
            int successfulBuilds = 0;
            int unstableBuilds = 0;
            while (next != null && isSameDay(next, date)) {
                final DateTime buildDate = new DateTime(next.getTimestamp());
                final BuildResult result = next.getResult();
                switch (result) {
                    case ABORTED:
                        lastResult = result;
                        interruptedBuilds++;
                        break;
                    case FAILURE:
                        lastResult = result;
                        failedBuilds++;
                        lastFail = buildDate;
                        break;
                    case SUCCESS:
                        lastResult = result;
                        successfulBuilds++;
                        if (lastFail != null) {
                            final long duration = buildDate.toDate().getTime() - lastFail.toDate().getTime();
                            final double days = duration / (1000d * 60 * 60 * 24);
                            addMeasure(JenkinsKpis.FIX_TIME, entityId, buildDate.toDate(), days, measures);
                            lastFail = null;
                        }
                        break;
                    case UNSTABLE:
                        lastResult = result;
                        unstableBuilds++;
                        break;
                }
                next = iterator.hasNext() ? iterator.next() : null;
            }
            int totalBuilds = failedBuilds + interruptedBuilds + successfulBuilds + unstableBuilds;
            addMeasure(JenkinsKpis.BUILDS_FAILED, entityId, date.toDate(), failedBuilds, measures);
            addMeasure(JenkinsKpis.BUILDS_INTERRUPTED, entityId, date.toDate(), interruptedBuilds, measures);
            addMeasure(JenkinsKpis.BUILDS_SUCCESSFUL, entityId, date.toDate(), successfulBuilds, measures);
            addMeasure(JenkinsKpis.BUILDS_TOTAL, entityId, date.toDate(), totalBuilds, measures);
            addMeasure(JenkinsKpis.BUILDS_UNSTABLE, entityId, date.toDate(), unstableBuilds, measures);
            date = date.plusDays(1);
        }
        if (BuildResult.FAILURE.equals(lastResult)) {
            addMeasure(JenkinsKpis.LAST_BUILDS_FAILED, entityId, DateTime.now().toDate(), 1, measures);
            final long duration = DateTime.now().toDate().getTime() - lastFail.toDate().getTime();
            final double days = duration / (1000d * 60 * 60 * 24);
            addMeasure(JenkinsKpis.FAILED_STATUS_DURATION, entityId, DateTime.now().toDate(), days, measures);
        } else {
            addMeasure(JenkinsKpis.FAILED_STATUS_DURATION, entityId, DateTime.now().toDate(), 0, measures);
        }
        return measures;
    }

    private static void addMeasure(final String kpiKey, final Integer entityId,
            final Date date, final Number value, final List<Measure> measures) {
        final Measure measure = new Measure();
        measure.setDate(date);
        measure.setEntityID(entityId);
        measure.setIdKpi(kpiKey);
        measure.setValue(value.doubleValue());
        measures.add(measure);
    }

    private static boolean isSameDay(final BuildWithDetails build, final DateTime date) {
        return toDayDate(build.getTimestamp()).equals(date);
    }

    private static DateTime toDayDate(final long timestamp) {
        return new DateTime(timestamp).withMillisOfSecond(0)
                .withSecondOfMinute(0).withMinuteOfHour(0).withHourOfDay(0);
    }

}
