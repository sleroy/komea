package org.komea.connectors.bugzilla

import org.komea.connectors.bugzilla.proxy.impl.BugzillaServerConfiguration
import org.komea.connectors.bugzilla.schema.impl.BugzillaSchemaBuilder
import org.komea.connectors.bugzilla.schema.impl.BugzillaSchemaConnector
import org.komea.core.model.impl.OKomeaModelFactory
import org.komea.orientdb.session.impl.OrientGraphDatabaseFactory
import org.komea.orientdb.session.impl.TestDatabaseConfiguration
import org.komea.software.model.impl.MinimalCompanySchema

import spock.lang.Specification

import com.google.common.collect.Iterators




class BugzillaFromPOISchemaIT extends Specification{

	def schemaInstantiation() {
		setup:
		def dbc = new TestDatabaseConfiguration()
		// ORIENTDB
		def ogf = new OrientGraphDatabaseFactory(dbc)
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


		when:
		def companySchema = new MinimalCompanySchema()
		def bzSchema = new BugzillaSchemaBuilder(companySchema)
		def modelFactory = new OKomeaModelFactory(companySchema.getSchema(), ogf)
		def connector = new BugzillaSchemaConnector(new XStreamBugzillaUnserializerAPI(), ogf, bzServerConfiguration)
		connector.updateSchema()

		println Iterators.size(modelFactory.getStorageService().entities().iterator())

		then:
		Iterators.size(modelFactory.getStorageService().entities().iterator()) == 463 // FOR NO REGRESSION


	}
}
