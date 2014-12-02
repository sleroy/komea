package org.komea.core.model.integration.tests;

import static junit.framework.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.model.impl.OEntityCollector;
import org.komea.core.model.impl.OKomeaEntity;
import org.komea.core.schema.IEntityType;

public class OEntityCollectorTests extends AbstractIntegrationTest {

	private static class ChildrenGenerator {
		private final int maxDepth;
		private final int nbChildren;
		private final IEntityType type;
		private int id = 1;
		private final IKomeaEntityFactory mFactory;
		private int depth;

		public ChildrenGenerator(final int maxDepth, final int nbChildren,
				final IEntityType type, final IKomeaEntityFactory mFactory) {
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
					generateChildren(child);
				}
			}
		}
	}

	@Test
	@Ignore("Perfs tests")
	public void aggregationPerfsTest() {
		final IEntityType type = this.schema.findType("Person");

		final IKomeaEntity parent = this.mfactory.create(type);
		parent.set("name", "person" + 0);
		final int depth = 20;
		final int nbChildren = 2000;
		final ChildrenGenerator generator = new ChildrenGenerator(depth,
				nbChildren, type, this.mfactory);
		generator.generateChildren(parent);

		final OEntityCollector collector = new OEntityCollector(
				(OKomeaEntity) parent);
		final long t1 = System.currentTimeMillis();
		final long count = collector.countAllAggregatedEntities();
		final long t2 = System.currentTimeMillis();
		final int expected = generator.id - 1;
		System.out.println("Fetching " + expected + " aggregations in "
				+ (t2 - t1) + " ms");

		assertEquals(expected, count);

	}

	@Test
	public void aggregationTest() {
		final IEntityType type = this.schema.findType("Person");

		final IKomeaEntity p1 = this.mfactory.create(type);
		p1.set("name", "John");

		final IKomeaEntity p2 = this.mfactory.create(type);
		p2.set("name", "Bob");
		p1.add("children", p2);

		final IKomeaEntity p3 = this.mfactory.create(type);
		p3.set("name", "Bobby");
		p2.add("children", p3);

		final OEntityCollector collector = new OEntityCollector(
				(OKomeaEntity) p1);
		final Iterable<IKomeaEntity> allChildren = collector
				.findAllAggregatedEntities();
		int count = 0;
		final Iterator<IKomeaEntity> iterator = allChildren.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			count++;

		}

		assertEquals(2, count);
	}

}
