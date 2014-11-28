package org.komea.core.model.impl;

import java.util.Collection;
import java.util.Iterator;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.impl.OGraphSchemaUpdater;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IReference;

import com.google.common.collect.AbstractIterator;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class OEntityReferenceManager extends AbstractPropertyManager {
	private final IKomeaSchema schema;
	private final OKomeaEntity owner;

	public OEntityReferenceManager(final OKomeaEntity entity,
			final IReference reference) {
		super(entity.getVertex(), reference);
		this.schema = entity.getType().getSchema();
		this.owner = entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get() {
		DestinationsIterable iterable = new DestinationsIterable(edges(),
				this.schema);
		if (this.reference.isMany()) {
			return (T) iterable;
		} else {
			Iterator<IKomeaEntity> iterator = iterable.iterator();
			if (iterator.hasNext()) {
				return (T) iterator.next();
			} else {
				return null;
			}
		}
	}

	private Iterator<Edge> edges() {
		return this.vertex.getEdges(Direction.OUT, etype()).iterator();
	}

	@Override
	protected void doSet(final Object value) {
		doAdd(value);
	}

	@Override
	public void remove(final Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doAdd(final Object value) {
		OKomeaEntity target = (OKomeaEntity) value;
		this.vertex.addEdge(etype(), target.getVertex());

	}

	private String etype() {
		return OGraphSchemaUpdater.etype(this.owner.getType(), this.reference);
	}

	

	@Override
	protected void doAddAll(final Collection<?> input) {
		for (Object object : input) {
			add(object);
		}
	}

	private static class DestinationsIterable implements
			Iterable<IKomeaEntity> {
		private final Iterator<Edge> edges;
		private final IKomeaSchema schema;
		private Iterator<IKomeaEntity> iterator;

		public DestinationsIterable(final Iterator<Edge> edges,
				final IKomeaSchema schema) {
			super();
			this.edges = edges;
			this.schema = schema;
		}

		@Override
		public Iterator<IKomeaEntity> iterator() {
			if (this.iterator == null) {
				this.iterator = new DestinationsIterator(this.edges, this.schema);
			}
			return this.iterator;
		}
	}

	private static class DestinationsIterator extends
			AbstractIterator<IKomeaEntity> {
		private final Iterator<Edge> edges;
		private final IKomeaSchema schema;

		public DestinationsIterator(final Iterator<Edge> edges,
				final IKomeaSchema schema) {
			super();
			this.edges = edges;
			this.schema = schema;
		}

		@Override
		protected IKomeaEntity computeNext() {
			if (this.edges.hasNext()) {
				Edge next = this.edges.next();
				OrientVertex target = (OrientVertex) next
						.getVertex(Direction.IN);
				IEntityType type = this.schema.findType(target.getLabel());
				return new OKomeaEntity(type, target);
			}
			return endOfData();

		}
	}

}
