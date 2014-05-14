package org.komea.product.database.dto;

import com.google.common.collect.Lists;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.model.Measure;

public class MeasureDto extends Measure {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    public static List<MeasureDto> convert(final List<Measure> measures, final String kpiKey) {

        final List<MeasureDto> measureDtos = Lists.newArrayList();
        for (final Measure measure : measures) {
            measureDtos.add(new MeasureDto(measure, kpiKey));
        }
        return measureDtos;
    }

    private String kpiKey;

    public MeasureDto() {

    }

    public MeasureDto(final Measure measure, final String kpiKey) {
        setDate(measure.getDate());
        setDay(measure.getDay());
        setEntityID(measure.getEntityID());
        setHour(measure.getHour());
        setId(measure.getId());
        setIdKpi(measure.getIdKpi());
        setMonth(measure.getMonth());
        setSprint(measure.getSprint());
        setValue(measure.getValue());
        setWeek(measure.getWeek());
        setYear(measure.getYear());
        this.kpiKey = kpiKey;
    }

    public String getKpiKey() {

        return kpiKey;
    }

    public void setKpiKey(final String kpiKey) {

        this.kpiKey = kpiKey;
    }

    @Override
    public String toString() {

        return "MeasureDto{" + super.toString() + ", kpiKey=" + kpiKey + '}';
    }

}
