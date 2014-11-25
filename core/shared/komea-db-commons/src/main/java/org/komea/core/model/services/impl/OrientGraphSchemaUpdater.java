package org.komea.core.model.services.impl;

import java.util.Collection;
import java.util.Set;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class OrientGraphSchemaUpdater {
	public static final String REFERENCE_EDGE = "ref";
	public static final String CONTAINMENT_EDGE = "contains";
	private final OrientGraph graph;
	private final static Logger LOGGER = LoggerFactory
			.getLogger("Orient DB schema updater");

	public OrientGraphSchemaUpdater(final OrientGraph graph) {
		super();
		this.graph = graph;
	}

	private void addProperties(final OrientVertexType vtype,
			final IEntityType type) {
		for (final IReference property : type.getProperties()) {
			final OProperty oproperty = createProperty(vtype, type, property);
			oproperty.setMandatory(property.isMandatory());
		}
	}

	private OrientVertexType create(final IEntityType type,
			final Set<IEntityType> updated) {
		LOGGER.trace("Create a new Orient DB vertex type for {}",
				type.getName());
		final IEntityType superType = type.getSuperType();
		OrientVertexType vtype;
		if (superType != null) {
			final OrientVertexType superOrientType = getOrUpdateType(superType,
					updated);
			vtype = this.graph
					.createVertexType(type.getName(), superOrientType);
		} else {
			vtype = this.graph.createVertexType(type.getName());
		}
		addProperties(vtype, type);
		updated.add(type);
		return vtype;
	}

	private OProperty createProperty(final OrientVertexType vtype,
			final IEntityType type, final IReference property) {
		OProperty oproperty;
		if (property.getType().isPrimitive()) {

			if (property.isMany()) {
				LOGGER.trace("Create {} list for <{}> property of {}",
						property.getType(), property.getName(), type.getName());
				oproperty = vtype.createProperty(property.getName(),
						OType.EMBEDDEDLIST);
			} else {
				LOGGER.trace("Create {} attribute for <{}> property of {}",
						property.getType(), property.getName(), type.getName());
				oproperty = vtype.createProperty(
						property.getName(),
						OType.valueOf(property.getType().getName()
								.toUpperCase()));
			}

		} else {
			LOGGER.trace("Create {} reference for <{}> property of {}",
					property.getType(), property.getName(), type.getName());
			oproperty = vtype.createEdgeProperty(Direction.OUT,
					property.getName());
		}
		return oproperty;
	}

	private OrientVertexType getOrUpdateType(final IEntityType type,
			final Set<IEntityType> updated) {
		OrientVertexType vertexType = this.graph.getVertexType(type.getName());
		if (vertexType == null) {
			vertexType = create(type, updated);
		} else {
			if (!updated.contains(type)) {
				vertexType = update(vertexType, type);
			}
		}
		return vertexType;
	}

	private void initializeEdges() {
		final OrientEdgeType reference = this.graph.getEdgeType(REFERENCE_EDGE);
		if (reference == null) {
			this.graph.createEdgeType(REFERENCE_EDGE);
		}
		final OrientEdgeType containment = this.graph
				.getEdgeType(CONTAINMENT_EDGE);
		if (containment == null) {
			this.graph.createEdgeType(CONTAINMENT_EDGE);
		}

	}

	private void initializeTypes(final IKomeaSchema schema) {
		final Set<IEntityType> updated = Sets.newHashSet();
		for (final IEntityType type : schema.getEntities()) {
			getOrUpdateType(type, updated);
		}
	}

	public void update(final IKomeaSchema schema) {
		final boolean tx = this.graph.isAutoStartTx();
		this.graph.setAutoStartTx(false);
		this.graph.getRawGraph().commit(true);

		initializeEdges();

		initializeTypes(schema);
		this.graph.setAutoStartTx(tx);
	}

	private OrientVertexType update(final OrientVertexType vtype,
			final IEntityType type) {
		LOGGER.trace("Update existing Orient DB vertex type for {}",
				type.getName());

		// drop all previous properties
		final Collection<OProperty> properties = vtype.properties();
		for (final OProperty oProperty : properties) {
			if (vtype.existsProperty(oProperty.getName())) {
				vtype.dropProperty(oProperty.getName());
			}
		}

		addProperties(vtype, type);
		return vtype;
	}

}
