
package org.komea.product.backend.service.kpi;


import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.MeasureEvolutionResult;
import org.komea.product.service.dto.MeasureResult;
import org.komea.product.service.dto.PeriodCriteria;

public interface IMeasureService {
    
    /**
     * This method return the current value list of kpi apply on a list of
     * entities
     *
     * @param _kpiKeys
     *            contain the kpi name list, the entity keys list and the
     *            entity type
     * @return the current value list for each couple (entity, kpi)
     */
    List<MeasureResult> currentMeasures(KpiStringKeyList _kpiKeys);
    
    /**
     * This method return the current value of the kpi on an entity If the kpi
     * does not exist a KPINotFoundRuntimeException is launched If the entity
     * does not exist a EntityNotFoundException is launched
     *
     * @param _kpiKey
     *            the kpiString keu (kpi key, entity key and entity type)
     * @return the current value
     */
    Double currentMeasure(KpiStringKey _kpiKey);
    
    /**
     * This method return the current value of the kpi on an entity
     *
     * @param kpi
     *            kpi
     * @param entity
     *            entity
     * @return the current value
     */
    Double currentMeasure(Kpi kpi, IEntity entity);
    
    /**
     * this method find historical measure for a list of kpi apply on a list of
     * entities during a period
     *
     * @param _kpiKeyList
     *            contain the kpi name list, the entity keys list and
     *            the entity type
     * @param _period
     *            the period to fond measure (start --> end)
     * @return a list of series of measure with these date for each couple
     *         (entity, kpi)
     * @deprecated Use {@link #findMultipleHistoricalMeasure(KpiStringKeyList,PeriodCriteria)} instead
     */
    List<TimeSerieDTO> findMupltipleHistoricalMeasure(KpiStringKeyList _kpiKeyList, PeriodCriteria _period);

    /**
     * this method find historical measure for a list of kpi apply on a list of
     * entities during a period
     *
     * @param _kpiKeyList contain the kpi name list, the entity keys list and
     * the entity type
     * @param _period the period to fond measure (start --> end)
     * @return a list of series of measure with these date for each couple
     * (entity, kpi)
     */
    List<TimeSerieDTO> findMultipleHistoricalMeasure(KpiStringKeyList _kpiKeyList, PeriodCriteria _period);
    
    /**
     * this method find historical measure for a kpi on an entity during a
     * period
     *
     * @param _kpiKey
     *            the kpi key, which contain the kpi name, the entity name
     *            and the entity type.
     * @param _period
     *            the period to find measure (start --> end). If the period is not valid an IllegalArgumentException is launched
     * @return a series of measure with these date
     */
    TimeSerieDTO findHistoricalMeasure(KpiStringKey _kpiKey, PeriodTimeSerieOptions _period);
    //
    
    /**
     * This method return the last values list of kpi apply on a list of
     * entities
     *
     * @param _kpiKeys
     *            contain the kpi name list, the entity keys list and the
     *            entity type
     * @return the current value list for each couple (entity, kpi)
     */
    List<MeasureResult> lastMeasures(KpiStringKeyList _kpiKeys);
    
    /**
     * This method return the last value stored in database of the kpi on an
     * entity If the kpi does not exist a KPINotFoundRuntimeException is
     * launched If the entity does not exist a EntityNotFoundException is
     * launched
     *
     * @param _kpiKey
     *            the kpiString keu (kpi key, entity key and entity type)
     * @return the current value
     */
    Double lastMeasure(KpiStringKey _kpiKey);
    
    /**
     * This method return the last value stored in database of the kpi on an
     * entity
     *
     * @param kpi
     *            kpi
     * @param entity
     *            entity
     * @return the current value
     */
    Double lastMeasure(Kpi kpi, IEntity entity);
    
    /**
     * Get current measure and last value in history for each pair of entity/kpi
     *
     * @param _kpiKeys
     *            kpiStringKeyList
     * @return measures with last values in history
     */
    List<MeasureEvolutionResult> measuresWithEvolution(KpiStringKeyList _kpiKeys);
    
    Double lastButOneMeasure(Kpi kpi, IEntity entity);
    
}
