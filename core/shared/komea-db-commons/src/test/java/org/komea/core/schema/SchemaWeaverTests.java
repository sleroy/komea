package org.komea.core.schema;

import org.junit.Assert;
import org.junit.Test;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.core.schema.impl.SchemaWeaver;

import com.google.common.collect.Lists;

public class SchemaWeaverTests {

	private final IKomeaSchemaFactory	factory	= new KomeaSchemaFactory();

	@Test
	public void weaverTest() {

		final IKomeaSchema schema1 = buildSchema1();
		final IKomeaSchema schema2 = buildSchema2();

		final SchemaWeaver weaver = new SchemaWeaver();
		final IKomeaSchema weaved = weaver.weave("weaved", Lists.newArrayList(schema1, schema2));
		Assert.assertTrue(weaved.findType("Person").getProperties().size() == 2);
	}

	private IKomeaSchema buildSchema1() {

		final IKomeaSchema schema = factory.newSchema("S1");

		final IEntityType person = factory.newEntity("Person");
		person.addProperty(factory.newAttribute("name", Primitive.STRING).enableIndexation());

		final IEntityType developer = factory.newEntity("Developper");
		developer.setSuperType(person);

		schema.addType(person);
		schema.addType(developer);
		return schema;
	}

	private IKomeaSchema buildSchema2() {

		final IKomeaSchema schema = factory.newSchema("S2");

		final IEntityType person = factory.newEntity("Person");
		person.addProperty(factory.newAttribute("name", Primitive.STRING));
		person.addProperty(factory.newAttribute("email", Primitive.STRING));

		schema.addType(person);
		return schema;
	}
}
