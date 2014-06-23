/**
 *
 */

package org.komea.product.plugins.bugzilla.service.backup;



import org.joda.time.DateTime;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.dto.KpiResultIterator;
import org.komea.product.service.dto.EntityKey;



/**
 * @author sleroy
 */
final class StoreValueIntoMeasureResultIterator implements KpiResultIterator
{


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


        statisticsAPI.storeValueInHistory(HistoryKey.of(kpiID, _key), _number.doubleValue(),
                beginDateTime);

    }
}
