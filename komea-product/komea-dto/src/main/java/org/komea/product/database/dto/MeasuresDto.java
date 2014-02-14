package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.komea.product.database.enums.EntityTypeExtended;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;

public class MeasuresDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityTypeExtended entityTypeExtended;
    private List<BaseEntity> entities = new ArrayList<BaseEntity>();
    private List<Kpi> kpis = new ArrayList<Kpi>();
    private List<Measure> measures = new ArrayList<Measure>();

    public MeasuresDto() {
    }

    public MeasuresDto(EntityTypeExtended entityTypeExtended, List<BaseEntity> entities,
            List<Kpi> kpis, List<Measure> measures) {
        this.entityTypeExtended = entityTypeExtended;
        this.entities = entities;
        this.kpis = kpis;
        this.measures = measures;
    }

    public EntityTypeExtended getEntityTypeExtended() {
        return entityTypeExtended;
    }

    public void setEntityTypeExtended(EntityTypeExtended entityTypeExtended) {
        this.entityTypeExtended = entityTypeExtended;
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<BaseEntity> entities) {
        this.entities = entities;
    }

    public List<Kpi> getKpis() {
        return kpis;
    }

    public void setKpis(List<Kpi> kpis) {
        this.kpis = kpis;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    @Override
    public String toString() {
        return "MeasuresDto{" + "entityTypeExtended=" + entityTypeExtended + ", entities="
                + entities + ", kpis=" + kpis + ", measures=" + measures + '}';
    }

}
