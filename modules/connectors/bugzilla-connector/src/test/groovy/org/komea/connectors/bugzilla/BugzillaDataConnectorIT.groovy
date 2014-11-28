package org.komea.connectors.bugzilla

import static org.junit.Assert.*

import org.joda.time.DateTime
import org.junit.Test
import org.komea.event.query.service.EventQueryManagerService
import org.komea.event.storage.service.EventStorageService
import org.komea.orientdb.session.impl.OrientDocumentDatabaseFactory
import org.komea.orientdb.session.impl.TestDatabaseConfiguration

import spock.lang.Specification

class BugzillaDataConnectorIT extends Specification {

	@Test
	public void test() {
		setup:
		def bzServerConfiguration = new BugzillaServerConfiguration()
		bzServerConfiguration.serverURL = 'https://issues.apache.org/bugzilla/'
		bzServerConfiguration.project= 'POI'
		bzServerConfiguration.since = new DateTime().minusYears 1

		def bugAPI =  new BugzillaAPI()
		def dbc = new TestDatabaseConfiguration()
		// ORIENTDB
		def ogf = new OrientDocumentDatabaseFactory(dbc)
		def eventStorage = new EventStorageService(ogf)
		def queryservice = new EventQueryManagerService(ogf)


		when: "I inject the history of bugzilla from the past year of Apache POI"
		def dataConnector = new BugzillaDataConnector(bugAPI, eventStorage, bzServerConfiguration)
		dataConnector.launch()
		// "I query Komea for the number of new bugs and updated bugs"
		def newBugs = println("Number of new bugs found " + queryservice.countEventsOfType(IBugzillaConnectorInformations.EVENT_NEW_BUG))
		def updatedBugs = println("Number of update bugs found " + queryservice.countEventsOfType(IBugzillaConnectorInformations.EVENT_UPDATED_BUG))
		then: "I obtain positive values (bug created, bug updated)"
		newBugs > 0
		updatedBugs > 0

	}
}
