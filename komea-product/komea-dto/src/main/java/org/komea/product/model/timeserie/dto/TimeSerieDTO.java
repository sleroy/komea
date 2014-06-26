package org.komea.product.model.timeserie.dto;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.MeasureResult;

public class TimeSerieDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<TimeCoordinateDTO> coordinates = Lists.newArrayList();
    private BaseEntityDto entity;
    private Kpi kpi;

    public TimeSerieDTO() {

    }

    public TimeSerieDTO(
            final List<TimeCoordinateDTO> coordinates,
            final Kpi kpi,
            final BaseEntityDto entity) {

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

    public Double getGroupFormulaValue() {

        double sum = 0;
        int count = 0;
        for (final TimeCoordinateDTO coordinate : coordinates) {
            if (coordinate.hasValue()) {
                sum += coordinate.getValue();
                count++;
            }
        }
        if (count < 1) {
            return null;
        }
        if (GroupFormula.AVG_VALUE.equals(kpi.getGroupFormula())) {
            return sum / count;
        }
        return sum;
    }

    public Kpi getKpi() {

        return kpi;
    }

    public void setCoordinates(final List<TimeCoordinateDTO> coordinates) {

        this.coordinates = coordinates;
    }

    public void setEntity(final BaseEntityDto _entity) {

        entity = _entity;
    }

    public void setKpi(final Kpi _kpi) {

        kpi = _kpi;
    }

    public static List<MeasureResult> timeSeriesToMeasureResults(final List<TimeSerieDTO> timeSerieDTOs) {
        final List<MeasureResult> measureResults = new ArrayList<MeasureResult>(timeSerieDTOs.size());
        for (final TimeSerieDTO timeSerieDTO : timeSerieDTOs) {
            measureResults.add(timeSerieDTO.toMeasureResult());
        }
        return measureResults;
    }

    public MeasureResult toMeasureResult() {

        final Double average = getGroupFormulaValue();
        return new MeasureResult(entity, kpi, average);
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "TimeSerieDTO [\\n\\tcoordinates="
                + coordinates + ", \\n\\tkpi=" + kpi.getKey() + ", \\n\\tentity=" + entity + "]";
    }

}
