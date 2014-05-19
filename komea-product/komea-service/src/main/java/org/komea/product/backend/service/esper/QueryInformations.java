/**
 * 
 */

package org.komea.product.backend.service.esper;

import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.engine.IQuery;
import org.komea.eventory.query.CEPQuery;
import org.komea.product.backend.api.IQueryInformations;

/**
 * This class defines the required data to build a new cep query.
 * 
 * @author sleroy
 */
public class QueryInformations implements IQueryInformations {

	private IQuery	implementation;
	private String	queryName;

	/**
     *  
     */
	public QueryInformations() {

		super();
	}

	public QueryInformations(final String _queryName, final ICEPQueryImplementation _queryImplementation) {
		this(_queryName, new CEPQuery(_queryImplementation));
	}

	public QueryInformations(final String _queryName, final IQuery _queryImplementation) {

		queryName = _queryName;
		implementation = _queryImplementation;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.product.backend.api.IQueryDefinition#getImplementation()
	 */
	@Override
	public IQuery getImplementation() {

		return implementation;
	}

	@Override
	public String getQueryName() {

		return queryName;
	}

	public void setImplementation(final IQuery _implementation) {

		implementation = _implementation;
	}

	public void setQueryName(final String _queryName) {

		queryName = _queryName;
	}

}
