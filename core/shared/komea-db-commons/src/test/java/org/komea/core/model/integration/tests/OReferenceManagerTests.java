package org.komea.core.model.integration.tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.OEntityReferenceManager;
import org.komea.core.model.impl.OKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;

public class OReferenceManagerTests extends AbstractIntegrationTest {
	private IEntityType	type;

	@Test
	public void addEntityReferenceTest() {
		final OKomeaEntity p1 = (OKomeaEntity) this.getMfactory().create(this.type);
		final OKomeaEntity p2 = (OKomeaEntity) this.getMfactory().create(this.type);

		final IReference property = this.type.findProperty("family");
		final OEntityReferenceManager updater = new OEntityReferenceManager(p1, property);
		updater.addReference(p2);
		final Iterable<IKomeaEntity> family = updater.get();
		Assert.assertTrue(family.iterator().hasNext());
	}

	@Override
	@After
	public void end() throws IOException {
		this.getSessionsFactory().getGraph().drop();
		this.getSessionsFactory().close();
	}

	@Test
	public void setEntityReferenceTest() {
		final OKomeaEntity p1 = (OKomeaEntity) this.getMfactory().create(this.type);
		final OKomeaEntity p2 = (OKomeaEntity) this.getMfactory().create(this.type);

		final IReference property = this.type.findProperty("partner");
		final OEntityReferenceManager updater = new OEntityReferenceManager(p1, property);
		updater.set(p2);

		Assert.assertEquals(p2, updater.get());
	}

	@Override
	protected void initSchema() {
		this.type = this.getSchemaFactory().newEntity("Person");
		final IReference name = this.getSchemaFactory().newAttribute("name", Primitive.STRING);
		this.type.addProperty(name);
		final IReference references = this.getSchemaFactory().newReference("children", this.type)
				.setArity(ReferenceArity.MANY).setKind(ReferenceKind.AGGREGATION);
		this.type.addProperty(references);
		this.getSchema().addType(this.type);
		this.type = this.getSchemaFactory().newEntity("Person");
		this.type.addProperty(this.getSchemaFactory().newAttribute("name", Primitive.STRING));
		this.type.addProperty(this.getSchemaFactory().newReference("partner", this.type));
		this.type.addProperty(this.getSchemaFactory().newReference("family", this.type).setArity(ReferenceArity.MANY));

		this.getSchema().addType(this.type);

	}
}
