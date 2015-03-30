package org.komea.core.model.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.impl.OEntityAttributeManager;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.KomeaSchemaFactory;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class OAttributeManagerTests extends AbstractIntegrationTest {
	private OrientVertex	          vertex;
	private final IKomeaSchemaFactory	sfactory	= new KomeaSchemaFactory();

	@Before
	public void before() {
		this.vertex = this.getGraph().addVertex(null);
	}

	@Test
	public void setPrimitiveCollectionReferenceTest() {
		final IReference name = this.sfactory.newAttribute("values", Primitive.INTEGER).setArity(ReferenceArity.MANY);
		final OEntityAttributeManager updater = new OEntityAttributeManager(this.vertex, name);
		updater.addReference(1);
		updater.addReference(2);
		final List<Integer> values = updater.get();
		assertEquals(values.size(), 2);
	}

	@Test
	public void setPrimitiveCollectionValidationReferenceTest() {
		final IReference name = this.sfactory.newAttribute("values", Primitive.INTEGER).setArity(ReferenceArity.MANY);
		final OEntityAttributeManager updater = new OEntityAttributeManager(this.vertex, name);

		try {
			updater.addReference("a");
			// we can not add a String in an integer collection attribute
			fail();
		} catch (final IllegalArgumentException e) {
			// succeed
		}
	}

	@Test
	public void setPrimitiveReferenceTest() {
		final IReference name = this.sfactory.newAttribute("name", Primitive.STRING);
		final OEntityAttributeManager updater = new OEntityAttributeManager(this.vertex, name);
		updater.set("John Doe");
		final String value = updater.get();
		assertEquals("John Doe", value);
	}

	@Test
	public void setPrimitiveValidationReferenceTest() {
		final IReference name = this.sfactory.newAttribute("name", Primitive.STRING);
		final OEntityAttributeManager updater = new OEntityAttributeManager(this.vertex, name);

		try {
			updater.set(1);
			// we can not set an integer in a string attribute
			fail();
		} catch (final IllegalArgumentException e) {
			// succeed
		}
	}

	@Override
	protected void initSchema() {
		final IEntityType type = this.sfactory.newEntity("Person");
		final IReference name = this.sfactory.newAttribute("name", Primitive.STRING);
		type.addProperty(name);
		final IReference references = this.sfactory.newReference("children", type).setArity(ReferenceArity.MANY)
				.setKind(ReferenceKind.AGGREGATION);
		type.addProperty(references);
		this.getSchema().addType(type);

	}

}
