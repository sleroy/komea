package org.komea.product.model.timeserie.dto;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.model.Kpi;

public class TimeSerieDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<TimeCoordinateDTO> coordinates = Lists.newArrayList();
    private Kpi kpi;
    private BaseEntityDto entity;

    public TimeSerieDTO() {
    }

    public TimeSerieDTO(List<TimeCoordinateDTO> coordinates, Kpi kpi, BaseEntityDto entity) {
        this.coordinates = coordinates;
        this.kpi = kpi;
        this.entity = entity;
    }

    public boolean addCoordinate(final TimeCoordinateDTO _e) {

        return coordinates.add(_e);
    }

    public List<TimeCoordinateDTO> getCoordinates() {

        return coordinates;
    }

    public BaseEntityDto getEntity() {

        return entity;
    }

    public Kpi getKpi() {

        return kpi;
    }

    public void setEntity(final BaseEntityDto _entity) {

        entity = _entity;
    }

    public void setKpi(final Kpi _kpi) {

        kpi = _kpi;
    }

    public void setCoordinates(List<TimeCoordinateDTO> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "TimeSerieDTO{" + "coordinates=" + coordinates + ", kpi=" + kpi + ", entity=" + entity + '}';
    }

}
