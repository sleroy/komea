package org.komea.experimental.kpis;

import java.util.Date;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.executor.QueryExecutor;
import org.komea.event.queries.executor.QueryResult;
import org.komea.event.queries.impl.EventQuery;
import org.komea.event.queries.predicate.FieldPredicate;
import org.komea.event.queries.rows.IRow;
import org.komea.event.storage.IEventDBFactory;
import org.komea.experimental.model.AnalyzedApplication;
import org.komea.experimental.prediction.Release;
import org.komea.experimental.prediction.ReleaseCodeChunk;
import org.komea.orientdb.session.document.IODocument;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class CodeChunkPerRelease
{
	
	private final IEventDBFactory	  eventDBFactory;
	private final AnalyzedApplication	application;
	
	public CodeChunkPerRelease(final IEventDBFactory _eventDbFactory,
	        final AnalyzedApplication application) {
		
		super();
		eventDBFactory = _eventDbFactory;
		this.application = application;
	}
	
	public ReleaseCodeChunk chunk(final String release) {
		
		final Date releaseDate = findReleaseDate(release);
		if (releaseDate != null) {
			final Release previous = findPreviousRelease(releaseDate);
			final int chunk = chunk(previous.getDate(), releaseDate);
			return new ReleaseCodeChunk(new Release(release, releaseDate),
			        previous, chunk);
		}
		return null;
	}
	
	public Predicate<FlatEvent> clause(final AnalyzedApplication app) {
		
		return Predicates.and(FieldPredicate.build("project", app.getName()),
		        FieldPredicate.build("branch", app.getBranch()));
	}
	
	public String pathClause(final AnalyzedApplication app) {
		
		final StringBuilder sb = new StringBuilder();
		for (final String path : app.getSourcePaths()) {
			sb.append(" AND file LIKE ").append("'").append(path).append("%' ");
		}
		return sb.toString();
	}
	
	private int chunk(final Date previous, final Date releaseDate) {
		
		final String query = "SELECT SUM(total_updated_lines) FROM file_update WHERE "
		        + clause(application)
		        + " AND date < '"
		        + format(releaseDate)
		        + "' AND  date > '"
		        + format(previous)
		        + "'" + pathClause(application);
		final Iterator<IODocument> tags = eventDBFactory.query(query);
		if (tags.hasNext()) {
			final IODocument tag = tags.next();
			return tag.getField("SUM", Integer.class);
		}
		return 0;
	}
	
	private Release findPreviousRelease(final Date date) {

		final EventQuery eventQuery = new EventQuery();
		eventQuery.eventTypes("tag");
		eventQuery.until(new DateTime(date));
		eventQuery.where(clause(application));
		eventQuery.returnsEvents();
		
		final QueryExecutor queryExecutor = new QueryExecutor(eventDBFactory,
				eventQuery);
		final QueryResult result = queryExecutor.execute();

		final String query = "SELECT * FROM tag WHERE " + clause(application)
		        + "AND date < '" + format(date)
		        + "' ORDER BY date DESC LIMIT 1";
		final Iterator<IODocument> tags = eventDBFactory.query(query);
		if (tags.hasNext()) {
			final IODocument tag = tags.next();
			final Date rdate = tag.getField("date", Date.class);
			return new Release(tag.getField("name", String.class), rdate);
		}
		return null;
	}
	
	private Date findReleaseDate(final String release) {
		final EventQuery eventQuery = new EventQuery();
		eventQuery.from("tag");
		eventQuery.where(Predicates.and(clause(application),
				new FieldPredicate("name", release)));
		eventQuery.limit(1);
		eventQuery.returnsEvents();
		
		final QueryExecutor queryExecutor = new QueryExecutor(eventDBFactory,
				eventQuery);
		final QueryResult result = queryExecutor.execute();

		if (result.isEmpty()) {
			final IRow row = result.firstRow();
			return row.field("date", Date.class);

		}
		return null;
	}
	
	private String format(final Date date) {
		
		final DateTime dtime = new DateTime(date);
		
		return dtime.toString("yyyy-MM-dd");
	}
}
