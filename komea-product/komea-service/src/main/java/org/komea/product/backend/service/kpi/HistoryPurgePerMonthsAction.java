
package org.komea.product.backend.service.kpi;



import org.joda.time.DateTime;
import org.komea.product.backend.api.IHistoryPurgeAction;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;



/**
 */
public class HistoryPurgePerMonthsAction implements IHistoryPurgeAction
{
    
    
    private final MeasureDao measureDAO;
    private final Kpi        kpi;
    
    
    
    /**
     * Constructor for HistoryPurgePerMonthsAction.
     * @param _measureDAO MeasureDao
     * @param _kpi Kpi
     */
    public HistoryPurgePerMonthsAction(final MeasureDao _measureDAO, final Kpi _kpi) {
    
    
        super();
        measureDAO = _measureDAO;
        kpi = _kpi;
        
    }
    
    
    /**
     * Method purgeHistory.
     * @return int
     * @see org.komea.product.backend.api.IHistoryPurgeAction#purgeHistory()
     */
    @Override
    public int purgeHistory() {
    
    
        final DateTime dateTime = new DateTime();
        dateTime.minusMonths(kpi.getEvictionRate());
        final MeasureCriteria historyFilter = new MeasureCriteria();
        historyFilter.createCriteria().andDateLessThan(dateTime.toDate()).andIdKpiEqualTo(kpi.getId());
        ;
        return measureDAO.deleteByCriteria(historyFilter);
    }
}
