package org.komea.product.backend.service.standardkpi;

import org.komea.eventory.query.CEPQuery;
import org.komea.product.plugins.kpi.standard.sonar.SonarMetricKpi;

public class SonarMetricScript extends CEPQuery {

	public SonarMetricScript() {
		super(new SonarMetricKpi("##metric##"));
	}
}
