package org.komea.core.model.query.integration.tests;

import static junit.framework.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.integration.tests.AbstractIntegrationTest;
import org.komea.core.model.query.impl.KomeaQueryException;
import org.komea.core.model.query.impl.OEntitiesQueryManager;
import org.komea.core.schema.IEntityType;

import com.google.common.collect.Iterables;

public class OEntitiesQueryManagerTests extends AbstractIntegrationTest {

	@Before
	public void initData() {
		IEntityType type = this.schema.findType("Person");

		IKomeaEntity p1 = this.mfactory.create(type);
		p1.set("name", "John");

		IKomeaEntity p2 = this.mfactory.create(type);
		p2.set("name", "Bob");
		p1.add("children", p2);

		IKomeaEntity p3 = this.mfactory.create(type);
		p3.set("name", "Bobby");
		p2.add("children", p3);
		this.storage.commit();

	}

	@Test
	public void countTest() throws IOException {

		OEntitiesQueryManager queryManager = new OEntitiesQueryManager(
				this.storage);

		assertEquals(3, queryManager.count());
		queryManager.close();
	}

	@Test
	public void countTypeTest() throws IOException {
		IEntityType type = this.schema.findType("Person");

		OEntitiesQueryManager queryManager = new OEntitiesQueryManager(
				this.storage);

		assertEquals(3, queryManager.count(type));
		queryManager.close();
	}

	@Test
	public void selectTest() throws IOException {

		OEntitiesQueryManager queryManager = new OEntitiesQueryManager(
				this.storage);
		try {
			queryManager.select("A buggy query");
			fail();
		} catch (KomeaQueryException e) {

		}
		Iterable<IKomeaEntity> selected = queryManager
				.select("SELECT * FROM Person");

		assertEquals(3, Iterables.size(selected));
		queryManager.close();
	}

}
