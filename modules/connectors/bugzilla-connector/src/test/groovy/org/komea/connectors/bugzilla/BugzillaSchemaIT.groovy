package org.komea.connectors.bugzilla

import org.joda.time.DateTime
import org.komea.connectors.bugzilla.schema.impl.BugzillaSchemaBuilder
import org.komea.core.model.impl.OKomeaModelFactory
import org.komea.software.model.impl.MinimalCompanySchema
import org.komea.software.model.pojo.Human
import org.springframework.orientdb.session.impl.OrientSessionFactory
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration

import spock.lang.Specification

import com.google.common.collect.Iterators




class BugzillaSchemaIT extends Specification{

	def schemaInstantiation() {
		setup:
		def dbc = new TestDatabaseConfiguration()
		// ORIENTDB
		def ogf = new OrientSessionFactory(dbc)




		when:
		def companySchema = new MinimalCompanySchema()
		def bzSchema = new BugzillaSchemaBuilder(companySchema)
		def modelFactory = new OKomeaModelFactory(companySchema.getSchema(), ogf.getGraph())

		def entityFiller = modelFactory.newEntityFiller(companySchema.getSchema().findType('Human'))
		def human = new Human()
		human.login = "dvader"
		human.firstName = "Darth"
		human.lastName = "Vader"
		human.creation_date = new DateTime().toDate()

		entityFiller.put human


		then:
		Iterators.size(modelFactory.storageService.entities().iterator()) == 1


	}
}
