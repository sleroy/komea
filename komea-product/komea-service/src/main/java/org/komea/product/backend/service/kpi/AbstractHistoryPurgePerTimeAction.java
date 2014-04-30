/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.joda.time.DateTime;
import org.komea.product.backend.api.IHistoryPurgeAction;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;



/**
 * @author sleroy
 */
public abstract class AbstractHistoryPurgePerTimeAction implements IHistoryPurgeAction
{
    
    
    protected final Kpi        kpi;
    protected final MeasureDao measureDAO;
    
    
    
    /**
     * @param _kpi
     * @param _measureDAO
     */
    public AbstractHistoryPurgePerTimeAction(final MeasureDao _measureDAO, final Kpi _kpi) {
    
    
        super();
        measureDAO = _measureDAO;
        kpi = _kpi;
    }
    
    
    /**
     * Method purgeHistory.
     * 
     * @return int
     * @see org.komea.product.backend.api.IHistoryPurgeAction#purgeHistory()
     */
    @Override
    public int purgeHistory() {
    
    
        final DateTime dateTime = getEvictionTime();
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        
        measureCriteria.createCriteria().andDateLessThan(dateTime.toDate())
                .andIdKpiEqualTo(kpi.getId());
        return measureDAO.deleteByCriteria(measureCriteria);
    }
    
    
    protected abstract DateTime getEvictionTime();
}
