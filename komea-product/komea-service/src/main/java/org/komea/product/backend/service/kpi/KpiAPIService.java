/**
 *
 */

package org.komea.product.backend.service.kpi;


import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.EntityStringKey;
import org.komea.product.service.dto.HistoryStringKey;
import org.komea.product.service.dto.HistoryStringKeyList;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.service.dto.LimitCriteria;
import org.komea.product.service.dto.MeasureResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * @author sleroy
 */
@Service
@Transactional
public class KpiAPIService implements IKpiAPI {
    
    @Autowired
    private IKPIService      kpiService;
    
    @Autowired
    private IKpiValueService kpiValueService;
    
    @Autowired
    private IEntityService   entityService;
    
    /**
     * This method convert a HistoryStingKey to KpiKey
     *
     * @param _historyKey
     * @return the KpiKey
     */
    private KpiKey convertToKpiKey(final HistoryStringKey _historyKey) {
    
        EntityStringKey entityKey = EntityStringKey.of(_historyKey.getEntityType().getEntityType(), _historyKey.getEntityKey());
        IEntity entity = entityService.findEntityByEntityStringKey(entityKey);
        if (entity == null) {
            return null;
        }
        return KpiKey.ofKpiNameAndEntity(_historyKey.getKpiKey(), entity);
    }
    /*
     * (non-Javadoc)
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
     * @see
     * org.komea.product.backend.service.kpi.IKpiAPI#getBaseKpisOfGroupKpiKeys
     * (java.util.List)
     */
    @Override
    public List<Kpi> getBaseKpisOfGroupKpiKeys(final List<String> _groupKpiKeys) {
    
        Validate.notNull(_groupKpiKeys);
        return kpiService.getBaseKpisOfGroupKpiKeys(_groupKpiKeys);
    }
    
    @Override
    public MeasureResult getHistoricalMeasure(final HistoryStringKey _historyKey, final LimitCriteria _limit) {
    
        MeasureResult historicalMeasure = new MeasureResult();
        // FIXME measureHistoryService.getHistoricalMeasure(_historyKey, _limit);
        return historicalMeasure;
    }
    @Override
    public List<MeasureResult> getHistoricalMeasures(final HistoryStringKeyList _historyKeys, final LimitCriteria _limit) {
    
        Validate.notNull(_historyKeys, "history string key list can't be null");
        
        List<MeasureResult> measuresList = Lists.newArrayList();
        HistoryStringKey historyKey;
        for (String kpiKey : _historyKeys.getKpiKeys()) {
            for (String entityKey : _historyKeys.getEntityKeys()) {
                historyKey = new HistoryStringKey(kpiKey, entityKey, _historyKeys.getEntityType());
                measuresList.add(getHistoricalMeasure(historyKey, _limit));
            }
        }
        return measuresList;
    }
    /**
     * @return the kpiService
     */
    public IKPIService getKpiService() {
    
        return kpiService;
    }
    
    /*
     * (non-Javadoc)
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
