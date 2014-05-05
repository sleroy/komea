package org.komea.product.backend.service.kpi;

import java.util.List;

import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;

public class InferMissingEntityValuesIntoKpiResult {

	private KpiResult result;
	private Kpi kpi;
	private IEntityService entityService;

	public InferMissingEntityValuesIntoKpiResult(KpiResult _result, Kpi _kpi,
			IEntityService _entityService) {
		super();
		this.result = _result;
		this.kpi = _kpi;
		this.entityService = _entityService;
	}

	/**
	 * Infer vacant entities into the kpi result;
	 */
	public KpiResult inferEntityKeys() {

		List<IEntity> byEntityType = entityService.getEntitiesByEntityType(kpi
				.getEntityType());
		return result.inferResults(byEntityType, kpi.getValueMin());
	}

}
