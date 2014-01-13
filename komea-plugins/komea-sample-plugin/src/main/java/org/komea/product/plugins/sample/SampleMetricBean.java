package org.komea.product.plugins.sample;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tocea.scertify.ci.flow.app.api.service.model.IEPLMetricService;

@Component
public class SampleMetricBean {

//	private static final String STATEMENTS_FILE = "/config/statements/samples.xml";
    private static final String STATEMENTS_FILE = "/config/statements/eplStatements.xml";

	@Autowired
	private IEPLMetricService eplMetric;

	@PostConstruct
	public void init() {
		eplMetric.loadStatements(getClass().getResourceAsStream(
				STATEMENTS_FILE));
	}
}
