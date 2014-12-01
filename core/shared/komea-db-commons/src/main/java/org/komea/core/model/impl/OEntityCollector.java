package org.komea.core.model.impl;

import java.util.List;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.ReferenceKind;

import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;

public class OEntityCollector {
	private final OKomeaEntity entity;

	public OEntityCollector(final OKomeaEntity entity) {
		super();
		this.entity = entity;
	}

	public long countAllContainedEntities(){
		return traverseCount(ReferenceKind.CONTAINMENT);
	}
	
	public long countAllAggregatedEntities(){
		return traverseCount(ReferenceKind.AGGREGATION);
	}
	
	public Iterable<IKomeaEntity> findAllContainedEntities() {
		return traverse(ReferenceKind.CONTAINMENT);
	}

	public Iterable<IKomeaEntity> findAllAggregatedEntities() {
		return traverse(ReferenceKind.AGGREGATION);
	}
	
	private long traverseCount(final ReferenceKind edgesType){
		String query = "SELECT count(*) FROM ("+buildTraverseQuery(edgesType)+")";
		OCommandRequest command = this.entity.getVertex().getGraph().getRawGraph()
				.command(new OCommandSQL(query));
		
		List<ODocument> result = command.execute();
	
		ODocument oDocument = result.get(0);
		return ((long)oDocument.field("count"))-1L;
	}

	private Iterable<IKomeaEntity> traverse(final ReferenceKind edgesType) {
		StringBuilder qbuilder = buildTraverseQuery(edgesType);
		OCommandRequest command = this.entity.getVertex().getGraph()
				.command(new OCommandSQL(qbuilder.toString()));
		Iterable<Vertex> results = command.execute();
		// first element is the element itself: skip it
		results.iterator().next();
		return new OEntityIterable(results.iterator(), this.entity.getType()
				.getSchema());
	}

	private StringBuilder buildTraverseQuery(final ReferenceKind edgesType) {
		StringBuilder qbuilder = new StringBuilder();
		qbuilder.append("traverse out('");
		qbuilder.append(edgesType);
		qbuilder.append("') from ");
		qbuilder.append(this.entity.getVertex().getId());
		qbuilder.append(" strategy BREADTH_FIRST");
		return qbuilder;
	}

}
