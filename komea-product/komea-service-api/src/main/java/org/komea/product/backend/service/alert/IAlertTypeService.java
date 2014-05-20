package org.komea.product.backend.service.alert;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.AlertTypeDto;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiAlertTypeCriteria;

public interface IAlertTypeService extends IGenericService<KpiAlertType, Integer, KpiAlertTypeCriteria> {

    List<AlertTypeDto> getAlertTypes(ExtendedEntityType entityType);

    List<KpiAlertType> getAlertTypes(ExtendedEntityType entityType, List<String> alertTypeKeys,
            Severity severityMin);
}
