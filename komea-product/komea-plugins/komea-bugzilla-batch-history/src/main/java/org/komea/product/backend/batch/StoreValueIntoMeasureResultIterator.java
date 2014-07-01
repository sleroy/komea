/**
 *
 */

package org.komea.product.backend.batch;



import org.joda.time.DateTime;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.dto.KpiResultIterator;
import org.komea.product.service.dto.EntityKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
final class StoreValueIntoMeasureResultIterator implements KpiResultIterator
{


    private static final Logger  LOGGER =
            LoggerFactory
            .getLogger(StoreValueIntoMeasureResultIterator.class);
    private final DateTime       beginDateTime;
    private final Integer        kpiID;


    private final IStatisticsAPI statisticsAPI;



    public StoreValueIntoMeasureResultIterator(
            final IStatisticsAPI _statisticsAPI,
            final Integer _kpiID,
            final DateTime _beginDateTime) {


        statisticsAPI = _statisticsAPI;
        kpiID = _kpiID;
        beginDateTime = _beginDateTime;


    }
    
    
    @Override
    public void iterate(final EntityKey _key, final Number _number) {


        final HistoryKey historyKey = HistoryKey.of(kpiID, _key);
        LOGGER.debug("HistoryKey -> {} = {}, dateTime={}", historyKey, _number, beginDateTime);
        statisticsAPI.storeValueInHistory(historyKey, _number.doubleValue(), beginDateTime);

    }
}
