package org.komea.microservices.events.database.model;

import java.util.Date;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AggregationFormula {

	private String	groupBy	   = "";

	private String	filter	   = "";

	private String	aggformula	= "";

	private String	fromClause	= "";

	private Date	fromDateTime	   = null;

	private Date	toDateTime	       = new Date();

	public String getAggformula() {
		return this.aggformula;
	}

	public String getFilter() {
		return this.filter;
	}

	public Date getFrom() {
		return this.fromDateTime;
	}

	public String getFromClause() {
		return this.fromClause;
	}

	public String getGroupBy() {
		return this.groupBy;
	}

	public Date getTo() {
		return this.toDateTime;
	}

	public void setAggformula(final String aggformula) {
		this.aggformula = aggformula;
	}

	public void setFilter(final String filter) {
		this.filter = filter;
	}

	public void setFrom(final Date from) {
		this.fromDateTime = from;
	}

	public void setFromDateTime(final DateTime _fromPeriod) {
		this.fromDateTime = _fromPeriod.toDate();

	}

	public void setFromClause(final String from) {
		this.fromClause = from;
	}

	public void setGroupBy(final String groupBy) {
		this.groupBy = groupBy;
	}

	public void setTo(final Date to) {
		this.toDateTime = to;
	}

	public void setToDateTime(final DateTime _toPeriod) {
		this.toDateTime = _toPeriod.toDate();

	}

	@Override
	public String toString() {
		return "AggregationFormula [" + (this.groupBy != null ? "groupBy=" + this.groupBy + ", " : "")
		        + (this.filter != null ? "filter=" + this.filter + ", " : "")
		        + (this.aggformula != null ? "aggformula=" + this.aggformula + ", " : "")
		        + (this.fromClause != null ? "fromClause=" + this.fromClause + ", " : "")
		        + (this.fromDateTime != null ? "from=" + this.fromDateTime + ", " : "") + (this.toDateTime != null ? "to=" + this.toDateTime : "")
		        + "]";
	}

	@JsonIgnore
	public void untilNow() {
		this.toDateTime = new Date();

	}

}
