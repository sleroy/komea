package org.komea.core.model.query.integration.tests;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.integration.tests.AbstractIntegrationTest;
import org.komea.core.model.query.impl.KomeaQueryException;
import org.komea.core.model.query.impl.OEntitiesQueryManager;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;

import com.google.common.collect.Iterables;

public class OEntitiesQueryManagerTests extends AbstractIntegrationTest {

	@Test
	public void countTest() throws IOException {

		final OEntitiesQueryManager queryManager = new OEntitiesQueryManager(this.getStorage());

		assertEquals(3, queryManager.count());
		queryManager.close();
	}

	@Test
	public void countTypeTest() throws IOException {
		final IEntityType type = this.getSchema().findType("Person");

		final OEntitiesQueryManager queryManager = new OEntitiesQueryManager(this.getStorage());

		assertEquals(3, queryManager.count(type));
		queryManager.close();
	}

	@Before
	public void initData() {
		super.init();
		final IEntityType type = this.getSchema().findType("Person");

		final IKomeaEntity p1 = this.getMfactory().create(type);
		p1.set("name", "John");

		final IKomeaEntity p2 = this.getMfactory().create(type);
		p2.set("name", "Bob");
		p1.add("children", p2);

		final IKomeaEntity p3 = this.getMfactory().create(type);
		p3.set("name", "Bobby");
		p2.add("children", p3);
		this.getStorage().commit();

	}

	@Test
	public void selectTest() throws IOException {

		final OEntitiesQueryManager queryManager = new OEntitiesQueryManager(this.getStorage());
		try {
			queryManager.select("A buggy query");
			fail();
		} catch (final KomeaQueryException e) {

		}
		final Iterable<IKomeaEntity> selected = queryManager.select("SELECT * FROM Person");

		assertEquals(3, Iterables.size(selected));
		queryManager.close();
	}

	@Override
	protected void initSchema() {
		final IEntityType type = getSchemaFactory().newEntity("Person");
		final IReference name = getSchemaFactory().newAttribute("name", Primitive.STRING);
		type.addProperty(name);
		final IReference references = getSchemaFactory().newReference("children", type).setArity(ReferenceArity.MANY)
				.setKind(ReferenceKind.AGGREGATION);
		type.addProperty(references);
		this.getSchema().addType(type);

	}

}
