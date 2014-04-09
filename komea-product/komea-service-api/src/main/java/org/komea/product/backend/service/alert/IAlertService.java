package org.komea.product.backend.service.alert;

import java.util.List;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;

public interface IAlertService {

    List<KpiAlertDto> findAlerts(SearchKpiAlertsDto _searchAlert);
}
