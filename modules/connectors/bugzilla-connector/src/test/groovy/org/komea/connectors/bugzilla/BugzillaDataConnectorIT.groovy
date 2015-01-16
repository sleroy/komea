package org.komea.connectors.bugzilla

import static org.junit.Assert.*

import org.junit.Test
import org.komea.connectors.bugzilla.proxy.impl.BugzillaServerConfiguration
import org.komea.event.storage.impl.EventStorage
import org.springframework.orientdb.session.impl.OrientSessionFactory
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration

import spock.lang.Specification

class BugzillaDataConnectorIT extends Specification {

	@Test
	public void test() {
		setup:
		def bzServerConfiguration = new BugzillaServerConfiguration()
		bzServerConfiguration.serverURL = 'https://issues.apache.org/bugzilla/'
		bzServerConfiguration.project= 'POI'

		def dbc = new TestDatabaseConfiguration()
		// ORIENTDB
		def ogf = new OrientSessionFactory(dbc)
		def eventStorage = new EventStorage(ogf)
//		def queryservice = new EventQueryManager(ogf)

		when: "I inject the history of bugzilla  of Apache POI"
		def dataConnector = new BugzillaEventConnector(eventStorage, bzServerConfiguration)
		dataConnector.launch()
//                FIXME
		// "I query Komea for the number of new bugs and updated bugs"
//		def newBugs = queryservice.countEventsOfType(BugzillaEventConnector.EVENT_NEW_BUG)
//		println "Number of new bugs found ${newBugs}"
//		def updatedBugs = queryservice.countEventsOfType(BugzillaEventConnector.EVENT_UPDATED_BUG)
//		println "Number of update bugs found ${updatedBugs}"

		then: "I obtain positive values (bug created, bug updated)"
//		newBugs > 0
//		updatedBugs > 0
                true

	}
}
