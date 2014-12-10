package org.komea.core.model.integration.tests;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.model.impl.OEntityCollector;
import org.komea.core.model.impl.OKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;

import com.google.common.collect.Iterables;

public class OEntityCollectorTests extends AbstractIntegrationTest {

	private static class ChildrenGenerator {
		private final int		          maxDepth;
		private final int		          nbChildren;
		private final IEntityType		  type;
		private int		                  id	= 1;
		private final IKomeaEntityFactory	mFactory;
		private int		                  depth;

		public ChildrenGenerator(final int maxDepth, final int nbChildren, final IEntityType type,
				final IKomeaEntityFactory mFactory) {
			super();
			this.maxDepth = maxDepth;
			this.nbChildren = nbChildren;
			this.type = type;
			this.mFactory = mFactory;
		}

		private void generateChildren(final IKomeaEntity parent) {
			this.depth++;
			if (this.depth <= this.maxDepth) {
				for (int j = 0; j <= this.nbChildren; j++) {
					final IKomeaEntity child = this.mFactory.create(this.type);
					child.set("name", "person" + this.id++);
					parent.add("children", child);
					this.generateChildren(child);
				}
			}
		}
	}

	@Test
	public void aggregationCountTest() {
		final IEntityType type = this.getSchema().findType("Person");

		final IKomeaEntity p1 = this.getMfactory().create(type);
		p1.set("name", "John");

		final IKomeaEntity p2 = this.getMfactory().create(type);
		p2.set("name", "Bob");
		p1.add("children", p2);

		final IKomeaEntity p3 = this.getMfactory().create(type);
		p3.set("name", "Bobby");
		p2.add("children", p3);

		final OEntityCollector collector = new OEntityCollector((OKomeaEntity) p1);

		assertEquals(2, collector.countAllAggregatedEntities());
	}

	@Test
	// @Ignore("Perfs tests")
	public void aggregationPerfsTest() {
		System.out.println("Init");
		final IEntityType type = this.getSchema().findType("Person");
		System.out.println("Step1");
		final IKomeaEntity parent = this.getMfactory().create(type);
		parent.set("name", "person" + 0);
		final int depth = 20;
		final int nbChildren = 2000;
		final ChildrenGenerator generator = new ChildrenGenerator(depth, nbChildren, type, this.getMfactory());
		generator.generateChildren(parent);
		System.out.println("Step2");
		final OEntityCollector collector = new OEntityCollector((OKomeaEntity) parent);
		final long t1 = System.currentTimeMillis();
		final long count = collector.countAllAggregatedEntities();
		final long t2 = System.currentTimeMillis();
		final int expected = generator.id - 1;
		System.out.println("Fetching " + expected + " aggregations in " + (t2 - t1) + " ms");

		assertEquals(expected, count);

	}

	@Test
	public void aggregationTest() {
		final IEntityType type = this.getSchema().findType("Person");

		final IKomeaEntity p1 = this.getMfactory().create(type);
		p1.set("name", "John");

		final IKomeaEntity p2 = this.getMfactory().create(type);
		p2.set("name", "Bob");
		p1.add("children", p2);

		final IKomeaEntity p3 = this.getMfactory().create(type);
		p3.set("name", "Bobby");
		p2.add("children", p3);

		final OEntityCollector collector = new OEntityCollector((OKomeaEntity) p1);
		final Iterable<IKomeaEntity> allChildren = collector.findAllAggregatedEntities();

		assertEquals(2, Iterables.size(allChildren));
	}

	@Override
	protected void initSchema() {

		final IEntityType type = this.getSchemaFactory().newEntity("Person");
		final IReference name = this.getSchemaFactory().newAttribute("name", Primitive.STRING);
		type.addProperty(name);
		final IReference references = this.getSchemaFactory().newReference("children", type)
				.setArity(ReferenceArity.MANY).setKind(ReferenceKind.AGGREGATION);
		type.addProperty(references);
		this.getSchema().addType(type);

	}

}
