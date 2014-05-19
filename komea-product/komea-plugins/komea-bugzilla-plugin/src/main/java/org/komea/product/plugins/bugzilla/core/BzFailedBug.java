package org.komea.product.plugins.bugzilla.core;

import java.util.Collections;

import org.komea.product.backend.kpi.search.Filter;
import org.komea.product.backend.kpi.search.Search;

public class BzFailedBug extends BZBugCountKPI {

	public BzFailedBug() {
		super(Collections.singletonList(Search.create(Filter.create("status", true, "new", "unconfirmed", "onhold",
		        "accepted", "assigned", "opened", "reopened"))));

	}

}
