package org.komea.eventory.api.engine;

public interface IQueryVisitor {

	/**
	 * Visits cep query.
	 * 
	 * @param _query
	 */
	void visit(final ICEPQuery _query);

	void visit(IDynamicDataQuery _query);
}
