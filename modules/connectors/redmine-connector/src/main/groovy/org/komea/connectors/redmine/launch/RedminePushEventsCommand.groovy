/**
 *
 */
package org.komea.connectors.redmine.launch

import org.joda.time.DateTime
import org.kohsuke.args4j.Option
import org.komea.connectors.sdk.bugtracker.BugEvent
import org.komea.connectors.sdk.bugtracker.IBugTrackerAPI
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI
import org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand

import com.taskadapter.redmineapi.RedmineManagerFactory
import com.taskadapter.redmineapi.bean.Issue
import com.taskadapter.redmineapi.bean.Project

/**
 * Git push event
 *
 * @author sleroy
 *
 */
class RedminePushEventsCommand extends AbstractPushEventsCommand {

	@Option(name = "-r", aliases = [
		"--redmine"
	], usage = "URL to redmine server", required = true)
	protected String redmineURL

	@Option(name = "-u", aliases = [
		"--user"
	], usage = "Redmine username", required = true)
	protected String userName
	@Option(name = "-p", aliases = [
		"--password"
	], usage = "Redmine password", required = true)
	protected String password

	RedminePushEventsCommand() {
		super(IBugTrackerAPI.EVENT_UPDATED_BUG)
	}



	void sendEvents(final IEventoryClientAPI _eventoryClientAPI,
			final DateTime _from, final DateTime _to) throws Exception {

		_eventoryClientAPI.eventStorage.declareEventType IBugTrackerAPI.EVENT_NEW_BUG
		_eventoryClientAPI.eventStorage.declareEventType IBugTrackerAPI.EVENT_UPDATED_BUG
		println "Number of new events " + _eventoryClientAPI.countEvents(IBugTrackerAPI.EVENT_NEW_BUG)
		println "Number of updated events " + _eventoryClientAPI.countEvents(IBugTrackerAPI.EVENT_NEW_BUG)

		def redmineManager = RedmineManagerFactory.createWithUserAuth redmineURL, userName, password
		for (Project project in  redmineManager.projectManager.projects) {
			println "Scanning project ${project.name}"

			def issuemap = ["project_id": project.id.toString(), "status_id": "*"]

			for (Issue issue in redmineManager.issueManager.getIssues(issuemap)) {
				if (isBetween(_from,_to, new DateTime(issue.createdOn))) {
					println "\t> issue created $issue"
					def event = new BugEvent("redmine", IBugTrackerAPI.EVENT_NEW_BUG, issue, project, project.getIdentifier())
					_eventoryClientAPI.getEventStorage().storeEvent(event)
				}
				if (isBetween(_from, _to, new DateTime(issue.updatedOn))) {
					println "\t> issue updated $issue"
					def event = new BugEvent("redmine", IBugTrackerAPI.EVENT_UPDATED_BUG, issue, project, project.getIdentifier())
					_eventoryClientAPI.getEventStorage().storeEvent(event)
				}
			}
		}
	}

	def isBetween(_from , _to, _date) {
		return _from == null ? !_to.isBefore(_date) : _from.isBefore(_date) && !_to.isBefore(_date)
	}
}
