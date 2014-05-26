package org.komea.product.backend.service.alert;

import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.model.KpiAlertType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
@Service
@Transactional
public class AlertService implements IAlertService {

    /**
     * Cette méthode : est ce que le seuil est franchi ?
     *
     * @param alertType
     * @param value
     * @return
     */
    @Override
    public Boolean isAlertActivated(final KpiAlertType alertType, final Number value) {

        if (value == null) {
            return null;
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
}
