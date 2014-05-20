package org.komea.product.backend.service.alert;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.AlertCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertFinderService implements IAlertFinderService {

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

    /**
     * @param _alertCriteria
     * @param _mapKpis
     * @return
     */
    public KpiAlertDto findAlert(final AlertCriteria _alertCriteria, final IdKpiMap _mapKpis) {

        final Measure measure = findMeasure(_alertCriteria);
        if (measure == null) {
            return null;
        }
        final Kpi kpi = _mapKpis.get(_alertCriteria.getAlertType().getIdKpi());
        final KpiAlertDto kpiAlert = new KpiAlertDto();
        kpiAlert.setKpiAlertType(_alertCriteria.getAlertType());
        kpiAlert.setKpi(kpi);
        kpiAlert.setDate(new Date());
        kpiAlert.setEntityName(_alertCriteria.getEntity().getDisplayName());
        kpiAlert.setValue(measure.getValue());
        kpiAlert.setActivated(alertService.isAlertActivated(_alertCriteria.getAlertType(), measure.getValue()));

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

        final IdKpiMap idKpiMap = new IdKpiMap();
        final Set<String> kpiKeys = idKpiMap.fillIdKpi(alertTypesOfKpiAndSeverity, kpiService);
        final SearchMeasuresDto searchMeasuresDto = createMeasureFilterOnKpiKeys(_searchAlert, extendedEntityType, kpiKeys);

//        final List<Measure> measuresOfKpi = measureService.getMeasures(idKpiMap.values(), entities, searchMeasuresDto);
        final List<Measure> measuresOfKpi = Lists.newArrayList();
        final List<KpiAlertDto> filteredActivatedAlerts = Lists.newArrayList();
        for (final KpiAlertType alertType : alertTypesOfKpiAndSeverity) {
            for (final BaseEntityDto entity : entities) {
                final KpiAlertDto kpiAlert = findAlert(new AlertCriteria(alertType, entity, entityType, measuresOfKpi), idKpiMap);
                boolean alertFiltered = alertService.isAlertFiltered(_searchAlert, kpiAlert);
                if (alertFiltered) {
                    filteredActivatedAlerts.add(kpiAlert);
                }
            }
        }
        return filteredActivatedAlerts;
    }

    /**
     * @param _criteria
     * @return
     */
    public Measure findMeasure(final AlertCriteria _criteria) {

        for (final Measure measure : _criteria.getMeasures()) {
            if (alertService.isMeasureAssociatedToAlert(_criteria, measure)) {
                return measure;
            }
        }

        return null;
    }

    private SearchMeasuresDto createMeasureFilterOnKpiKeys(final SearchKpiAlertsDto _searchAlert, final ExtendedEntityType entityType,
            final Set<String> kpiKeys) {

        final SearchMeasuresDto searchMeasuresDto = new SearchMeasuresDto(entityType, new ArrayList<String>(kpiKeys),
                _searchAlert.getEntityKeys(), 1);
        return searchMeasuresDto;
    }
}
