package org.komea.core.model.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.impl.OEntityAttributeManager;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IPrimitiveType.Primitive;
import org.komea.core.schema.IReference;
import org.komea.core.schema.impl.KomeaSchemaFactory;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
public class OAttributeManagerTests {
	private OrientVertex vertex;
	private final IKomeaSchemaFactory sfactory = new KomeaSchemaFactory();
	private OrientGraph graph;

	@Before
	public void init() {
		this.graph = new OrientGraph("memory:test", "admin", "admin");
		this.vertex = this.graph.addVertex(null);

	}

	@After
	public void end() {
		this.graph.shutdown();
	}

	@Test
	public void setPrimitiveReferenceTest() {
		IReference name = this.sfactory.newAttribute("name", Primitive.STRING);
		OEntityAttributeManager updater = new OEntityAttributeManager(
				this.vertex, name);
		updater.set("John Doe");
		String value = updater.get();
		assertEquals("John Doe", value);
	}
	
	@Test
	public void setPrimitiveValidationReferenceTest() {
		IReference name = this.sfactory.newAttribute("name", Primitive.STRING);
		OEntityAttributeManager updater = new OEntityAttributeManager(
				this.vertex, name);

		try{
			updater.set(1);
			//we can not set an integer in a string attribute
			fail();
		}catch(IllegalArgumentException e){
			//succeed
		}
	}
	
	@Test
	public void setPrimitiveCollectionReferenceTest() {
		IReference name = this.sfactory.newAttribute("values", Primitive.INTEGER).setMany(true);
		OEntityAttributeManager updater = new OEntityAttributeManager(
				this.vertex, name);
		updater.add(1);
		updater.add(2);
		List<Integer> values = updater.get();
		assertEquals(values.size(), 2);
	}
	
	@Test
	public void setPrimitiveCollectionValidationReferenceTest() {
		IReference name = this.sfactory.newAttribute("values", Primitive.INTEGER).setMany(true);
		OEntityAttributeManager updater = new OEntityAttributeManager(
				this.vertex, name);

		try{
			updater.add("a");
			//we can not add a String in an integer collection attribute
			fail();
		}catch(IllegalArgumentException e){
			//succeed
		}
	}
	
	
}
