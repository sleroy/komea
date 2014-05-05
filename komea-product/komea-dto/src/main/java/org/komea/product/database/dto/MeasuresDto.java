package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;

public class MeasuresDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private ExtendedEntityType extendedEntityType;
    private List<BaseEntityDto> entities = new ArrayList<BaseEntityDto>();
    private List<Kpi> kpis = new ArrayList<Kpi>();
    private List<MeasureDto> measures = new ArrayList<MeasureDto>();

    public MeasuresDto() {
    }

    public MeasuresDto(ExtendedEntityType entityType, List<BaseEntityDto> entities,
            List<Kpi> kpis, List<MeasureDto> measures) {
        this.extendedEntityType = entityType;
        this.entities = entities;
        this.kpis = kpis;
        this.measures = measures;
    }

    public ExtendedEntityType getExtendedEntityType() {
        return extendedEntityType;
    }

    public void setExtendedEntityType(ExtendedEntityType entityType) {
        this.extendedEntityType = entityType;
    }

    public List<BaseEntityDto> getEntities() {
        return entities;
    }

    public void setEntities(List<BaseEntityDto> entities) {
        this.entities = entities;
    }

    public List<Kpi> getKpis() {
        return kpis;
    }

    public void setKpis(List<Kpi> kpis) {
        this.kpis = kpis;
    }

    public List<MeasureDto> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasureDto> measures) {
        this.measures = measures;
    }

    public void changeAccuracy(int nb) {
        for (final Measure measure : measures) {
            measure.setValue(Math.round(measure.getValue() * Math.pow(10, nb)) / Math.pow(10, nb));
        }
    }

    public String[] getEntityNames() {
        final String[] entityNames = new String[entities.size()];
        for (int i = 0; i < entities.size(); i++) {
            entityNames[i] = entities.get(i).getDisplayName();
        }
        return entityNames;
    }

    public String[] getKpiNames() {
        final String[] kpiNames = new String[kpis.size()];
        for (int i = 0; i < kpis.size(); i++) {
            kpiNames[i] = kpis.get(i).getName();
        }
        return kpiNames;
    }

    public List<Pair<String, List<Number>>> getEntitySeries() {
        final List<Pair<String, List<Number>>> series = new ArrayList<Pair<String, List<Number>>>(kpis.size());
        for (final Kpi kpi : kpis) {
            final List<Number> numbers = new ArrayList<Number>();
            series.add(Pair.create(kpi.getName(), numbers));
            for (final BaseEntityDto entity : entities) {
                Number number = null;
                for (final MeasureDto measure : measures) {
                    boolean measureMatches = measureMatches(kpi, entity, measure);
                    if (measureMatches) {
                        number = measure.getValue();
                        break;
                    }
                }
                if (number != null) {
                    numbers.add(number);
                }
            }
        }
        return series;
    }

    public Integer getId(final Measure measure) {
        return getId(extendedEntityType.getEntityType(), measure);
    }

    public static Integer getId(final EntityType entityType, final Measure measure) {
        switch (entityType) {
            case PERSON:
                return measure.getIdPerson();
            case TEAM:
            case DEPARTMENT:
                return measure.getIdPersonGroup();
            case PROJECT:
                return measure.getIdProject();
        }
        return null;
    }

    private boolean measureMatches(final Kpi kpi, final BaseEntityDto entity, final MeasureDto measure) {
        return kpi.getKpiKey().equals(measure.getKpiKey()) && entity.getId().equals(getId(measure));
    }

    public List<Pair<String, List<Number>>> getKpiSeries() {
        final List<Pair<String, List<Number>>> series = new ArrayList<Pair<String, List<Number>>>(entities.size());
        for (final BaseEntityDto entity : entities) {
            final List<Number> numbers = new ArrayList<Number>();
            series.add(Pair.create(entity.getDisplayName(), numbers));
            for (final Kpi kpi : kpis) {
                Number number = null;
                for (final MeasureDto measure : measures) {
                    if (measureMatches(kpi, entity, measure)) {
                        number = measure.getValue();
                        break;
                    }
                }
                numbers.add(number);
            }
        }
        return series;
    }

    @Override
    public String toString() {
        return "MeasuresDto{" + "extendedEntityType=" + extendedEntityType + ", entities="
                + entities + ", kpis=" + kpis + ", measures=" + measures + '}';
    }

}
