package org.komea.product.model.timeserie;

import java.util.List;

import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.dto.TimeCoordinateDTO;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;

public abstract class TimeSerieConvertor {

    public static TimeSerieDTO build(final List<TimeCoordinate> _serie, final Kpi _kpi, final BaseEntityDto _entity) {

        TimeSerieDTO dto = new TimeSerieDTO();
        dto.setKpi(_kpi);
        dto.setEntity(_entity);
        for (TimeCoordinate coordinate : _serie) {
            dto.addCoordinate(new TimeCoordinateDTO(coordinate.getDate().toDate(), coordinate.getValue()));
        }
        return dto;
    }

    private TimeSerieConvertor() {
    }

}
