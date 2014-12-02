package org.komea.core.schema.tests;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.core.schema.impl.YUMLExport;

public class SchemaUMLTests {

	private final IKomeaSchemaFactory factory = new KomeaSchemaFactory();

	@Test
	public void yumlUrlTest() {
		IKomeaSchema schema = buildSchema();
		YUMLExport.buildYumlSchemaUrl(schema);
		
	}

	@Test
	public void htmlExportTest() throws IOException {
		IKomeaSchema schema = buildSchema();
		YUMLExport.exportToHtml(new File("build/output.html"), schema);
	}

	private IKomeaSchema buildSchema() {
		IKomeaSchema schema = this.factory.newSchema("test");

		final IEntityType entityType = this.factory.newEntity("Person");
		schema.addType(entityType);
		final IEntityType devType = this.factory.newEntity("Dev");
		devType.setSuperType(entityType);
		schema.addType(devType);
		final IEntityType headType = this.factory.newEntity("Head");
		schema.addType(headType);

		entityType.addProperty(this.factory.newAttribute("name",
				Primitive.STRING));
		entityType.addProperty(this.factory.newAttribute("age",
				Primitive.INTEGER));
		entityType.addProperty(this.factory.newAttribute("email",
				Primitive.STRING));

		entityType.addProperty(this.factory.newReference("family", entityType)
				.setKind(ReferenceKind.AGGREGATION)
				.setArity(ReferenceArity.MANY));

		entityType.addProperty(this.factory.newReference("head", headType)
				.setKind(ReferenceKind.CONTAINMENT)
				.setArity(ReferenceArity.ONE).enableMandatory());
		return schema;
	}
}
