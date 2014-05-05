/**
 * 
 */

package org.komea.product.backend.service.kpi;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IKpiMathService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiMeasureFilter;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.KpiKey;
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
	private IKpiMathService kpiMathService;

	@Autowired
	private IKPIService kpiService;

	@Autowired
	private IKpiValueService kpiValueService;

	@Autowired
	private IMeasureHistoryService measureHistoryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#computeAverageFromMeasures
	 * (java.util.List)
	 */
	@Override
	public double computeAverageFromMeasures(final List<Measure> _kpiMeasures) {

		return kpiMathService.computeAverageFromMeasures(_kpiMeasures);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#computeSumFromMeasures(
	 * java.util.List)
	 */
	@Override
	public double computeSumFromMeasures(final List<Measure> _kpiMeasures) {

		return kpiMathService.computeSumFromMeasures(_kpiMeasures);
	}

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
	 * @return the kpiMathService
	 */
	public IKpiMathService getKpiMathService() {

		return kpiMathService;
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
	public Collection<? extends Kpi> getKpisOfGroupKpiKeys(
			final List<String> _groupKpiKeys, final List<Kpi> _baseKpis) {

		return kpiService.getKpisOfGroupKpiKeys(_groupKpiKeys, _baseKpis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getKpiValues(java.lang.
	 * String)
	 */
	@Override
	public KpiResult getKpiValues(final String _kpiName) {

		return kpiValueService.getRealTimeValue(_kpiName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getKpiValuesAverageOnPeriod
	 * (java.lang.String, org.joda.time.DateTime)
	 */
	@Override
	public KpiResult getKpiValuesAverageOnPeriod(final String _kpiName,
			final DateTime _previousTime) {

		throw new UnsupportedOperationException("Not yet implemented");
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
	 * org.komea.product.backend.service.kpi.IKpiAPI#getLastStoredValueInHistory
	 * (org.komea.product.database.model.Kpi,
	 * org.komea.product.database.api.IEntity)
	 */
	@Override
	public Double getLastStoredValueInHistory(final HistoryKey _key) {

		final Measure lastMeasureInHistoryOfAKpi = kpiValueService
				.getLastMeasureInHistoryOfAKpi(_key);
		if (lastMeasureInHistoryOfAKpi != null) {
			return lastMeasureInHistoryOfAKpi.getValue();
		} else {
			return kpiValueService.getMinimalValueForAKpi(_key.getKpiID());
		}
	}

	/**
	 * @return the measureHistoryService
	 */
	public IMeasureHistoryService getMeasureHistoryService() {

		return measureHistoryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getMeasures(java.util.List,
	 * java.util.List, org.komea.product.database.dto.SearchMeasuresDto)
	 */
	@Override
	public List<MeasureDto> getMeasures(final List<Kpi> _baseKpis,
			final List<? extends IEntity> _allSubEntitiesDto,
			final SearchMeasuresDto _searchMeasuresDto) {

		return measureHistoryService.getMeasures(_baseKpis, _allSubEntitiesDto,
				_searchMeasuresDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#getRealTimeMeasuresFromEntities
	 * (java.util.List, java.util.List)
	 */
	@Override
	public List<MeasureDto> getRealTimeMeasuresFromEntities(
			final List<Kpi> _baseKpis, final List<? extends IEntity> _subEntitiesDto) {

		return kpiValueService.getAllRealTimeMeasuresPerEntityAndPerKpi(_baseKpis,
				_subEntitiesDto);
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
	 * @param _kpiMathService
	 *            the kpiMathService to set
	 */
	public void setKpiMathService(final IKpiMathService _kpiMathService) {

		kpiMathService = _kpiMathService;
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

	/**
	 * @param _measureHistoryService
	 *            the measureHistoryService to set
	 */
	public void setMeasureHistoryService(
			final IMeasureHistoryService _measureHistoryService) {

		measureHistoryService = _measureHistoryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiAPI#storeValueInHistory(org
	 * .komea.product.service.dto.KpiKey, java.lang.Double)
	 */
	@Override
	public void storeValueInHistory(final KpiKey _kpiKey, final Double _value) {

		Validate.notNull(_kpiKey);
		Validate.notNull(_value);
		kpiValueService.storeValueInKpiHistory(_kpiKey, _value);
	}

}
