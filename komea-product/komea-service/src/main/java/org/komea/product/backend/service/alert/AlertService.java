package org.komea.product.backend.service.alert;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.KpiAlertDto;
import org.komea.product.database.dto.SearchKpiAlertsDto;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public final class AlertService implements IAlertService {

    private static final Logger LOGGER = Logger.getLogger(AlertService.class.getName());
    @Autowired
    private IAlertTypeService alertTypeService;

    @Autowired
    private IKPIService kpiService;

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IHistoryService measureService;

    @Override
    public List<KpiAlertDto> findAlerts(final SearchKpiAlertsDto _searchAlert) {
        final EntityType entityType = _searchAlert.getEntityType();
        final List<KpiAlertDto> alerts = Lists.newArrayList();
        final List<KpiAlertType> alertTypes = alertTypeService.selectByKeys(_searchAlert.getKpiAlertTypeKeys());
        final List<BaseEntityDto> entities = entityService.getEntities(entityType, _searchAlert.getEntityKeys());
        final Map<Integer, Kpi> mapKpis = new HashMap<Integer, Kpi>();
        final Set<String> kpiKeys = Sets.newHashSet();
        for (final KpiAlertType alertType : alertTypes) {
            final Kpi kpi = kpiService.selectByPrimaryKey(alertType.getIdKpi());
            mapKpis.put(kpi.getId(), kpi);
            kpiKeys.add(kpi.getKpiKey());
        }
        final SearchMeasuresDto searchMeasuresDto = new SearchMeasuresDto(
                entityType, new ArrayList<String>(kpiKeys), _searchAlert.getEntityKeys());
        final List<Measure> measures = measureService.getMeasures(mapKpis.values(), entities, searchMeasuresDto);
        for (final KpiAlertType alertType : alertTypes) {
            for (final BaseEntityDto entity : entities) {
                final KpiAlertDto kpiAlert = findAlert(entityType, entity, alertType, measures, mapKpis);
                if (kpiAlert != null) {
                    alerts.add(kpiAlert);
                }
            }
        }
        return alerts;
    }

    private KpiAlertDto findAlert(final EntityType entityType, final BaseEntityDto entity,
            final KpiAlertType alertType, final List<Measure> measures, final Map<Integer, Kpi> mapKpis) {
        final Measure measure = findMeasure(entityType, entity, alertType, measures);
        if (measure == null) {
            return null;
        }

        final KpiAlertDto kpiAlert = new KpiAlertDto();
        kpiAlert.setKpiAlertType(alertType);
        final Kpi kpi = mapKpis.get(alertType.getIdKpi());
        kpiAlert.setKpi(kpi);
        kpiAlert.setDate(new Date());
        kpiAlert.setEntityName(entity.getDisplayName());

        kpiAlert.setValue(measure.getValue());
        kpiAlert.setActivated(alertActivated(alertType, measure.getValue()));
        return kpiAlert;
    }

    private Measure findMeasure(final EntityType entityType, final BaseEntityDto entity,
            final KpiAlertType alertType, final List<Measure> measures) {
        for (final Measure measure : measures) {
            if (measure.getIdKpi().equals(alertType.getIdKpi())) {
                final boolean matches;
                switch (entityType) {
                    case PROJECT:
                        matches = measure.getIdProject().equals(entity.getId());
                        break;
                    case PERSON:
                        matches = measure.getIdPerson().equals(entity.getId());
                        break;
                    default:
                        matches = measure.getIdPersonGroup().equals(entity.getId());
                }
                if (matches) {
                    return measure;
                }
            }
        }
        return null;
    }

    private boolean alertActivated(final KpiAlertType alertType, final Number value) {
        if (value == null) {
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
}
