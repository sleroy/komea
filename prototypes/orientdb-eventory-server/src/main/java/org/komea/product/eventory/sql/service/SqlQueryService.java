package org.komea.product.eventory.sql.service;

import java.text.MessageFormat;
import java.util.List;

import org.komea.product.eventory.database.model.AggregationFormula;
import org.komea.product.eventory.database.model.EntityValue;
import org.komea.product.eventory.database.session.api.IDocumentSessionFactory;
import org.komea.product.eventory.sql.api.ISqlQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

@Service
public class SqlQueryService implements ISqlQueryService {

	@Autowired
	private IDocumentSessionFactory dsf;

	@Override
	public List<EntityValue> aggregateOnPeriod(
			final AggregationFormula _sqlQuery) {
		final String sqlQuery = MessageFormat.format(
				"SELECT {0} as entity FROM {2}  GROUP BY {0}",
				_sqlQuery.getGroupBy(), _sqlQuery.getAggformula(),
				_sqlQuery.getFromClause(), _sqlQuery.getFilter(),
				_sqlQuery.getFrom(), _sqlQuery.getTo());
		final OSQLSynchQuery<ODocument> osqlSynchQuery = new OSQLSynchQuery<ODocument>(
				sqlQuery);
		final List<ODocument> documentList = this.dsf
				.getOrCreateDatabaseSession().query(osqlSynchQuery);
		final List<EntityValue> entityValues = Lists.newArrayList();
		System.out.println(documentList.size());
		for (final ODocument document : documentList) {
			final String entity = document.field("entity", String.class);
			final Double value = document.field("value", Double.class);
			entityValues.add(new EntityValue(entity, value));
		}
		return entityValues;
	}
}
