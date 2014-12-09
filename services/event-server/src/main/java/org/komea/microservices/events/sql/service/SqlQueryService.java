package org.komea.microservices.events.sql.service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.komea.microservices.events.database.model.AggregationFormula;
import org.komea.microservices.events.database.model.EntityValue;
import org.komea.microservices.events.sql.api.ISqlQueryRepository;
import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.document.IODocumentToolbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository
public class SqlQueryService implements ISqlQueryRepository {

	@Autowired
	private IODocumentToolbox	odt;

	@Override
	public List<EntityValue> aggregateOnPeriod(final AggregationFormula _sqlQuery) {
		final String sqlQuery = MessageFormat.format("SELECT {0} as entity, {1} as value FROM {2}  GROUP BY eventKey",
		        _sqlQuery.getGroupBy(), _sqlQuery.getAggformula(), _sqlQuery.getFromClause(), _sqlQuery.getFilter(),
		        _sqlQuery.getFrom(), _sqlQuery.getTo());

		final Iterator<IODocument> documentList = this.odt.query(sqlQuery);
		final List<EntityValue> entityValues = Lists.newArrayList();
		while (documentList.hasNext()) {
			final IODocument document = documentList.next();
			final String entity = document.getField("entity", String.class);
			final Double value = document.getField("value", Double.class);
			entityValues.add(new EntityValue(entity, value));
		}

		return entityValues;
	}
}
