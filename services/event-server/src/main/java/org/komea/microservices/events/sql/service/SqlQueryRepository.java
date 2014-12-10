package org.komea.microservices.events.sql.service;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.komea.microservices.events.database.model.AggregationFormula;
import org.komea.microservices.events.database.model.EntityValue;
import org.komea.microservices.events.sql.api.ISqlQueryRepository;
import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.document.IODocumentToolbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository
public class SqlQueryRepository implements ISqlQueryRepository {

	@Autowired
	private IODocumentToolbox	odt;

	private static final Logger	LOGGER	= LoggerFactory.getLogger(SqlQueryRepository.class);

	@Override
	public List<EntityValue> aggregateOnPeriod(final AggregationFormula _sqlQuery) {

		final StringBuilder sb = new StringBuilder();
		sb.append(MessageFormat.format("SELECT {0} as entity, {1} as value FROM {2}", _sqlQuery.getGroupBy(),
		        _sqlQuery.getAggformula(), _sqlQuery.getFromClause()));
		sb.append(MessageFormat.format(" WHERE date BETWEEN (\'{0}\' AND \'{1}\')", _sqlQuery.getFrom(),
		        _sqlQuery.getTo()));
		if (!_sqlQuery.getFilter().isEmpty()) {
			sb.append(" AND (" + _sqlQuery.getFilter() + ") ");
		}
		sb.append(" GROUP BY ").append(_sqlQuery.getGroupBy());
		final String sqlQuery = sb.toString();
		LOGGER.info("Querying OrientDB : {}", sqlQuery);
		final List<EntityValue> entityValues = Lists.newArrayList();
		try {
			final Iterator<IODocument> documentList = this.odt.query(sqlQuery);
			while (documentList.hasNext()) {
				final IODocument document = documentList.next();
				final String entity = document.getField("entity", String.class);
				final Double value = document.getField("value", Double.class);
				entityValues.add(new EntityValue(entity, value));
			}
		} catch (final Exception e) {
			LOGGER.error("Error with the query {}", sqlQuery, e);
		}
		return entityValues;

	}
}
