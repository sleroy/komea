package org.komea.connectors.bugzilla

import org.komea.connectors.bugzilla.proxy.impl.BugzillaServerConfiguration
import org.komea.connectors.bugzilla.schema.impl.BugzillaSchemaBuilder
import org.komea.connectors.bugzilla.schema.impl.BugzillaSchemaConnector
import org.komea.core.model.impl.OKomeaModelFactory
import org.komea.core.model.storage.impl.OKomeaGraphStorage
import org.komea.software.model.impl.MinimalCompanySchema
import org.springframework.orientdb.session.impl.OrientSessionFactory
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration

import spock.lang.Specification

import com.google.common.collect.Iterators




class BugzillaFromPOISchemaIT extends Specification{

	def schemaInstantiation() {
		setup:
		def dbc = new TestDatabaseConfiguration()
		// ORIENTDB
		def ogf = new OrientSessionFactory(dbc)

		/** "Schema Factories" **/

		def companySchema = new MinimalCompanySchema()
		def orientGraph = ogf.getGraphTx()
		def modelFactory = new OKomeaModelFactory(companySchema.getSchema(), orientGraph)

		when: "We update the schema"
		def bzSchema = new BugzillaSchemaBuilder(companySchema)

		and: "We create the model factory"

		def okomeaGraphStorage = new OKomeaGraphStorage(companySchema.getSchema(), orientGraph)

		and: "We connect to bugzilla"

		def bzServerConfiguration = new BugzillaServerConfiguration()
		bzServerConfiguration.serverURL = 'https://issues.apache.org/bugzilla/'
		bzServerConfiguration.project = "POI"

		//bzServerConfiguration.serverURL = 'https://bugzilla.wikimedia.org'
		//bzServerConfiguration.serverURL = 'https://www.libreoffice.org/bugzilla/'
		//bzServerConfiguration.serverURL = 'https://bugzilla.gnome.org'
		//bzServerConfiguration.serverURL = 'https://gcc.gnu.org/bugzilla'
		//bzServerConfiguration.serverURL = 'https://sourceware.org/bugzilla'
		//bzServerConfiguration.serverURL = 'https://bugzilla.kernel.org'
		//bzServerConfiguration.serverURL = 'https://bugzilla.redhat.org'
		//bzServerConfiguration.serverURL = 'https://bugzilla.gentoo.org'
		and: "We fill the model with new entities from an extract of a bugzilla server"

		def connector = new BugzillaSchemaConnector(new XStreamBugzillaUnserializerAPI(), okomeaGraphStorage, bzServerConfiguration)
		connector.updateSchema()

		println Iterators.size(modelFactory.getStorageService().entities().iterator())

		println "Count vertices " + orientGraph.countVertices()
		println "Count edges " + orientGraph.countEdges()

		then: "We should obtain a model with a given number of entities"
		Iterators.size(modelFactory.getStorageService().entities().iterator()) == 463 // FOR NO REGRESSION


	}
}
