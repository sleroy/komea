package org.komea.product.eventory.database.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AggregationFormula {

	private String groupBy = "";

	private String filter = "";

	private String aggformula = "";

	private String fromClause = "";

	private Date from = null;

	private Date to = new Date();

	public String getAggformula() {
		return this.aggformula;
	}

	public String getFilter() {
		return this.filter;
	}

	public Date getFrom() {
		return this.from;
	}

	public String getFromClause() {
		return this.fromClause;
	}

	public String getGroupBy() {
		return this.groupBy;
	}

	public Date getTo() {
		return this.to;
	}

	public void setAggformula(final String aggformula) {
		this.aggformula = aggformula;
	}

	public void setFilter(final String filter) {
		this.filter = filter;
	}

	public void setFrom(final Date from) {
		this.from = from;
	}

	public void setFromClause(final String from) {
		this.fromClause = from;
	}

	public void setGroupBy(final String groupBy) {
		this.groupBy = groupBy;
	}

	public void setTo(final Date to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "AggregationFormula ["
				+ (this.groupBy != null ? "groupBy=" + this.groupBy + ", " : "")
				+ (this.filter != null ? "filter=" + this.filter + ", " : "")
				+ (this.aggformula != null ? "aggformula=" + this.aggformula
						+ ", " : "")
						+ (this.fromClause != null ? "fromClause=" + this.fromClause
								+ ", " : "")
								+ (this.from != null ? "from=" + this.from + ", " : "")
								+ (this.to != null ? "to=" + this.to : "") + "]";
	}

	@JsonIgnore
	public void untilNow() {
		this.to = new Date();

	}

}
