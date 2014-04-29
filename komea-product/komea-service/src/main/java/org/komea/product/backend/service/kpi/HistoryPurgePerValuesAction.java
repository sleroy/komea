
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.api.IHistoryPurgeAction;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;



/**
 */
public class HistoryPurgePerValuesAction implements IHistoryPurgeAction
{
    
    
    private final Kpi        kpi;
    private final MeasureDao measureDAO;
    
    
    
    /**
     * Constructor for HistoryPurgePerValuesAction.
     * 
     * @param _measureDAO
     *            MeasureDao
     * @param _kpi
     *            Kpi
     */
    public HistoryPurgePerValuesAction(final MeasureDao _measureDAO, final Kpi _kpi) {
    
    
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
    
    
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.setOrderByClause("date");
        measureCriteria.createCriteria().andIdKpiEqualTo(kpi.getId());
        final List<Measure> listofMeasures = measureDAO.selectByCriteria(measureCriteria);
        if (listofMeasures.size() > kpi.getEvictionRate()) {
            listofMeasures.subList(kpi.getEvictionRate(), listofMeasures.size());
        }
        final List<Integer> listofMeasuresId = new ArrayList<Integer>(listofMeasures.size());
        
        for (final Measure toDelete : listofMeasures) {
            listofMeasuresId.add(toDelete.getId());
            
        }
        final MeasureCriteria deleteCriteria = new MeasureCriteria();
        deleteCriteria.createCriteria().andIdIn(listofMeasuresId);
        
        return measureDAO.deleteByCriteria(deleteCriteria);
        
        
    }
}
