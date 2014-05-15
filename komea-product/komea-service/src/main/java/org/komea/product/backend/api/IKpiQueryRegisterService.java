package org.komea.product.backend.api;

import org.komea.product.database.model.Kpi;

/**
 * This interface hides all the mechanism required to register and initialize a
 * new query inside komea.
 * 
 * @author sleroy
 * 
 */
public interface IKpiQueryRegisterService {

	/**
	 * Registers a query.
	 * 
	 * @param _kpi
	 * @param _formula
	 * @param _queryImplementation
	 */
	public void registerQuery(Kpi _kpi, Object _queryImplementation);

}
