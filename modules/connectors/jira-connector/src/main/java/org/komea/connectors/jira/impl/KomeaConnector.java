package org.komea.connectors.jira.impl;


import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.company.database.enums.EntityType;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IKpisAPI;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.rest.client.api.IProjectsAPI;

import java.util.ArrayList;
import java.util.List;

public class KomeaConnector {

	public static final String ANALYZES_COUNT = "analyzes_count";

	public static KomeaConnector create(final String url) throws Exception {

		final KomeaConnector komeaConnector = new KomeaConnector();
		komeaConnector.init(url);
		return komeaConnector;
	}

	private IKpisAPI     kpisService;
	private IProjectsAPI projectsService;
	private IMeasuresAPI measuresService;

	private KomeaConnector() {

	}

	private void init(final String url) throws Exception {

		kpisService = RestClientFactory.INSTANCE.createKpisAPI(url);
		projectsService = RestClientFactory.INSTANCE.createProjectsAPI(url);
		measuresService = RestClientFactory.INSTANCE.createMeasuresAPI(url);
	}

	public Kpi getOrCreateKpiName(final String kpiName, EntityType type, GroupFormula _groupFormula) {

//		Kpi kpi = KpiBuilder.create().nameAndKeyDescription(kpiName).entityType(type).build();
		Kpi kpi = new Kpi();
		kpi.setKpiKey(kpiName);
		kpi.setName(kpiName);
		kpi.setDescription(kpiName);
		kpi.setEntityType(type);
		kpi.setGroupFormula(_groupFormula);
		return getOrCreate(kpi);
	}

	public Kpi getOrCreate(final Kpi kpi) {

		try {
			return kpisService.getOrCreate(kpi);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Project getOrCreate(final Project project) {

		try {
			return projectsService.getOrCreate(project);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void pushMeasures(final List<Measure> measures) {

		try {
			measuresService.pushMeasures(measures);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void pushMeasure(final Measure measure) {

		try {
			List<Measure> measures = new ArrayList<>();
			measures.add(measure);
			measuresService.pushMeasures(measures);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void clearMeasuresOfKpi(final String kpiKey) {

		try {
			measuresService.clearMeasuresOfKpi(kpiKey);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Project getOrCreateProject(final String _projectName) {
		Project project = new Project(null, _projectName, _projectName, _projectName, null, null);
		return  getOrCreate(project);

	}
}
