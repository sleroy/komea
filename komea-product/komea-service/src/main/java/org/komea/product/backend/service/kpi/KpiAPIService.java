/**
 *
 */

package org.komea.product.backend.service.kpi;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
@Service
@Transactional
public class KpiAPIService implements IKpiAPI {

	@Autowired
	private IKPIService	     kpiService;

	@Autowired
	private IKpiValueService	kpiValueService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getAllKpisOfEntityType(
	 * org.komea.product.database.enums.EntityType)
	 */
	@Override
	public List<Kpi> getAllKpisOfEntityType(final EntityType _entityType) {

		Validate.notNull(_entityType);

		return kpiService.getAllKpisOfEntityType(_entityType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getBaseKpisOfGroupKpiKeys
	 * (java.util.List)
	 */
	@Override
	public List<Kpi> getBaseKpisOfGroupKpiKeys(final List<String> _groupKpiKeys) {

		Validate.notNull(_groupKpiKeys);
		return kpiService.getBaseKpisOfGroupKpiKeys(_groupKpiKeys);
	}

	/**
	 * @return the kpiService
	 */
	public IKPIService getKpiService() {

		return kpiService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getKpisForGroups(java.util
	 * .List)
	 */
	@Override
	public List<Kpi> getKpisForGroups(final List<Kpi> _kpis) {

		return kpiService.getKpisForGroups(_kpis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getKpisOfGroupKpiKeys(java
	 * .util.List, java.util.List)
	 */
	@Override
	public Collection<? extends Kpi> getKpisOfGroupKpiKeys(final List<String> _groupKpiKeys, final List<Kpi> _baseKpis) {

		return kpiService.getKpisOfGroupKpiKeys(_groupKpiKeys, _baseKpis);
	}

	/**
	 * @return the kpiValueService
	 */
	public IKpiValueService getKpiValueService() {

		return kpiValueService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getSelectionOfKpis(org.
	 * komea.product.database.enums.EntityType, java.util.List)
	 */
	@Override
	public Collection<? extends Kpi> getSelectionOfKpis(

	final List<String> _kpiKeys) {

		Validate.notNull(_kpiKeys);
		return kpiService.selectByKeys(_kpiKeys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.product.backend.service.kpi.IKpiAPI#selectAll()
	 */
	@Override
	public List<Kpi> selectAll() {

		return kpiService.selectAll();
	}

	/**
	 * @param _kpiService
	 *            the kpiService to set
	 */
	public void setKpiService(final IKPIService _kpiService) {

		kpiService = _kpiService;
	}

	/**
	 * @param _kpiValueService
	 *            the kpiValueService to set
	 */
	public void setKpiValueService(final IKpiValueService _kpiValueService) {

		kpiValueService = _kpiValueService;
	}

}
