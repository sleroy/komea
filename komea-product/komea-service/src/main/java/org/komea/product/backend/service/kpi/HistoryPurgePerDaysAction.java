
package org.komea.product.backend.service.kpi;



import org.joda.time.DateTime;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;



/**
 */
public class HistoryPurgePerDaysAction extends AbstractHistoryPurgePerTimeAction
{
    
    
    /**
     * Constructor for HistoryPurgePerDaysAction.
     * 
     * @param _measureDAO
     *            MeasureDao
     * @param _kpi
     *            Kpi
     */
    public HistoryPurgePerDaysAction(final MeasureDao _measureDAO, final Kpi _kpi) {
    
    
        super(_measureDAO, _kpi);
    }
    
    
    @Override
    protected DateTime getEvictionTime() {
    
    
        final DateTime dateTime = new DateTime();
        dateTime.minusDays(kpi.getEvictionRate());
        return dateTime;
    }
}
