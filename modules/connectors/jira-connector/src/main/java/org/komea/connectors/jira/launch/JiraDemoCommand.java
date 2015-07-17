package org.komea.connectors.jira.launch;


import org.joda.time.DateTime;
import org.kohsuke.args4j.Option;
import org.komea.connectors.jira.IJiraConfiguration;
import org.komea.connectors.jira.IJiraEvents;
import org.komea.connectors.jira.impl.KomeaConnector;
import org.komea.connectors.jira.impl.KomeaDemoPushMeasures;
import org.komea.connectors.jira.utils.JiraServerFactory;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;
import org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand;
import org.komea.product.company.database.enums.EntityType;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;

/**
 * Created by jguidoux on 16/07/15.
 */
public class JiraDemoCommand extends AbstractPushEventsCommand implements IJiraConfiguration
 {

	@Option(name = "-jira", usage = "URL to the  jira server", required = true, aliases = { "-j" })
	protected String jiraURL;


	@Option(name = "-project", usage = "project name", required = true, aliases = { "-P" })
	protected String projectName;


	@Option(name = "--login", usage = "Login to connect on the  jira server", required = false, aliases = {
			"-l" }, depends = { "--password" })
	protected String login;

	@Option(name = "--password", usage = "Login to connect on the  jira server", required = false, aliases = { "-p" })
	protected String password;

	@Option(name = "--fields", usage = "Fields to import (separated by a comma)", required = false)
	protected String fields = "id,key,created,issuetype,priority,assignee,reporter,description,project,status,versions,resolution,issuetype,status,assignee,resolution,project,created,updated,creator";

	/**
	 *
	 */
	public JiraDemoCommand() {
		super(IJiraEvents.EVENT_NEW_BUG);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.jira.impl.IJiraConfiguration#getLogin()
	 */
	@Override
	public String getLogin() {
		return this.login;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.jira.impl.IJiraConfiguration#getPassword()
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.connectors.jira.impl.IJiraConfiguration#getSelectedFields()
	 */
	@Override
	public String getSelectedFields() {
		return this.fields;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.jira.impl.IJiraConfiguration#getUrl()
	 */
	@Override
	public String getUrl() {
		return this.jiraURL;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#init()
	 */
	@Override
	public void init() throws Exception {
		//
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand#sendEvents
	 * (org.komea.connectors.sdk.rest.impl.EventoryClientAPI)
	 */
	@Override
	protected void sendEvents(final IEventoryClientAPI _eventoryClientAPI,
			final DateTime _from, final DateTime _to) throws Exception {
		_eventoryClientAPI.getEventStorage()
		                  .declareEventType(IJiraEvents.EVENT_NEW_BUG);
		_eventoryClientAPI.getEventStorage()
		                  .declareEventType(IJiraEvents.EVENT_UPDATE_BUG);

		initKomeaInformations();


//		final KomeaDemoPushMeasures demePushMeasures = new KomeaDemoPushMeasures(komeaConnector,
//				JiraServerFactory.getInstance(),
//				this, project);
//
//		demePushMeasures.importNewIssues(_from.toDate(), _to.toDate(), -1);
//		demePushMeasures.sendCreatedIssuesMeasures();
	}
	 private void initKomeaInformations() throws Exception {

		 System.out.println(getServerURL());
		 KomeaConnector komeaConnector = KomeaConnector.create(getServerURL());
		 Project project = komeaConnector.getOrCreateProject("blablabla");
		 komeaConnector.getOrCreateKpiName("bugtracker_bugs_created", EntityType.PROJECT, GroupFormula.LAST_VALUE);
		 Kpi kpi = komeaConnector
				 .getOrCreateKpiName("bugtracker_badwritten_tickets", EntityType.PROJECT, GroupFormula.LAST_VALUE);
		 komeaConnector.getOrCreateKpiName("bugtracker_project_progress", EntityType.PROJECT, GroupFormula.LAST_VALUE);
		 komeaConnector.getOrCreateKpiName("bugtracker_bugs_total", EntityType.PROJECT, GroupFormula.LAST_VALUE);
	 }

 }
