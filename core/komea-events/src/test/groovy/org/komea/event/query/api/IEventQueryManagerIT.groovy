package org.komea.event.query.api

import org.komea.event.model.beans.BasicEvent
import org.komea.event.query.service.EventQueryManagerService
import org.komea.event.storage.service.EventStorageService
import org.komea.orientdb.session.impl.OrientDocumentDatabaseFactory
import org.komea.orientdb.session.impl.RandomMemoryDatabaseTestConfiguration

import spock.lang.Specification

class IEventQueryManagerIT extends Specification{


	def queryTest() {
		setup:
		def dbc = new RandomMemoryDatabaseTestConfiguration()
		def ogf = new OrientDocumentDatabaseFactory(dbc)
		def eventStorage = new EventStorageService(ogf)
		def queryservice = new EventQueryManagerService(ogf)

		when: "I store two events"

		eventStorage.storeEvent(new BasicEvent("bugzilla", "newbug"))
		eventStorage.storeEvent(new BasicEvent("bugzilla", "newbug"))

		and: "I execute a query to count evnets"
		def numberOfEvents = queryservice.countEventsOfType("newbug")

		then: "I expect to receive the value two"
		numberOfEvents == 2


		cleanup:
		ogf.close()
	}
}
