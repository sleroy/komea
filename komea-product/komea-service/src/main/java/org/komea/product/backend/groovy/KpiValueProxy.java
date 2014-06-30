/**
 *
 */

package org.komea.product.backend.groovy;



import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.service.dto.EntityKey;
import org.thymeleaf.util.Validate;



/**
 * @author sleroy
 */
public class KpiValueProxy
{


    private final Kpi            kpi;
    private final IStatisticsAPI statisticsAPI;



    public KpiValueProxy(final Kpi _kpi, final IStatisticsAPI _statisticsAPI) {


        kpi = _kpi;
        statisticsAPI = _statisticsAPI;
        Validate.notNull(_kpi, "A valid kpi name is expected");
        Validate.notNull(statisticsAPI, "Statistics API cannot be reached");
    }


    public Double dailyValue() {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_DAY).computeUniqueValue(
                kpi.getGroupFormula());
    }


    public Double dailyValue(final EntityKey _entityKey) {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_DAY).getDoubleValueOrNull(
                _entityKey);
    }


    /**
     * Returns the value of a kpi result.
     * 
     * @param _delay
     *            the delay.
     * @return
     */
    public KpiResult getValue(final TimeScale _delay) {


        return statisticsAPI.evaluateKpiLastValue(kpi, _delay);
    }


    public Double hourValue() {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_HOUR).computeUniqueValue(
                kpi.getGroupFormula());
    }


    public Double hourValue(final EntityKey _entityKey) {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_HOUR).getDoubleValueOrNull(
                _entityKey);
    }


    public Double monthValue() {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_MONTH).computeUniqueValue(
                kpi.getGroupFormula());
    }


    public Double monthValue(final EntityKey _entityKey) {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_MONTH).getDoubleValueOrNull(
                _entityKey);
    }


    public Double weekValue() {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_WEEK).computeUniqueValue(
                kpi.getGroupFormula());
    }


    public Double weekValue(final EntityKey _entityKey) {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_WEEK).getDoubleValueOrNull(
                _entityKey);
    }


    public Double yearValue() {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_YEAR).computeUniqueValue(
                kpi.getGroupFormula());
    }
    
    
    public Double yearValue(final EntityKey _entityKey) {


        return statisticsAPI.evaluateKpiLastValue(kpi, TimeScale.PER_YEAR).getDoubleValueOrNull(
                _entityKey);
    }


}
