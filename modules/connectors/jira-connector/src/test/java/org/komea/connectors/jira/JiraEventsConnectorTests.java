package org.komea.connectors.jira;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import org.junit.Test;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.impl.JiraConfiguration;
import org.komea.connectors.jira.impl.JiraEventsConnector;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.komea.connectors.jira.utils.JiraServerFactory;
import org.mockito.Mockito;

public class JiraEventsConnectorTests {

	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm");
	private final static String DATE = "2014/12/16 11:00";
	private final static int OCCURENCE = 5;

	@Test
	public void test() throws BadConfigurationException, ParseException {

		final CountingStorage storage = new CountingStorage();
		final IJiraConfiguration config = new JiraConfiguration(
				"https://jira.mongodb.org/");
		final JiraEventsConnector jc = new JiraEventsConnector(storage,
				JiraServerFactory.getInstance(), config);

		final Date since = DATE_FORMAT.parse("2014/11/10 11:00");
		jc.push(since, 3);

		assertEquals(3, storage.counters.get(IJiraEvents.EVENT_NEW_BUG)
				.intValue());

	}

	@Test
	public void testJiraApi() throws ParseException, JiraException {

		final Date _date = DATE_FORMAT.parse("2014/11/10 11:00");
		final String format = JiraServerContext.FORMATTER.format(_date);
		final IJiraConfiguration config = new JiraConfiguration(
				"https://jira.mongodb.org/");
		final JiraClient jiraClient = new JiraClient(config.getUrl(), null,
				true);

		final Issue.SearchResult searchIssues = jiraClient.searchIssues(
				"created > \"" + format + "\"", 10);
		assertTrue(searchIssues.issues.size() > 0);

	}

	@Test
	public void testUpdateEvents1() {
		final int nb = 3;
		final int max = -1;
		final int result = testcoreUpdateEvents(nb, max);
		assertTrue(result == 2 * nb * OCCURENCE);
	}

	@Test
	public void testUpdateEvents2() {

		final int nb = 1;
		final int max = -1;
		final int result = testcoreUpdateEvents(nb, max);
		assertTrue(result == 2 * nb * OCCURENCE);
	}

	@Test
	public void testUpdateEvents3() {

		final int nb = 3;
		final int max = 20;
		final int result = testcoreUpdateEvents(nb, max);
		assertTrue(result == 2 * nb * OCCURENCE);
	}

	@Test
	public void testUpdateEvents4() {

		final int nb = 1;
		final int max = 20;
		final int result = testcoreUpdateEvents(nb, max);
		assertTrue(result == 2 * nb * OCCURENCE);
	}

	@Test
	public void testUpdateEvents5() {

		final int nb = 5;
		final int max = 16;
		final int result = testcoreUpdateEvents(nb, max);
		assertTrue(result == 2 * max);
	}

	@Test
	public void testUpdateEvents6() {

		final int nb = 1;
		final int max = 2;
		final int result = testcoreUpdateEvents(nb, max);
		assertTrue(result == 2 * max);
	}

	private List<Issue> createIssues(final int nb, final int start) {

		final List<Issue> result = new ArrayList<>();

		for (int i = 0; i < nb; i++) {
			final Issue mock = Mockito.mock(Issue.class);
			final Map<String, String> map = new HashMap<String, String>();
			map.put("name", "name" + (start + i));
			map.put("key", "key" + (start + i));
			Mockito.when(mock.getFields()).thenReturn(map);
			result.add(mock);
		}

		return result;
	}

	private void createOccurence(final JiraClient mockJiraClient,
			final int nombre, final int max) throws JiraException,
			ParseException {

		final int total = nombre * OCCURENCE;

		if (max != -1 && max < OCCURENCE) {
			final Issue.SearchResult rs = new Issue.SearchResult();
			rs.issues = createIssues(max, 0);
			rs.start = 0;
			rs.max = max;
			rs.total = total;
			createReturnIssue(mockJiraClient, max, 0, rs);
		} else if (max != -1 && max < total) {

			for (int i = 0; i < nombre; i++) {

				if (max < (i + 1) * OCCURENCE) {
					final int valLIm = max - i * OCCURENCE;

					final Issue.SearchResult rs = new Issue.SearchResult();
					rs.issues = createIssues(valLIm, i * OCCURENCE);
					rs.start = i * OCCURENCE;
					rs.max = valLIm;
					rs.total = total;
					createReturnIssue(mockJiraClient, valLIm, i * OCCURENCE, rs);
					break;

				} else {
					final Issue.SearchResult rs = new Issue.SearchResult();
					rs.issues = createIssues(OCCURENCE, i * OCCURENCE);
					rs.start = i * OCCURENCE;
					rs.max = OCCURENCE;
					rs.total = total;
					createReturnIssue(mockJiraClient, OCCURENCE, i * OCCURENCE,
							rs);
				}
			}

		} else {
			for (int i = 0; i < nombre; i++) {
				final Issue.SearchResult rs = new Issue.SearchResult();
				rs.issues = createIssues(OCCURENCE, i * OCCURENCE);
				rs.start = i * OCCURENCE;
				rs.max = OCCURENCE;
				rs.total = total;
				createReturnIssue(mockJiraClient, OCCURENCE, i * OCCURENCE, rs);
			}
		}

	}

	private void createReturnIssue(final JiraClient mockJiraClient,
			final int occurence, final int start, final Issue.SearchResult rs)
			throws ParseException, JiraException {
		final Date since = DATE_FORMAT.parse(DATE);
		final String jql1 = "created > \""
				+ JiraServerContext.FORMATTER.format(since) + "\"";
		Mockito.when(
				mockJiraClient.searchIssues(jql1,
						JiraConfiguration.SELECTED_FIELDS, occurence, start))
				.thenReturn(rs);
		final String jql2 = "updated > \""
				+ JiraServerContext.FORMATTER.format(since) + "\"";
		Mockito.when(
				mockJiraClient.searchIssues(jql2,
						JiraConfiguration.SELECTED_FIELDS, occurence, start))
				.thenReturn(rs);
	}

	private int testcoreUpdateEvents(final int nb, final int max) {

		try {
			final IJiraConfiguration jconf = new JiraConfiguration(
					"https://jira.atlassian.com/", null, null);
			// initialisation
			final Date since = DATE_FORMAT.parse(DATE);

			// Mock de jiraClient
			final JiraClient mockJiraClient = Mockito.mock(JiraClient.class);
			final JiraServerFactory mockFactory = Mockito
					.mock(JiraServerFactory.class);
			final JiraServerContext jcontext = new JiraServerContext(
					mockJiraClient);
			Mockito.when(mockFactory.getNewJiraServerContext(jconf))
					.thenReturn(jcontext);

			final CountingStorage storage = new CountingStorage();

			final JiraEventsConnector jc = new JiraEventsConnector(storage,
					mockFactory, jconf);

			// ----------------
			// Maitrise des valeurs retourner par le mock de jira client
			createOccurence(mockJiraClient, nb, max);
			jc.setOccurence(OCCURENCE);
			if (max == -1) {
				jc.push(since);
			} else {
				jc.push(since, max);
			}

			// verification
			final int countEventsNew = storage.counters
					.get(IJiraEvents.EVENT_NEW_BUG);
			final int countEventsUpdate = storage.counters
					.get(IJiraEvents.EVENT_UPDATE_BUG);
			return countEventsNew + countEventsUpdate;
		} catch (ParseException | BadConfigurationException | JiraException ex) {
			Logger.getLogger(JiraEventsConnectorTests.class.getName()).log(
					Level.SEVERE, null, ex);
			return -1;

		}
	}
}
