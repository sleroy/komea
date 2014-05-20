package org.komea.product.backend.service.alert;

import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.AlertCriteria;

public interface IAlertService {

    boolean isAlertActivated(KpiAlertType _alertType, Number _value);
    //

    boolean isAlertFiltered(SearchKpiAlertsDto _filter, KpiAlertDto _kpiAlert);

    boolean isMeasureAssociatedToAlert(AlertCriteria _criteria, Measure _measure);
}
