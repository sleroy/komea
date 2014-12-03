package org.komea.core.model.storage.impl;

import java.util.Collection;
import java.util.Set;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IReference;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.SchemaNamingConvention;
import org.komea.core.schema.impl.SchemaNamingConvention.EdgeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
public class OGraphSchemaUpdater {
    public static final String SEPARATOR = "_";
    public static final String TRUE = "true";
    public static final String INDEXED = "indexed";
    public static final String UNIQUE = "unique";
    public static final String MANDATORY = "mandatory";
    public static final String TYPE = "type";
    
	private final OrientGraph graph;
	private final static Logger LOGGER = LoggerFactory
			.getLogger("Orient DB schema updater");

	public OGraphSchemaUpdater(final OrientGraph graph) {
		super();
		this.graph = graph;
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

	private void addProperties(final OrientVertexType vtype,
			final IEntityType type) {
		for (final IReference property : type.getProperties()) {
			//we only need to create properties for attributes (references on primitive types)
			if (isAttribute(property)) {
				final OProperty oproperty = createAttribute(vtype, type,
						property);
				oproperty.setMandatory(property.isMandatory());
			}
		}
	}

	private boolean isAttribute(final IReference property) {
		return property.getType().isPrimitive();
	}

	private OProperty createAttribute(final OrientVertexType vtype,
			final IEntityType type, final IReference property) {

		OProperty oproperty;

		if (property.isMany()) {
			LOGGER.trace("Create {} list for <{}> property of {}",
					property.getType(), property.getName(), type.getName());
			oproperty = vtype.createProperty(property.getName(),
					OType.EMBEDDEDLIST);
		} else {
			LOGGER.trace("Create {} attribute for <{}> property of {}",
					property.getType(), property.getName(), type.getName());
			oproperty = vtype.createProperty(property.getName(),
					OType.valueOf(property.getType().getName().toUpperCase()));
		}
		if(property.isUnique()){
			oproperty.createIndex(OClass.INDEX_TYPE.UNIQUE);
		}
		else if(property.isIndexed()){
			oproperty.createIndex(OClass.INDEX_TYPE.NOTUNIQUE);
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

	private void initializeEdgesTypes(final IKomeaSchema schema) {

		String refEdge = ReferenceKind.REFERENCE.name();
		final OrientEdgeType reference = this.graph.getEdgeType(refEdge);
		if (reference == null) {
			this.graph.createEdgeType(refEdge);
		}
		String contEdge = ReferenceKind.CONTAINMENT.name();
		final OrientEdgeType containment = this.graph
				.getEdgeType(contEdge);
		if (containment == null) {
			this.graph.createEdgeType(contEdge);
		}
		String aggEdge = ReferenceKind.AGGREGATION.name();
		final OrientEdgeType aggregation = this.graph
				.getEdgeType(aggEdge);
		if (aggregation == null) {
			this.graph.createEdgeType(aggEdge);
		}

		for (final IEntityType type : schema.getTypes()) {
			initializeReferencesEdgesTypes(type);

		}
	}

	/**
	 * Create all edges types for the entity references
	 * 
	 * @param type
	 */
	private void initializeReferencesEdgesTypes(final IEntityType type) {
		for (IReference property : type.getProperties()) {
			if (!isAttribute(property)) {
				String fqn = etype(type, property);
				if (this.graph.getEdgeType(fqn) != null) {
					this.graph.dropEdgeType(fqn);
				}
				this.graph.createEdgeType(fqn, findEdgeBaseType(property));
			}
		}
	}

	/**
	 * Returns the type of an edge reference.
	 * 
	 * @param type
	 * @param reference
	 * @return
	 */
	public static String etype(final IEntityType type,
			final IReference reference) {
		return SchemaNamingConvention.formatEdgeType(new EdgeDefinition(type.getName(), reference.getName()));
	}

	private static String findEdgeBaseType(final IReference reference) {
		return reference.getKind().name();
	}

	private void initializeTypes(final IKomeaSchema schema) {
		final Set<IEntityType> updated = Sets.newHashSet();
		for (final IEntityType type : schema.getTypes()) {
			getOrUpdateType(type, updated);
		}
	}

	public void update(final IKomeaSchema schema) {
		final boolean tx = this.graph.isAutoStartTx();
		this.graph.setAutoStartTx(false);
		this.graph.getRawGraph().commit(true);

		initializeEdgesTypes(schema);
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
