/**
 *
 */
package org.komea.connectors.sdk.rest.impl;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * @author sleroy
 *
 */
public interface IQueryClientAPI {
	@GET
	@Path("query/execute")
	@Produces("application/json")
	public Map<String, Number> executeQuery(
	                                        @QueryParam("eventFilter") final String eventFilter,
	                                        @QueryParam("periodBegin") final Date periodBegin,
	                                        @QueryParam("periodEnd") final Date periodEnd,
	                                        @QueryParam("periodField") final String periodField,
	                                        @QueryParam("groupBy") final String groupBy,
	                                        @QueryParam("aggregateField") final String aggregateField,
	                                        @QueryParam("formula") final String formula);
}
