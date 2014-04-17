
package org.komea.product.backend.service.kpi;



import org.joda.time.DateTime;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;



/**
 */
public class HistoryPurgePerDaysAction implements IHistoryPurgeAction
{
    
    
    private final MeasureDao measureDAO;
    private final Kpi        kpi;
    
    
    
    /**
     * Constructor for HistoryPurgePerDaysAction.
     * @param _measureDAO MeasureDao
     * @param _kpi Kpi
     */
    public HistoryPurgePerDaysAction(final MeasureDao _measureDAO, final Kpi _kpi) {
    
    
        super();
        measureDAO = _measureDAO;
        kpi = _kpi;
        
    }
    
    
    /**
     * Method purgeHistory.
     * @return int
     * @see org.komea.product.cep.tester.IHistoryPurgeAction#purgeHistory()
     */
    @Override
    public int purgeHistory() {
    
    
        final DateTime dateTime = new DateTime();
        dateTime.minusDays(kpi.getEvictionRate());
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        
        measureCriteria.createCriteria().andDateLessThan(dateTime.toDate())
                .andIdKpiEqualTo(kpi.getId());
        return measureDAO.deleteByCriteria(measureCriteria);
    }
    
}
