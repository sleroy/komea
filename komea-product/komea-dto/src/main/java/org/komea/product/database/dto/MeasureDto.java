package org.komea.product.database.dto;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.model.Measure;

public class MeasureDto extends Measure implements Serializable {

    private static final long serialVersionUID = 1L;

    private String kpiKey;

    public MeasureDto() {
    }

    public MeasureDto(String kpiKey, Integer id, Integer idKpi, Date date, Integer idPersonGroup, Integer idPerson, Integer idProject, Double value) {
        super(id, idKpi, date, idPersonGroup, idPerson, idProject, value);
        this.kpiKey = kpiKey;
    }

    public MeasureDto(final Measure measure, String kpiKey) {
        super(measure.getId(), measure.getIdKpi(), measure.getDate(),
                measure.getIdPersonGroup(), measure.getIdPerson(),
                measure.getIdProject(), measure.getValue());
        this.kpiKey = kpiKey;
    }

    public String getKpiKey() {
        return kpiKey;
    }

    public void setKpiKey(String kpiKey) {
        this.kpiKey = kpiKey;
    }

    @Override
    public String toString() {
        return "MeasureDto{" + super.toString() + ", kpiKey=" + kpiKey + '}';
    }

    @JsonIgnore
    public static List<MeasureDto> convert(final List<Measure> measures, final String kpiKey) {
        final List<MeasureDto> measureDtos = Lists.newArrayList();
        for (final Measure measure : measures) {
            measureDtos.add(new MeasureDto(measure, kpiKey));
        }
        return measureDtos;
    }

}
