package org.komea.product.eventory.sql.api;

import java.util.List;

import org.komea.product.eventory.database.model.AggregationFormula;
import org.komea.product.eventory.database.model.EntityValue;

public interface ISqlQueryService {

	List<EntityValue> aggregateOnPeriod(AggregationFormula _sqlQuery);

}
