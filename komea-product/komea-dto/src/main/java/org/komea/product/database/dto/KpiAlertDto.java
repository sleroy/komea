package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.Date;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;

public class KpiAlertDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private KpiAlertType kpiAlertType;
    private Kpi kpi;
    private String entityName;
    private Boolean activated;
    private Double value;
    private Date date;

    public KpiAlertDto(KpiAlertType kpiAlertType, Kpi kpi, String entityName,
            Boolean activated, Double value, Date date) {
        this.kpiAlertType = kpiAlertType;
        this.kpi = kpi;
        this.entityName = entityName;
        this.activated = activated;
        this.value = value;
        this.date = date;
    }

    public KpiAlertDto() {
    }

    public KpiAlertType getKpiAlertType() {
        return kpiAlertType;
    }

    public void setKpiAlertType(KpiAlertType kpiAlertType) {
        this.kpiAlertType = kpiAlertType;
    }

    public Kpi getKpi() {
        return kpi;
    }

    public void setKpi(Kpi kpi) {
        this.kpi = kpi;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean enabled) {
        this.activated = enabled;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "KpiAlertDto{" + "kpiAlertType=" + kpiAlertType + ", kpi=" + kpi
                + ", entityName=" + entityName + ", activated=" + activated
                + ", value=" + value + ", date=" + date + '}';
    }

}
