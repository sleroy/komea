package org.komea.product.backend.service.alert;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiAlertTypeCriteria;

public interface IAlertTypeService extends IGenericService<KpiAlertType, Integer, KpiAlertTypeCriteria> {

    List<KpiAlertType> getAlertTypes(EntityType entityType);
}
