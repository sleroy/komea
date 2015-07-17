package org.komea.connectors.jira.impl;


import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.SearchResult;
import net.rcarz.jiraclient.JiraException;
import org.komea.connectors.jira.IJiraConfiguration;
import org.komea.connectors.jira.IJiraEvents;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.utils.IJiraServerFactory;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.model.Measure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KomeaDemoPushMeasures implements IJiraEvents {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(KomeaDemoPushMeasures.class);

	private static final int MIN_DESCRIPTION_SIZE = 10;
	private static final int MIN_SUBJECT_SIZE     = 10;

	private final KomeaConnector storage;
	private       int            Occurence;

	private final IJiraServerFactory jiraServerFactory;
	private       JiraServerContext  jira;

	private final IJiraConfiguration configuration;

	private List<Issue> issues;
	private Project     project;

	public KomeaDemoPushMeasures(final KomeaConnector storage,
			final IJiraServerFactory jiraServerFactory,
			final IJiraConfiguration _configuration, final Project _project) {

		super();
		this.storage = storage;
		configuration = _configuration;
		Occurence = JiraServerContext.GetOccurence;
		this.jiraServerFactory = jiraServerFactory;
		this.project = _project;
		issues = new ArrayList<>();

	}

	public void init() throws BadConfigurationException {

		jira = jiraServerFactory.getNewJiraServerContext(configuration);

	}
	public void push(final Date since, final Date to) throws BadConfigurationException {

		importNewIssues(since, to, -1);
	}

	public void push(final Date since, final Date to, final int max)
			throws BadConfigurationException {

		jira = jiraServerFactory.getNewJiraServerContext(configuration);
		importNewIssues(since, to, max);
	}

	private void getIssues(final String type, final String jql,
			final int max) throws JiraException {

		LOGGER.info("Fetch issues");
		int limit = max >= 0 && max < Occurence ? max : Occurence;

		final Issue.SearchResult searchIssues = jira.getClient().searchIssues(
				jql, configuration.getSelectedFields(), limit, 0);
		//sendCreatedIssuesMeasures(searchIssues.issues, type);
		for (int i = searchIssues.max; i < searchIssues.total
				&& (i < max || max == -1); i = i + searchIssues.max) {
			if (max != -1 && i + searchIssues.max > max) {
				limit = max - i;
			}
			final Issue.SearchResult parcourIssues = jira.getClient()
			                                             .searchIssues(jql, configuration.getSelectedFields(),
					                                             limit, i);
			issues.addAll(parcourIssues.issues);
			log(i, searchIssues);
		}
	}

	public void importNewIssues(final Date from, final Date to, final int max) {

		try {
			System.out.println("find issues from " + from.toString() + " to " + to.toString());
			jira = jiraServerFactory.getNewJiraServerContext(configuration);
			final String type = EVENT_NEW_BUG;
			final String jql = "created > \"" + JiraServerContext.FORMATTER.format(from) + "\" AND "
					+ "created <= \"" + JiraServerContext.FORMATTER.format(to) + "\"";
			getIssues(type, jql, max);
		} catch (final JiraException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} catch (BadConfigurationException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void log(final int current, final SearchResult searchIssues) {

		final double percentage = ((double) current + searchIssues.max)
				/ searchIssues.total * 100;

		LOGGER.info("Processing done at {}%", (int) percentage);

	}

	public void sendCreatedIssuesMeasures() throws BadConfigurationException {

		List<Measure> measures = new ArrayList<>();
		for (final Issue issue : issues) {

			final Measure measure = new Measure();
			measure.setIdKpi("bugtracker_bugs_created");
			measure.setDate(issue.getDueDate());
			measure.setDate(Field.getDate(issue.getField("created")));
			measure.setValue(1D);
			measures.add(measure);
		}
		storage.pushMeasures(measures);
	}

	public void sendBadWrittenIssuesMeasures() throws BadConfigurationException {

		List<Measure> measures = new ArrayList<>();
		for (final Issue issue : issues) {
			if (isBadWritten(issue)) {
				final Measure measure = new Measure();
				measure.setIdKpi("bugtracker_bugs_created");
				measure.setDate(Field.getDate(issue.getField("created")));
				measure.setValue(1D);
				measure.setEntityID(project.getId());
				measures.add(measure);
			}
		}
		storage.pushMeasures(measures);
	}
	private boolean isBadWritten(final Issue _issue) {

		System.out.println("issue fields : " + _issue.getFields());
		return _issue.getPriority() == null || _issue.getDescription().length() < MIN_DESCRIPTION_SIZE
				|| _issue.getTimeEstimate() == null;
	}

}
