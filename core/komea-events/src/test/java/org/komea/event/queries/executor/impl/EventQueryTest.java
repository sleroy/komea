package org.komea.event.queries.executor.impl;

import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.column.impl.ColumnMapper;
import org.komea.event.queries.column.impl.SumColumn;
import org.komea.event.queries.executor.impl.EventQuery;

import com.google.common.base.Predicate;

public class EventQueryTest {

	final String	release	= "EAX";
	final String	branch	= "master";
	final String	project	= "PRJ";
	
	@Test
	public void testQuery1() throws Exception {
		// .query("SELECT * FROM tag WHERE " + clause(this.application) +
		// "AND name='" + release + "'");
		final EventQuery eventQuery = new EventQuery();
		eventQuery.eventTypes("tag");
		eventQuery.returnsEvents();

		final Predicate<FlatEvent> closure = new Predicate<FlatEvent>() {

			@Override
			public boolean apply(final FlatEvent _event) {

				return _event.fieldEquals("name", release)
						&& _event.fieldEquals("project", project)
						&& _event.fieldEquals("branch", branch);
			}

		};
		eventQuery.where(closure);
	}

	@Test
	public void testQuery2() throws Exception {
		// String query = "SELECT * FROM tag WHERE " + clause(this.application)
		// + "AND date < '" + format(date)
		// + "' ORDER BY date DESC LIMIT 1";
		final EventQuery eventQuery = new EventQuery();
		eventQuery.eventTypes("tag");
		eventQuery.returnsEvents();
		eventQuery.limit(1);

		final DateTime dateTime = new DateTime();

		final Predicate<FlatEvent> closure = new Predicate<FlatEvent>() {

			@Override
			public boolean apply(final FlatEvent _event) {

				return _event.fieldEquals("name", release)
						&& _event.fieldEquals("project", project)
						&& _event.fieldEquals("branch", branch)
						&& _event.isBefore(dateTime);
			}

		};
		eventQuery.where(closure);
	}
	
	@Test
	public void testQuery3() throws Exception {
		// String query =
		// "SELECT SUM(total_updated_lines) FROM file_update WHERE " +
		// clause(this.application) + " AND date < '"
		// + format(releaseDate) + "' AND  date > '" + format(previous) + "'" +
		// pathClause(this.application);

		final EventQuery eventQuery = new EventQuery();

		final ColumnMapper columnMapper = new ColumnMapper();
		columnMapper.newColumn("sum", new SumColumn("total_updated_lines"));

		eventQuery.returnsResultMapper(columnMapper);

		final Predicate<FlatEvent> closure = new Predicate<FlatEvent>() {

			private DateTime	releaseDate;
			private DateTime	previousDate;
			private String		path;

			@Override
			public boolean apply(final FlatEvent _event) {

				return _event.fieldEquals("name", release)
						&& _event.fieldEquals("project", project)
						&& _event.fieldEquals("branch", branch)
						&& _event.isBefore(releaseDate)
						&& _event.isAfter(previousDate)
						&& ((String) _event.field("path")).startsWith(path);
			}

		};
		eventQuery.where(closure);
	}
	
}
