package org.komea.core.model.query.integration.tests;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.integration.tests.AbstractIntegrationTest;
import org.komea.core.model.query.impl.OEntitiesQueryManager;
import org.komea.core.schema.IEntityType;

public class OEntitiesQueryManagerTests extends AbstractIntegrationTest{


	@Test
	public void countTest() {
		IEntityType type = this.schema.findType("Person");

		IKomeaEntity p1 = this.mfactory.newInstance(type);
		p1.set("name", "John");

		IKomeaEntity p2 = this.mfactory.newInstance(type);
		p2.set("name", "Bob");
		p1.add("children", p2);

		IKomeaEntity p3 = this.mfactory.newInstance(type);
		p3.set("name", "Bobby");
		p2.add("children", p3);
		
		this.storage.commit();

		OEntitiesQueryManager queryManager =  new OEntitiesQueryManager(this.storage);
		
		assertEquals(3, queryManager.count());
	}
}
