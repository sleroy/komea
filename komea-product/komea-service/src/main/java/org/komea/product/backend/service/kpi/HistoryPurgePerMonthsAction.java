
package org.komea.product.backend.service.kpi;



import org.joda.time.DateTime;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;



/**
 */
public class HistoryPurgePerMonthsAction extends AbstractHistoryPurgePerTimeAction
{
    
    
    /**
     * Constructor for HistoryPurgePerMonthsAction.
     * 
     * @param _measureDAO
     *            MeasureDao
     * @param _kpi
     *            Kpi
     */
    public HistoryPurgePerMonthsAction(final MeasureDao _measureDAO, final Kpi _kpi) {
    
    
        super(_measureDAO, _kpi);
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.AbstractHistoryPurgePerTimeAction#getEvictionTime()
     */
    @Override
    protected DateTime getEvictionTime() {
    
    
        final DateTime dateTime = new DateTime();
        dateTime.minusMonths(kpi.getEvictionRate());
        return dateTime;
    }
    
    
}
