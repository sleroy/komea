package org.komea.product.backend.service.measure;

import java.util.List;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;

/**
 */
public interface IMeasureService {

    /**
     * Method getMeasures.
     * @param kpis List<Kpi>
     * @param entities List<BaseEntity>
     * @param searchMeasuresDto SearchMeasuresDto
     * @return List<Measure>
     */
    List<Measure> getMeasures(List<Kpi> kpis, List<BaseEntity> entities, SearchMeasuresDto searchMeasuresDto);
}
