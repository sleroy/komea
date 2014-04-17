
package org.komea.product.backend.service.alert;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dao.KpiAlertTypeDao;
import org.komea.product.database.dto.AlertTypeDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiAlertTypeCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public final class AlertTypeService extends
        AbstractService<KpiAlertType, Integer, KpiAlertTypeCriteria> implements IAlertTypeService
{
    
    
    @Autowired
    private IKPIService     kpiService;
    
    
    @Autowired
    private KpiAlertTypeDao requiredDAO;
    
    
    
    @Override
    public List<AlertTypeDto> getAlertTypes(final EntityType entityType) {
    
    
        final List<Kpi> kpis = kpiService.getKpis(entityType, null);
        final Map<Integer, Kpi> kpisById = new HashMap<Integer, Kpi>(kpis.size());
        for (final Kpi kpi : kpis) {
            kpisById.put(kpi.getId(), kpi);
        }
        if (kpis.isEmpty()) { return Collections.emptyList(); }
        final KpiAlertTypeCriteria alertTypeCriteria = new KpiAlertTypeCriteria();
        for (final Kpi kpi : kpis) {
            alertTypeCriteria.or().andIdKpiEqualTo(kpi.getId()).andEnabledEqualTo(Boolean.TRUE);
        }
        final List<KpiAlertType> kpiAlertTypes = requiredDAO.selectByCriteria(alertTypeCriteria);
        final List<AlertTypeDto> alertTypeDtos = new ArrayList<AlertTypeDto>(kpiAlertTypes.size());
        for (final KpiAlertType kpiAlertType : kpiAlertTypes) {
            final ProviderType providerType =
                    kpisById.get(kpiAlertType.getIdKpi()).getProviderType();
            alertTypeDtos.add(new AlertTypeDto(kpiAlertType, providerType));
        }
        return alertTypeDtos;
    }
    
    
    @Override
    public List<KpiAlertType> getAlertTypes(
            final EntityType entityType,
            final List<String> alertTypeKeys,
            final Severity severityMin) {
    
    
        if (alertTypeKeys == null || alertTypeKeys.isEmpty()) {
            final List<Kpi> kpis = kpiService.getKpis(entityType, null);
            if (kpis.isEmpty()) { return Collections.emptyList(); }
            final KpiAlertTypeCriteria alertTypeCriteria = new KpiAlertTypeCriteria();
            for (final Kpi kpi : kpis) {
                final KpiAlertTypeCriteria.Criteria criteria =
                        alertTypeCriteria.or().andIdKpiEqualTo(kpi.getId())
                                .andEnabledEqualTo(Boolean.TRUE);
                andSeverityGreaterThanOrEqualTo(severityMin, criteria);
            }
            return requiredDAO.selectByCriteria(alertTypeCriteria);
        }
        return selectByKeys(alertTypeKeys);
    }
    
    
    /**
     * @return the kpiService
     */
    public IKPIService getKpiService() {
    
    
        return kpiService;
    }
    
    
    @Override
    public KpiAlertTypeDao getRequiredDAO() {
    
    
        return requiredDAO;
    }
    
    
    /**
     * @param _kpiService
     *            the kpiService to set
     */
    public void setKpiService(final IKPIService _kpiService) {
    
    
        kpiService = _kpiService;
    }
    
    
    /**
     * @param _requiredDAO
     *            the requiredDAO to set
     */
    public void setRequiredDAO(final KpiAlertTypeDao _requiredDAO) {
    
    
        requiredDAO = _requiredDAO;
    }
    
    
    private void andSeverityGreaterThanOrEqualTo(
            final Severity severity,
            final KpiAlertTypeCriteria.Criteria criteria) {
    
    
        criteria.andSeverityIsNotNull();
        for (int i = 0; i < severity.ordinal(); i++) {
            criteria.andSeverityNotEqualTo(Severity.values()[i]);
        }
    }
    
    
    @Override
    protected KpiAlertTypeCriteria createKeyCriteria(final String key) {
    
    
        final KpiAlertTypeCriteria criteria = new KpiAlertTypeCriteria();
        criteria.createCriteria().andKpiAlertKeyEqualTo(key);
        return criteria;
    }
    
}
