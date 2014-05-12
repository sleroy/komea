
package org.komea.product.backend.service.alert;


import java.util.ArrayList;
import java.util.Set;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.AlertCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
@Service
@Transactional
public class AlertService implements IAlertService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertService.class);
    
    @Autowired
    private IKPIService         kpiService;
    
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
    
    private SearchMeasuresDto createMeasureFilterOnKpiKeys(final SearchKpiAlertsDto _searchAlert, final ExtendedEntityType entityType,
            final Set<String> kpiKeys) {
    
        final SearchMeasuresDto searchMeasuresDto = new SearchMeasuresDto(entityType, new ArrayList<String>(kpiKeys),
                _searchAlert.getEntityKeys(), 1);
        return searchMeasuresDto;
    }
    
    @Override
    public boolean isAlertAssociatedToMeasureEntity(final AlertCriteria _criteria, final Measure measure) {
    
        Integer entityId = measure.getEntityID();
        return entityId != null && _criteria.getEntity().getId().equals(entityId);
    }
    
    @Override
    public boolean isAlertIdAssociatedToKpi(final AlertCriteria _criteria, final Measure measure) {
    
        return measure.getIdKpi().equals(_criteria.getAlertType().getIdKpi());
    }
}
