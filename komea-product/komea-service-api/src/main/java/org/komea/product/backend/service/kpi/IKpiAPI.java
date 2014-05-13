/**
 *
 */

package org.komea.product.backend.service.kpi;

import java.util.Collection;
import java.util.List;

import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.HistoryStringKey;
import org.komea.product.service.dto.HistoryStringKeyList;
import org.komea.product.service.dto.LimitCriteria;
import org.komea.product.service.dto.MeasureResult;

/**
 * This interface defines the KPI api provided by Komea. It is recommended to
 * perform a precise analysis of the functionalities of this interface before to
 * add other methods. Single purpose methods are FORBIDDEN!
 *
 * @author sleroy
 */
public interface IKpiAPI {

	/**
	 * Returns the list of kpis associated to an entity type.
	 * 
	 * @param _entityType
	 *            the type of entity requested
	 * @return the list of kpi.
	 */
	List<Kpi> getAllKpisOfEntityType(EntityType _entityType);

	/**
	 * @param _groupKpiKeys
	 * @return
	 * @deprecated TO BE REFACTORED
	 */
	@Deprecated
	List<Kpi> getBaseKpisOfGroupKpiKeys(List<String> _groupKpiKeys);

    /**
     * This method find the historical measures of a a KPI on an entity
     *
     * @param _historyKey
     *            contain the KPI key, the entity key and the entity type
     * @param _limit
     *            the limit of the research by entity. As the number of result of the date range
     * @return the measures
     */
    MeasureResult getHistoricalMeasure(HistoryStringKey _historyKey, LimitCriteria _limit);
    
    /**
     * This method find the historical measures of a a list of KPIs on a list of entities
     *
     * @param _historyKey
     *            contain the KPI list keys, the entity list keys and the entity type
     * @param _limit
     *            the limit of the research by entity. As the number of result of the date range
     * @return a list of measures
     */
    List<MeasureResult> getHistoricalMeasures(HistoryStringKeyList _historyKeys, LimitCriteria _limit);
	/**
	 * Returns the list of kpis for groups.
	 * 
	 * @param _kpis
	 *            the list of kpis
	 * @deprecated TO BE REFACTORED
	 */
	@Deprecated
	List<Kpi> getKpisForGroups(List<Kpi> _kpis);

	/**
	 * @param _groupKpiKeys
	 * @param _baseKpis
	 * @return
	 * @deprecated TO BE REFACTORED
	 */
	@Deprecated
	Collection<? extends Kpi> getKpisOfGroupKpiKeys(List<String> _groupKpiKeys, List<Kpi> _baseKpis);

	/**
	 * Returns a selection of kpis filtered by their entity type and a list of
	 * kpi keys.
	 * 
	 * @param _kpiType
	 *            the kpi type
	 * @param _kpiKeys
	 *            the kpi keys
	 * @return the list of kpi
	 */
	Collection<? extends Kpi> getSelectionOfKpis(List<String> _kpiKeys);

	/**
	 * Returns the list of KPI
	 * 
	 * @return the list of KPI.
	 */
	List<Kpi> selectAll();

}
