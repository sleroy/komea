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

    public List<Measure> evaluateJob(final JobWithDetails job, final Integer entityId) throws IOException {
        final List<Measure> measures = new ArrayList<>();
        final List<Build> builds = job.getBuilds();
        if (builds.isEmpty()) {
            return measures;
        }
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
        while (date.isBefore(DateTime.now())) {
            int failedBuilds = 0;
            int interruptedBuilds = 0;
            int successfulBuilds = 0;
            int unstableBuilds = 0;
            while (next != null && isSameDay(next, date)) {
                final BuildResult result = next.getResult();
                switch (result) {
                    case ABORTED:
                        interruptedBuilds++;
                        break;
                    case FAILURE:
                        failedBuilds++;
                        break;
                    case SUCCESS:
                        successfulBuilds++;
                        break;
                    case UNSTABLE:
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
