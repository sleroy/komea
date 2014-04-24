
package org.komea.product.backend.service.kpi;


import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.backend.api.IHistoryPurgeAction;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;

/**
 */
public class HistoryPurgePerDaysAction implements IHistoryPurgeAction {
    
    private final MeasureDao measureDAO;
    private final Kpi        kpi;
    
    /**
     * Constructor for HistoryPurgePerDaysAction.
     * 
     * @param _measureDAO
     *            MeasureDao
     * @param _kpi
     *            Kpi
     */
    public HistoryPurgePerDaysAction(final MeasureDao _measureDAO, final Kpi _kpi) {
    
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
    
        final DateTime dateTime = new DateTime();
        dateTime.minusDays(kpi.getEvictionRate());
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        
        measureCriteria.createCriteria().andDateLessThan(dateTime.toDate()).andIdKpiEqualTo(kpi.getId());
        
        // FIXME pas optimal.
        // L'ancienne version provoquait une exception : 'Deadlock found when trying to get lock; try restarting transaction'
        List<Measure> measuresToDelete = measureDAO.selectByCriteria(measureCriteria);
        int nbDeletedLines = 0;
        for (Measure measureToDelete : measuresToDelete) {
            nbDeletedLines += measureDAO.deleteByPrimaryKey(measureToDelete.getId());
        }
        return nbDeletedLines;
    }
}
