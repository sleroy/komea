package org.komea.product.model.timeserie.dto;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.MeasureResult;

public class TimeSerieDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public static List<MeasureResult> timeSeriesToMeasureResults(
            final List<TimeSerieDTO> timeSerieDTOs) {

        final List<MeasureResult> measureResults
                = new ArrayList<MeasureResult>(timeSerieDTOs.size());
        for (final TimeSerieDTO timeSerieDTO : timeSerieDTOs) {
            measureResults.add(timeSerieDTO.toMeasureResult());
        }
        return measureResults;
    }

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

    private double getSum() {
        double sum = 0;
        for (final TimeCoordinateDTO coordinate : coordinates) {
            sum += coordinate.getValue();
        }
        return sum;
    }

    public Double getGroupFormulaValue() {
        if (coordinates.isEmpty()) {
            return null;
        }
        final double sum = getSum();
        if (GroupFormula.AVG_VALUE.equals(kpi.getGroupFormula())) {
            return sum / coordinates.size();
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

    public MeasureResult toMeasureResult() {

        final Double average = getGroupFormulaValue();
        return new MeasureResult(entity, kpi, average);
    }

    public static boolean hasValues(final Collection<TimeSerieDTO> timeSeries) {
        for (final TimeSerieDTO timeSerie : timeSeries) {
            if (!timeSerie.getCoordinates().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "TimeSerieDTO [\\n\\tcoordinates="
                + coordinates + ", \\n\\tkpi=" + kpi.getDisplayName() + ", \\n\\tentity="
                + entity.getDisplayName() + "]";
    }

}
