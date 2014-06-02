package org.komea.product.service.dto;

import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.model.Kpi;

public class MeasureResult {

    //
    private BaseEntityDto entity;

    private Kpi kpi;

    private Double value;

    public MeasureResult() {

    }

    public MeasureResult(final BaseEntityDto _entity, final Kpi _kpi, final Double _value) {

        super();
        entity = _entity;
        kpi = _kpi;
        value = _value;
    }

    public BaseEntityDto getEntity() {

        return entity;
    }

    public Kpi getKpi() {

        return kpi;
    }

    public Double getValue() {

        return value;
    }

    public void setEntity(final BaseEntityDto _entity) {

        entity = _entity;
    }

    public void setKpi(final Kpi _kpi) {

        kpi = _kpi;
    }

    public void setValue(final Double _value) {

        value = _value;
    }

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public String toString() {
        return "MeasureResult{" + "entity=" + entity + ", kpi=" + kpi + ", value=" + value + '}';
    }

}
