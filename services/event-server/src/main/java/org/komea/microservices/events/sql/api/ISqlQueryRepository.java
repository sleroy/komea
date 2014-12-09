package org.komea.microservices.events.sql.api;

import java.util.List;

import org.komea.microservices.events.database.model.AggregationFormula;
import org.komea.microservices.events.database.model.EntityValue;

public interface ISqlQueryRepository {

	List<EntityValue> aggregateOnPeriod(AggregationFormula _sqlQuery);

}
