package org.komea.connectors.redmine.launch

import org.komea.connectors.sdk.bugtracker.IBugTrackerAPI
import org.komea.connectors.sdk.main.impl.Connector

/**
 * Created by sleroy on 29/01/2015
 */
class Launcher {
	static void main(args) {
		def connector = new Connector("Redmine Provider")
		connector.addDefaultCommands IBugTrackerAPI.EVENT_NEW_BUG, IBugTrackerAPI.EVENT_UPDATED_BUG
		connector.addCommand new RedminePushEventsCommand()
		connector.run(args)
	}
}