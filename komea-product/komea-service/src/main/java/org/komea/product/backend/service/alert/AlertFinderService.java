package org.komea.product.backend.service.alert;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertFinderService implements IAlertFinderService {

    @Autowired
    private IMeasureService measureService;

    @Autowired
    private IAlertService alertService;

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IAlertTypeService alertTypeService;

    @Autowired
    private IKPIService kpiService;

    public IAlertService getAlertService() {

        return alertService;
    }

    public KpiAlertDto findAlert(final KpiAlertType alertType,
            final BaseEntityDto entity, final Kpi kpi) {
        final Double value = measureService.currentMeasure(kpi, entity);
        final KpiAlertDto kpiAlert = new KpiAlertDto();
        kpiAlert.setKpiAlertType(alertType);
        kpiAlert.setKpi(kpi);
        kpiAlert.setDate(new Date());
        kpiAlert.setEntityName(entity.getDisplayName());
        kpiAlert.setValue(value);
        kpiAlert.setActivated(alertService.isAlertActivated(alertType, value));

        return kpiAlert;
    }

    @Override
    public List<KpiAlertDto> findAlerts(final SearchKpiAlertsDto _searchAlert) {

        final ExtendedEntityType extendedEntityType = _searchAlert.getExtendedEntityType();
        final EntityType entityType = extendedEntityType.getEntityType();
        final List<KpiAlertType> alertTypesOfKpiAndSeverity = alertTypeService.getAlertTypes(extendedEntityType,
                _searchAlert.getKpiAlertTypeKeys(), _searchAlert.getSeverityMin());
        final List<BaseEntityDto> parentEntities = entityService.getBaseEntityDTOS(entityType, _searchAlert.getEntityKeys());
        final List<BaseEntityDto> entities = entityService.getSubEntities(extendedEntityType, parentEntities);

        final List<KpiAlertDto> filteredActivatedAlerts = Lists.newArrayList();
        for (final KpiAlertType alertType : alertTypesOfKpiAndSeverity) {
            final Kpi kpi = kpiService.selectByPrimaryKey(alertType.getIdKpi());
            if (kpi == null) {
                continue;
            }
            for (final BaseEntityDto entity : entities) {
                final KpiAlertDto kpiAlert = findAlert(alertType, entity, kpi);
                boolean alertFiltered = alertService.isAlertFiltered(_searchAlert, kpiAlert);
                if (alertFiltered) {
                    filteredActivatedAlerts.add(kpiAlert);
                }
            }
        }
        return filteredActivatedAlerts;
    }

}
