package org.komea.product.backend.service.kpi;

import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.exceptions.KpiProvidesInvalidFormulaException;
import org.komea.product.backend.service.esper.ConvertELIntoQuery;
import org.komea.product.database.model.Kpi;

public class InstantiateQueryFromFormula {

	private final Kpi	kpi;

	public InstantiateQueryFromFormula(final Kpi _kpi) {

		super();
		kpi = _kpi;
	}

	public IQuery instantiate() {

		final String formula = kpi.getEsperRequest();
		if (!ConvertELIntoQuery.isValidFormula(formula)) { throw new KpiProvidesInvalidFormulaException(kpi); }
		return ConvertELIntoQuery.parseEL(formula);
	}
}
