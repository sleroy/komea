
package org.komea.product.backend.service.alert;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.AlertCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
@Service
@Transactional
public class AlertService implements IAlertService
{
    
    
    @Autowired
    private IKPIService kpiService;
    
    
    
    /**
     * Cette méthode : est ce que le seuil est franchi ?
     * 
     * @param alertType
     * @param value
     * @return
     */
    @Override
    public boolean isAlertActivated(final KpiAlertType alertType, final Number value) {
    
    
        if (value == null) {
            return false;
        }
        if (!alertType.getEnabled()) {
            return false;
        }
        final int compareTo = Double.compare(value.doubleValue(), alertType.getValue());
        switch (alertType.getOperator()) {
            case DISTINCT:
                return compareTo != 0;
            case GREATER:
                return compareTo > 0;
            case GREATER_OR_EQUAL:
                return compareTo >= 0;
            case LESSER:
                return compareTo < 0;
            case LESSER_OR_EQUAL:
                return compareTo <= 0;
            default:
                return compareTo == 0;
        }
    }
    
    
    /**
     * @param _filter
     * @param kpiAlert
     * @return
     */
    @Override
    public boolean isAlertFiltered(final SearchKpiAlertsDto _filter, final KpiAlertDto kpiAlert) {
    
    
        return kpiAlert != null && (!_filter.isActivatedOnly() || kpiAlert.isActivated());
    }
    
    
    @Override
    public boolean isMeasureAssociatedToAlert(final AlertCriteria _criteria, final Measure _measure) {
    
    
        final Integer entityId = _measure.getEntityID();
        final KpiCriteria kpiCriteria = new KpiCriteria();
        // FIXME TODO get KPIs where formula corresponds to the measure
        final List<Kpi> kpis = kpiService.selectByCriteria(kpiCriteria);
        final List<Integer> kpiIds = new ArrayList<Integer>(kpis.size());
        for (final Kpi kpi : kpis) {
            kpiIds.add(kpi.getId());
        }
        return entityId != null
                && _criteria.getEntity().getId().equals(entityId)
                && kpiIds.contains(_criteria.getAlertType().getIdKpi());
    }
}
