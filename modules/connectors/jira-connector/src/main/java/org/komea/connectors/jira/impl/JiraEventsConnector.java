package org.komea.connectors.jira.impl;

import java.util.Date;
import java.util.List;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.SearchResult;
import net.rcarz.jiraclient.JiraException;

import org.komea.connectors.jira.IJiraConfiguration;
import org.komea.connectors.jira.IJiraEvents;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.utils.IJiraServerFactory;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraEventsConnector implements IJiraEvents {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(JiraEventsConnector.class);

	private final IEventStorage storage;
	private int Occurence;

	private final IJiraServerFactory jiraServerFactory;
	private JiraServerContext jira;

	private final IJiraConfiguration configuration;

	public JiraEventsConnector(final IEventStorage storage,
			final IJiraServerFactory jiraServerFactory,
			final IJiraConfiguration _configuration) {

		super();
		this.storage = storage;
		configuration = _configuration;
		Occurence = JiraServerContext.GetOccurence;
		this.jiraServerFactory = jiraServerFactory;
		storage.declareEventType(EVENT_NEW_BUG);
		storage.clearEventsOfType(EVENT_UPDATE_BUG);

	}

	public void push(final Date since) throws BadConfigurationException {

		jira = jiraServerFactory.getNewJiraServerContext(configuration);
		importNewIssue(since, -1);
		importUpdateIssue(since, -1);
	}

	public void push(final Date since, final int max)
			throws BadConfigurationException {

		jira = jiraServerFactory.getNewJiraServerContext(configuration);
		importNewIssue(since, max);
		importUpdateIssue(since, max);
	}

	public void setOccurence(final int Occurence) {
		this.Occurence = Occurence;
	}

	private void getAndSendIssues(final String type, final String jql,
			final int max) throws JiraException {

		int limit = max >= 0 && max < Occurence ? max : Occurence;

		final Issue.SearchResult searchIssues = jira.getClient().searchIssues(
				jql, configuration.getSelectedFields(), limit, 0);
		sendIssues(searchIssues.issues, type);
		for (int i = searchIssues.max; i < searchIssues.total
				&& (i < max || max == -1); i = i + searchIssues.max) {
			if (max != -1 && i + searchIssues.max > max) {
				limit = max - i;
			}
			final Issue.SearchResult parcourIssues = jira.getClient()
					.searchIssues(jql, configuration.getSelectedFields(),
							limit, i);
			sendIssues(parcourIssues.issues, type);
			log(searchIssues);
		}
	}

	private void importNewIssue(final Date date, final int max) {

		try {
			final String type = EVENT_NEW_BUG;
			final String jql = "created > \""
					+ JiraServerContext.FORMATTER.format(date) + "\"";
			getAndSendIssues(type, jql, max);
		} catch (final JiraException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void importUpdateIssue(final Date date, final int max) {

		try {
			final String type = EVENT_UPDATE_BUG;
			final String jql = "updated > \""
					+ JiraServerContext.FORMATTER.format(date) + "\"";
			getAndSendIssues(type, jql, max);

		} catch (final JiraException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void log(final SearchResult searchIssues) {

		final double percentage = ((double) searchIssues.start + searchIssues.max)
				/ searchIssues.total * 100;

		LOGGER.info("Processing done at {}%", (int) percentage);

	}

	private void sendEvent(final Issue bug, final String eventName) {

		final ComplexEvent event = new ComplexEvent();
		event.setProvider(JIRA);
		event.setEventType(eventName);
		event.setDate(Field.getDate(bug.getField("created")));

		event.setProperties(bug.getFields());
		storage.storeComplexEvent(event);
	}

	private void sendIssues(final List<Issue> issues, final String type) {

		for (final Issue issue : issues) {

			sendEvent(issue, type);
		}

	}

}
