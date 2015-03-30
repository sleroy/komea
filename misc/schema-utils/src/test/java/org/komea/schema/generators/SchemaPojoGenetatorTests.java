package org.komea.schema.generators;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.ecore.Ecore2KomeaSchemaBuilder;

public class SchemaPojoGenetatorTests {

	@Test
	public void generateProcessSchemaTest() throws IOException {

		final URI uri = URI.createFileURI("src/test/resources/process.ecore");
		final EPackage ecore = Ecore2KomeaSchemaBuilder.load(uri);
		final Ecore2KomeaSchemaBuilder builder = new Ecore2KomeaSchemaBuilder(new KomeaSchemaFactory());
		final String opackage = "process";
		final IKomeaSchema schema = builder.buildFrom(opackage, ecore);

		final File output = new File("build/gen");
		final KomeaSchemaPojoGenerator generator = new KomeaSchemaPojoGenerator(output, opackage);
		generator.generate(schema);
		Assert.assertTrue(FileUtils.getFile(output, opackage, "Member.java").exists());
	}

	@Test
	public void generateTest() {

		final IKomeaSchemaFactory factory = new KomeaSchemaFactory();
		final IKomeaSchema schema = factory.newSchema("schema");

		final IEntityType person = factory.newEntity("Person");
		person.addProperty(factory.newReference("name", Primitive.STRING));
		person.addProperty(factory.newReference("values", Primitive.INTEGER).setArity(ReferenceArity.MANY));
		person.addProperty(factory.newReference("children", person).setArity(ReferenceArity.MANY)
		        .setKind(ReferenceKind.AGGREGATION));
		schema.addType(person);

		final File output = new File("build/gen");

		final KomeaSchemaPojoGenerator generator = new KomeaSchemaPojoGenerator(output, "test");
		generator.generate(schema);
		Assert.assertTrue(FileUtils.getFile(output, "test", "Person.java").exists());
	}

}
