package org.komea.core.model.integration.tests;

import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.model.impl.OEntityCollector;
import org.komea.core.model.impl.OKomeaEntity;
import org.komea.core.schema.IEntityType;

import static junit.framework.Assert.*;

public class OEntityCollectorTests extends AbstractIntegrationTest {

	@Test
	public void aggregationTest() {
		IEntityType type = this.schema.findType("Person");

		IKomeaEntity p1 = this.mfactory.newInstance(type);
		p1.set("name", "John");

		IKomeaEntity p2 = this.mfactory.newInstance(type);
		p2.set("name", "Bob");
		p1.add("children", p2);

		IKomeaEntity p3 = this.mfactory.newInstance(type);
		p3.set("name", "Bobby");
		p2.add("children", p3);

		OEntityCollector collector = new OEntityCollector((OKomeaEntity) p1);
		Iterable<IKomeaEntity> allChildren = collector
				.findAllAggregatedEntities();
		int count = 0;
		Iterator<IKomeaEntity> iterator = allChildren.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			count++;

		}

		assertEquals(2, count);
	}

	@Test
	@Ignore("Perfs tests")
	public void aggregationPerfsTest() {
		IEntityType type = this.schema.findType("Person");

		IKomeaEntity parent = this.mfactory.newInstance(type);
		parent.set("name", "person" + 0);
		int depth = 20;
		int nbChildren = 2000;
		ChildrenGenerator generator = new ChildrenGenerator(depth, nbChildren,
				type, this.mfactory);
		generator.generateChildren(parent);

		OEntityCollector collector = new OEntityCollector((OKomeaEntity) parent);
		long t1 = System.currentTimeMillis();
		long count = collector.countAllAggregatedEntities();
		long t2 = System.currentTimeMillis();
		int expected = generator.id-1;
		System.out.println("Fetching "+expected+" aggregations in " + (t2 - t1) + " ms");

		assertEquals(expected, count);

	}

	private static class ChildrenGenerator {
		private final int maxDepth;
		private final int nbChildren;
		private final IEntityType type;
		private int id=1;
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
					IKomeaEntity child = this.mFactory.newInstance(this.type);
					child.set("name", "person" + this.id++);
					parent.add("children", child);
					generateChildren(child);
				}
			}
		}
	}

}
