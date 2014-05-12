package org.komea.product.backend.service.kpi;

import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.dto.KpiResult;

/**
 * Default implementation of a query.
 * 
 * @author sleroy
 * 
 */
public class NoQueryImplementation implements IDynamicDataQuery {

	@Override
	public String getFormula() {

		return "";
	}

	@Override
	public KpiResult getResult() {

		return KpiResult.EMPTY;
	}

}
