/**
 *
 */
package org.komea.connectors.jira.launch;

import org.joda.time.DateTime;
import org.kohsuke.args4j.Option;
import org.komea.connectors.jira.IJiraConfiguration;
import org.komea.connectors.jira.IJiraEvents;
import org.komea.connectors.jira.impl.JiraEventsConnector;
import org.komea.connectors.jira.utils.JiraServerFactory;
import org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand;
import org.komea.events.api.IEventsClient;

/**
 * Git push event
 *
 * @author sleroy
 *
 */
public class JiraPushEventsCommand extends AbstractPushEventsCommand implements
        IJiraConfiguration {

    @Option(name = "-jira", usage = "URL to the  jira server", required = true, aliases = {"-j"})
    protected String jiraURL;

    @Option(name = "--login", usage = "Login to connect on the  jira server", required = false, aliases = {"-l"}, depends = {"--password"})
    protected String login;

    @Option(name = "--password", usage = "Login to connect on the  jira server", required = false, aliases = {"-p"})
    protected String password;

    @Option(name = "--fields", usage = "Fields to import (separated by a comma)", required = false)
    protected String fields = "id,key,created,issuetype,priority,assignee,reporter,description,project,status,versions,resolution,issuetype,status,assignee,resolution,project,created,updated,creator";

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.jira.impl.IJiraConfiguration#getLogin()
     */
    @Override
    public String getLogin() {
        return login;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.jira.impl.IJiraConfiguration#getPassword()
     */
    @Override
    public String getPassword() {
        return password;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.komea.connectors.jira.impl.IJiraConfiguration#getSelectedFields()
     */
    @Override
    public String getSelectedFields() {
        return fields;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.connectors.jira.impl.IJiraConfiguration#getUrl()
     */
    @Override
    public String getUrl() {
        return jiraURL;
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
    protected void sendEvents(final IEventsClient _eventoryClientAPI,
            final DateTime _lastExecution) throws Exception {
        _eventoryClientAPI.declareEventType(
                IJiraEvents.EVENT_NEW_BUG);
        _eventoryClientAPI.declareEventType(
                IJiraEvents.EVENT_UPDATE_BUG);

        final JiraEventsConnector jiraEventsConnector = new JiraEventsConnector(
                _eventoryClientAPI,
                JiraServerFactory.getInstance(), this);
        jiraEventsConnector.push(_lastExecution.toDate());
    }
}
