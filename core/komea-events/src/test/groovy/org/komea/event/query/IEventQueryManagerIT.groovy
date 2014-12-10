package org.komea.event.query

import org.komea.event.model.beans.BasicEvent
import org.komea.event.query.impl.EventQueryManager
import org.komea.event.storage.impl.EventStorage
import org.komea.event.storage.impl.NewBugzillaBugEvent
import org.springframework.orientdb.session.impl.OrientSessionFactory
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration

import spock.lang.Specification

class IEventQueryManagerIT extends Specification{

	TestDatabaseConfiguration dbc

	OrientSessionFactory ogf
	EventStorage eventStorage
	EventQueryManager queryservice

	def setup() {

		dbc = new TestDatabaseConfiguration()
		ogf = new OrientSessionFactory(dbc)
		ogf.getOrCreateDB()
		eventStorage = new EventStorage(ogf)
		queryservice = new EventQueryManager(ogf)
	}


	def queryTest() {

		when: "I store two  bugzilla events"

		eventStorage.storeBasicEvent new BasicEvent("bugzilla", "newbug")
		eventStorage.storeBasicEvent new BasicEvent("bugzilla", "newbug")

		and: "I execute a query to count events"
		def numberOfEvents = queryservice.countEventsOfType "newbug"

		then: "I expect to receive the value two"
		numberOfEvents == 2
	}

	def cleanup() {
		ogf.close()
	}

	def complexQueryTest() {
		when: "I store one complex bugzilla event"

		eventStorage.storePojo new NewBugzillaBugEvent(12, 1)
		eventStorage.storePojo new NewBugzillaBugEvent(14, 2)
		eventStorage.storePojo new NewBugzillaBugEvent(18, 2)


		and: "I execute a query to count events"

		def numberOfEvents = queryservice.countEventsOfType("new_bug")

		then: "I expect to receive the value three"

		numberOfEvents == 3

		and: "I want to count events based on the severity"

		def severity1 = queryservice.countEventsPerQuery("new_bug", "severity=1")
		def severity2 = queryservice.countEventsPerQuery("new_bug", "severity=2")
		def events = queryservice.browseEvents("new_bug")
		for (def event in events) {
			println event
		}

		then: "I expect to receive 1 and 2 respectively"
		severity1 == 1
		severity2 == 2
	}
}
