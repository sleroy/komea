package org.komea.product.model.timeserie.dto;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.MeasureResult;

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

    public Double getAverage() {
        double sum = 0;
        int count = 0;
        for (final TimeCoordinateDTO coordinate : coordinates) {
            final Double value = coordinate.getValue();
            if (value != null) {
                sum += coordinate.getValue();
                count++;
            }
        }
        if (count < 1) {
            return null;
        }
        return sum / count;
    }

    public MeasureResult toMeasureResult() {
        final Double average = getAverage();
        return new MeasureResult(entity, kpi, average);
    }

    @Override
    public String toString() {
        return "TimeSerieDTO{" + "coordinates=" + coordinates + ", kpi=" + kpi + ", entity=" + entity + '}';
    }

}
