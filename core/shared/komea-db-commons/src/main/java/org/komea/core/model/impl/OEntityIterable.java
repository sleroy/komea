package org.komea.core.model.impl;

import java.util.Iterator;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;

import com.google.common.collect.AbstractIterator;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class OEntityIterable implements Iterable<IKomeaEntity>{
	
	private final Iterator<Vertex> vertices;
	private final IKomeaSchema schema;
	private Iterator<IKomeaEntity> iterator;

	public OEntityIterable(final Iterator<Vertex> vertices,
			final IKomeaSchema schema) {
		super();
		this.vertices = vertices;
		this.schema = schema;
	}

	@Override
	public Iterator<IKomeaEntity> iterator() {
		if (this.iterator == null) {
			this.iterator = new KomeaEntityIterator(this.vertices, this.schema);
		}
		return this.iterator;
	}
	
	private static class KomeaEntityIterator extends AbstractIterator<IKomeaEntity> {
		private final Iterator<Vertex> vertices;
		private final IKomeaSchema schema;

		public KomeaEntityIterator(final Iterator<Vertex> vertices,
				final IKomeaSchema schema) {
			super();
			this.vertices = vertices;
			this.schema = schema;
		}

		@Override
		protected IKomeaEntity computeNext() {
			if (this.vertices.hasNext()) {
				OrientVertex next = (OrientVertex) this.vertices.next();
				IEntityType type = this.schema.findType(next.getLabel());
				return new OKomeaEntity(type, next);
			}
			return endOfData();
		}
	}

}
