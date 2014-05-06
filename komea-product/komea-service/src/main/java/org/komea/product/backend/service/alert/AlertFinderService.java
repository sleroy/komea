
package org.komea.product.backend.service.alert;


import org.springframework.stereotype.Service;

@Service
public class AlertFinderService {/*
                                  * implements IAlertFinderService {
                                  * @Autowired
                                  * private IAlertService alertService;
                                  * @Autowired
                                  * private IAlertTypeService alertTypeService;
                                  * @Autowired
                                  * private IEntityService entityService;
                                  * @Autowired
                                  * private IKPIService kpiService;
                                  * @Autowired
                                  * private IHistoryService measureService;
                                  * @Override
                                  * public List<KpiAlertDto> findAlerts(final SearchKpiAlertsDto _searchAlert) {
                                  * final ExtendedEntityType extendedEntityType = _searchAlert.getExtendedEntityType();
                                  * final EntityType entityType = extendedEntityType.getEntityType();
                                  * final List<KpiAlertType> alertTypesOfKpiAndSeverity = alertTypeService.getAlertTypes(extendedEntityType,
                                  * _searchAlert.getKpiAlertTypeKeys(), _searchAlert.getSeverityMin());
                                  * final List<BaseEntityDto> parentEntities = entityService.getBaseEntityDTOS(entityType,
                                  * _searchAlert.getEntityKeys());
                                  * final List<BaseEntityDto> entities = entityService.getSubEntities(extendedEntityType, parentEntities);
                                  * final IdKpiMap idKpiMap = new IdKpiMap();
                                  * final Set<String> kpiKeys = idKpiMap.fillIdKpi(alertTypesOfKpiAndSeverity, kpiService);
                                  * final SearchMeasuresDto searchMeasuresDto = createMeasureFilterOnKpiKeys(_searchAlert,
                                  * extendedEntityType, kpiKeys);
                                  * final List<MeasureDto> measuresOfKpi = measureService.getMeasures(idKpiMap.values(), entities,
                                  * searchMeasuresDto);
                                  * final List<KpiAlertDto> filteredActivatedAlerts = Lists.newArrayList();
                                  * for (final KpiAlertType alertType : alertTypesOfKpiAndSeverity) {
                                  * for (final BaseEntityDto entity : entities) {
                                  * final KpiAlertDto kpiAlert = findAlert(new AlertCriteria(alertType, entity, entityType, measuresOfKpi),
                                  * idKpiMap);
                                  * boolean alertFiltered = isAlertFiltered(_searchAlert, kpiAlert);
                                  * if (alertFiltered) {
                                  * filteredActivatedAlerts.add(kpiAlert);
                                  * }
                                  * }
                                  * }
                                  * return filteredActivatedAlerts;
                                  * }
                                  * /**
                                  * @param _filter
                                  * @param kpiAlert
                                  * @return
                                  */
    /*
     * boolean isAlertFiltered(final SearchKpiAlertsDto _filter, final KpiAlertDto kpiAlert) {
     * return kpiAlert != null && (!_filter.isActivatedOnly() || kpiAlert.isActivated());
     * }
     * public KpiAlertDto findAlert(final AlertCriteria _alertCriteria, final IdKpiMap _mapKpis) {
     * final Measure measure = findMeasure(_alertCriteria);
     * if (measure == null) {
     * return null;
     * }
     * final KpiAlertDto kpiAlert = new KpiAlertDto();
     * kpiAlert.setKpiAlertType(_alertCriteria.getAlertType());
     * final Kpi kpi = _mapKpis.get(_alertCriteria.getAlertType().getIdKpi());
     * kpiAlert.setKpi(kpi);
     * kpiAlert.setDate(new Date());
     * kpiAlert.setEntityName(_alertCriteria.getEntity().getDisplayName());
     * kpiAlert.setValue(measure.getValue());
     * kpiAlert.setActivated(alertService.isAlertActivated(_alertCriteria.getAlertType(), measure.getValue()));
     * return kpiAlert;
     * }
     * SearchMeasuresDto createMeasureFilterOnKpiKeys(final SearchKpiAlertsDto _searchAlert, final ExtendedEntityType entityType,
     * final Set<String> kpiKeys) {
     * final SearchMeasuresDto searchMeasuresDto = new SearchMeasuresDto(entityType, new ArrayList<String>(kpiKeys),
     * _searchAlert.getEntityKeys(), 1);
     * return searchMeasuresDto;
     * }
     * Measure findMeasure(final AlertCriteria _criteria) {
     * for (final Measure measure : _criteria.getMeasures()) {
     * if (isAlertIdAssociatedToKpi(_criteria, measure) && isAlertAssociatedToMeasureEntity(_criteria, measure)) {
     * return measure;
     * }
     * }
     * return null;
     * }
     * boolean isAlertAssociatedToMeasureEntity(final AlertCriteria _criteria, final Measure measure) {
     * Integer entityId = measure.getEntityID();
     * return entityId != null && _criteria.getEntity().getId().equals(entityId);
     * }
     * boolean isAlertIdAssociatedToKpi(final AlertCriteria _criteria, final Measure measure) {
     * return measure.getIdKpi().equals(_criteria.getAlertType().getIdKpi());
     * }
     */
}
