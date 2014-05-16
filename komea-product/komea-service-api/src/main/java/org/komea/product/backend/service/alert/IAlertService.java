package org.komea.product.backend.service.alert;

import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.service.dto.AlertCriteria;

public interface IAlertService {

    boolean isAlertActivated(KpiAlertType _alertType, Number _value);
    //

    boolean isAlertFiltered(SearchKpiAlertsDto _filter, KpiAlertDto _kpiAlert);

    boolean isAlertAssociatedToEntity(AlertCriteria _criteria, Integer entityId);

    boolean isAlertIdAssociatedToKpi(AlertCriteria _criteria, Integer kpiId);
}
