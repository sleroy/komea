
package org.komea.product.backend.service.kpi;



import org.joda.time.DateTime;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;



public class HistoryPurgePerMonthsAction implements IHistoryPurgeAction
{
    
    
    private final MeasureDao measureDAO;
    private final Kpi        kpi;
    
    
    
    public HistoryPurgePerMonthsAction(final MeasureDao _measureDAO, final Kpi _kpi) {
    
    
        super();
        measureDAO = _measureDAO;
        kpi = _kpi;
        
    }
    
    
    @Override
    public int purgeHistory() {
    
    
        final DateTime dateTime = new DateTime();
        dateTime.minusMonths(kpi.getEvictionRate());
        final MeasureCriteria example = new MeasureCriteria();
        example.createCriteria().andDateLessThan(dateTime.toDate());
        return measureDAO.deleteByCriteria(example);
    }
}
